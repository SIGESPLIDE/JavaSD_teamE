package ScoreMain;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        ExamDao examDao = new ExamDao();

        // --- 1. ドロップダウン用のマスタデータを準備 ---
        List<Integer> entYearList = studentDao.filterBasic(school, true).stream()
                .map(Student::getEntYear).distinct().sorted().collect(Collectors.toList());
        List<String> classList = classNumDao.filter(school);
        List<Subject> subjectList = subjectDao.filter(school);

        req.setAttribute("entYearList", entYearList);
        req.setAttribute("classList", classList);
        req.setAttribute("subjectList", subjectList);

        // --- 2. 検索条件をすべて取得 ---
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String testNoStr = req.getParameter("f4");

        // --- 3. 検索が実行された場合のみリストを作成 ---
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                int entYear = Integer.parseInt(entYearStr);
                int testNo = (testNoStr != null && !testNoStr.isEmpty()) ? Integer.parseInt(testNoStr) : 0;

                // 3a. 【学生リストの取得】クラスに所属する在校生を全員取得 (これが表示の土台)
                List<Student> studentsInClass = studentDao.filterAllCond(school, entYear, classNum, true);

                // 3b. 【成績リストの取得】指定された科目・回数の成績を取得
                Subject subject = new Subject();
                subject.setCd(subjectCd);
                List<Exam> scores = examDao.filter(entYear, classNum, subject, testNo, school);

                // 3c. 【成績データの高速検索用Map作成】
                Map<String, Exam> scoreMap = scores.stream()
                    .collect(Collectors.toMap(
                        (Exam exam) -> exam.getStudent().getNo(),
                        (Exam exam) -> exam
                    ));

                // 3d. 【最終的な表示用リストの作成 (マージ処理)】
                List<Exam> examResults = new ArrayList<>();
                for (Student student : studentsInClass) {
                    Exam rowData;
                    if (scoreMap.containsKey(student.getNo())) {
                        // 成績が存在する場合：DBから取得した成績データをそのまま使う
                        rowData = scoreMap.get(student.getNo());
                    } else {
                        // 成績が存在しない場合：表示用に新規データを作成
                        rowData = new Exam();
                        rowData.setStudent(student); // 学生情報
                        rowData.setSubject(subject); // 検索した科目
                        rowData.setSchool(school);
                        rowData.setClassNum(classNum);
                        rowData.setNo(testNo);     // 検索した回数
                        rowData.setPoint(-1);      // 未入力は「-1」として扱う
                    }
                    examResults.add(rowData);
                }

                // 3e. JSPにデータを渡す
                req.setAttribute("examResults", examResults);
                req.setAttribute("selectedEntYear", entYear);
                req.setAttribute("selectedClassNum", classNum);
                req.setAttribute("selectedSubjectCd", subjectCd);
                req.setAttribute("selectedTestNo", testNo);

            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("errorMessage", "データの取得中にエラーが発生しました。");
            }
        }

        // --- 4. JSPへフォワード ---
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
