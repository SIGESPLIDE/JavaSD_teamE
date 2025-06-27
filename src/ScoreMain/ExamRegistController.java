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
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/main/LOGI001.jsp");
            return;
        }

        School school = teacher.getSchool();

        // DAOのインスタンス化
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        ExamDao examDao = new ExamDao(); // TestDaoからExamDaoに変更
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
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/main/login");
            return;
        }

        School school = teacher.getSchool();
        ExamDao examDao = new ExamDao(); // TestDaoからExamDaoに変更

        String subjectCd = req.getParameter("f3");
        String testNoStr = req.getParameter("f4");
        String[] studentNos = req.getParameterValues("student_no");

        // ★★★ 保存用のList<Exam>を作成 ★★★
        List<Exam> examsToSave = new ArrayList<>();
        boolean isSuccess = false;

        try {
            int testNo = (testNoStr != null && !testNoStr.isEmpty()) ? Integer.parseInt(testNoStr) : 0;

            for (String studentNo : studentNos) {
                String pointStr = req.getParameter("point_" + studentNo);
                int point = (pointStr != null && !pointStr.isEmpty()) ? Integer.parseInt(pointStr) : 0;

                // ★★★ Examオブジェクトを作成する ★★★
                Exam exam = new Exam();

                Student student = new Student();
                student.setNo(studentNo);
                exam.setStudent(student);

                Subject subject = new Subject();
                subject.setCd(subjectCd);
                exam.setSubject(subject);

                exam.setSchool(school);
                exam.setNo(testNo);
                exam.setPoint(point);
                exam.setClassNum(req.getParameter("class_num_" + studentNo));

                examsToSave.add(exam);
            }

            // ★★★ ExamDao.save を呼び出すだけ！ トランザクション管理はDAO任せ ★★★
            isSuccess = examDao.save(examsToSave);

        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "点数に数字以外の文字が入力されています。確認してください。");
            // エラー時はフォワードで画面を再表示
            doGet(req, resp);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            // isSuccessはデフォルトでfalseなので、ここでは何もしなくても良い
        }

        if (isSuccess) {
            resp.sendRedirect(req.getContextPath() + "/main/GRMU002.jsp");
        } else {
            // 登録失敗時のリダイレクト（DBエラーなど）
            String f1 = req.getParameter("f1");
            String f2 = req.getParameter("f2");
            String f3 = req.getParameter("f3");
            String f4 = req.getParameter("f4");
            String redirectUrl = "ExamRegist?f1=" + f1 + "&f2=" + f2 + "&f3=" + f3 + "&f4=" + f4
                               + "&errorMessage=" + URLEncoder.encode("登録処理中にデータベースエラーが発生しました。", "UTF-8");
            resp.sendRedirect(redirectUrl);
        }
    }
}