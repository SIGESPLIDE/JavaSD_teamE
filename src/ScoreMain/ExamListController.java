package ScoreMain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.ExamListStudent;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TeacherDao;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/main/ExamList"})
public class ExamListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // テスト用コード（本番ではセッションから取得）
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = teacherDao.get("admin");

        // teacherがnullの場合はログイン画面にリダイレクト
        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/main/LOGI001.jsp");
            return; // ★★★ 処理をここで中断させる return を追加 ★★★
        } // ★★★ if文を閉じる } を追加 ★★★

        School school = teacher.getSchool();

        // マスタ取得
        StudentDao studentDao = new StudentDao();
        List<Student> allStudents = studentDao.filterBasic(school, true);

        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classStrList = classNumDao.filter(school);
        List<ClassNum> classNumList = classStrList.stream().map(s -> {
            ClassNum c = new ClassNum();
            c.setClass_num(s);
            return c;
        }).collect(Collectors.toList());

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter();

        // 検索条件取得
        String entYear = req.getParameter("enrollmentYear");
        String classNum = req.getParameter("class");
        String subjectCd = req.getParameter("subject");
        String studentId = req.getParameter("studentId");

        // 初期画面：条件なしならGRMR001へ
        if ((entYear == null || entYear.isEmpty()) &&
            (classNum == null || classNum.isEmpty()) &&
            (subjectCd == null || subjectCd.isEmpty()) &&
            (studentId == null || studentId.isEmpty())) {

            req.setAttribute("classNumList", classNumList);
            req.setAttribute("subjectList", subjectList);
            req.setAttribute("entYearList", allStudents.stream()
                .map(Student::getEntYear).distinct().sorted().collect(Collectors.toList()));
            req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);
            return;
        }

        // フィルタ条件で学生抽出
        List<Student> filtered = allStudents.stream()
            .filter(s -> (entYear == null || entYear.isEmpty() || String.valueOf(s.getEntYear()).equals(entYear)))
            .filter(s -> (classNum == null || classNum.isEmpty() || s.getClassNum().equals(classNum)))
            .filter(s -> (studentId == null || studentId.isEmpty() || s.getNo().equals(studentId)))
            .collect(Collectors.toList());

        // 科目名取得
        String subjectName = "";
        for (Subject sub : subjectList) {
            if (sub.getCd().equals(subjectCd)) {
                subjectName = sub.getName();
                break;
            }
        }

        // 結果作成（点数は仮設定、DB接続部分はExamDaoなどで拡張可能）
        List<ExamListStudent> scoreList = new ArrayList<>();
        for (Student s : filtered) {
            ExamListStudent ex = new ExamListStudent();
            ex.setSubjectCd(subjectCd);
            ex.setSubjectName(subjectName);
            // ★★★ s.getNo()はStringなので、Integer.parseIntで変換する ★★★
            // ただし、ExamListStudentのnumフィールドがString型なら変換不要
            ex.setNum(Integer.parseInt(s.getNo())); // 学生番号
            ex.setPoint(0); // 仮の点数（後でExamDao等で取得）
            scoreList.add(ex);
        }

        // JSPに渡す
        req.setAttribute("scoreList", scoreList);
        req.setAttribute("subjectName", subjectName);

        // 成績参照画面にjump！！！
        req.getRequestDispatcher("/main/GRMR001.jsp").forward(req, resp);
    }



    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // ここには何も書かない。
    }

    /**
     * modeパラメータで表示モードを切り替え
     * mode=subject → 科目別、 mode=student → 学生別、それ以外 → フィルタなし全件表示
     */
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // ここには何も書かない。
    }

}