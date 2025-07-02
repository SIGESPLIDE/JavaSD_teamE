package ScoreMain;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// ★ beanパッケージのクラスをインポート
import bean.Exam; // TestからExamに変更
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
// ★ daoパッケージのクラスをインポート
import dao.ClassNumDao;
import dao.ExamDao; // TestDaoからExamDaoに変更
import dao.StudentDao;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/main/ExamRegist"})
public class ExamRegistController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/main/LOGI001.jsp");
            return;
        }

        School school = teacher.getSchool();

        // DAOのインスタンス化
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        ExamDao examDao = new ExamDao();
        StudentDao studentDao = new StudentDao();

        // ドロップダウン用のリストを準備
        List<Student> studentList = studentDao.filterBasic(school, true);
        List<Integer> entYearList = studentList.stream().map(Student::getEntYear)
                .distinct().sorted().collect(Collectors.toList());
        List<String> classList = classNumDao.filter(school);
        List<Subject> subjectList = subjectDao.filter(school); // 学校で絞り込むメソッドを推奨

        req.setAttribute("entYearList", entYearList);
        req.setAttribute("classList", classList);
        req.setAttribute("subjectList", subjectList);

        // --- 検索処理 ---
        String entYearStr = req.getParameter("f1");
        if (entYearStr != null && !entYearStr.isEmpty()) {
            String classNum = req.getParameter("f2");
            String subjectCd = req.getParameter("f3");
            String testNoStr = req.getParameter("f4");

            try {
                int entYear = Integer.parseInt(entYearStr);
                int testNo = (testNoStr != null && !testNoStr.isEmpty()) ? Integer.parseInt(testNoStr) : 0;

                // ★★★ ExamDao.filter の引数に合わせてオブジェクトを準備 ★★★
                Subject subject = new Subject();
                subject.setCd(subjectCd);

                // ★★★ ExamDao.filter を呼び出す ★★★
                List<Exam> examResults = examDao.filter(entYear, classNum, subject, testNo, school);
                req.setAttribute("examResults", examResults); // 変数名を変更

                // 検索条件をリクエストに保持
                req.setAttribute("selectedEntYear", entYear);
                req.setAttribute("selectedClassNum", classNum);
                req.setAttribute("selectedSubjectCd", subjectCd);
                req.setAttribute("selectedTestNo", testNo);

            } catch (NumberFormatException e) {
                req.setAttribute("errorMessage", "入学年度または回数に無効な値が入力されました。");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("errorMessage", "検索中にエラーが発生しました。");
            }
        }

        req.getRequestDispatcher("/main/GRMU001.jsp").forward(req, resp);
    }


    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/main/LOGI001.jsp");
            return;
        }

        School school = teacher.getSchool();
        ExamDao examDao = new ExamDao();

        // フォームから必要なデータを取得
        // ★ 登録対象の科目は、検索時のものを隠しフィールドから受け取る
        String subjectCd = req.getParameter("f3_registered");
        String[] studentNos = req.getParameterValues("student_no");

        List<Exam> examsToProcess = new ArrayList<>();
        boolean isSuccess = false;

        try {
            // 学生番号リストがnullでないことを確認
            if (studentNos != null) {
                for (String studentNo : studentNos) {
                    String pointStr = req.getParameter("point_" + studentNo);

                    // ★ 点数が入力されている学生のみを処理対象とする
                    if (pointStr != null && !pointStr.isEmpty()) {
                        int point = Integer.parseInt(pointStr);

                        // DAOに渡すためのExamオブジェクトを作成
                        Exam exam = new Exam();
                        Student student = new Student();
                        student.setNo(studentNo);
                        exam.setStudent(student);

                        Subject subject = new Subject();
                        subject.setCd(subjectCd);
                        exam.setSubject(subject);

                        exam.setSchool(school);
                        exam.setPoint(point);
                        exam.setClassNum(req.getParameter("class_num_" + studentNo));

                        // ★コントローラーは回数を意識しない。DAOに任せる。
                        // exam.setNo(testNo); ← この行は不要

                        examsToProcess.add(exam);
                    }
                }
            }

            // 処理対象のデータが1件以上ある場合のみDAOを呼び出す
            if (!examsToProcess.isEmpty()) {
                // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
                // ★★★ 修正箇所: 新しく作成した専用メソッドを呼び出す ★★★
                // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
                isSuccess = examDao.saveOrUpdateWithCountUp(examsToProcess);
            } else {
                // 点数が一つも入力されなかった場合は、DB処理は行わず成功とする
                isSuccess = true;
            }

        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "点数に数字以外の文字が入力されています。確認してください。");
            doGet(req, resp); // doGetを再実行してエラー表示
            return;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false; // DB関連などの予期せぬエラー
        }

        if (isSuccess) {
            resp.sendRedirect(req.getContextPath() + "/main/GRMU002.jsp");
        } else {
            // 失敗時のリダイレクト処理（元の検索条件をURLに付与して戻す）
            String f1 = req.getParameter("f1_registered");
            String f2 = req.getParameter("f2_registered");
            String f3 = req.getParameter("f3_registered");
            String f4 = req.getParameter("f4_registered");
            String redirectUrl = "ExamRegist?f1=" + f1 + "&f2=" + f2 + "&f3=" + f3 + "&f4=" + f4
                               + "&errorMessage=" + URLEncoder.encode("登録処理中にデータベースエラーが発生しました。", "UTF-8");
            resp.sendRedirect(redirectUrl);
        }
    }
}
