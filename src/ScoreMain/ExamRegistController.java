package ScoreMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Exam;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.ExamDao;
import tool.CommonServlet;

@WebServlet("/exam/regist")
public class ExamRegistController extends CommonServlet {

    /**
     * GETリクエスト: 成績登録画面の表示
     */
    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            resp.sendRedirect("/score_manage/login");
            return;
        }

        School school = teacher.getSchool();

        // DAOのインスタンス化
        ClassNumDao cNumDao = new ClassNumDao();
        SubjectDao subDao = new SubjectDao();
        StudentDao stuDao = new StudentDao();

        // 検索条件の取得 (入学年度、クラス、科目)
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        }

        // 画面のドロップダウンリスト用のデータを取得
        List<String> classNumList = cNumDao.filter(school);
        List<Subject> subjectList = subDao.filter(school);

        // リクエストスコープにセット
        req.setAttribute("class_num_list", classNumList);
        req.setAttribute("subject_list", subjectList);
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);

        // 検索条件がすべて揃っている場合、学生リストを取得
        if (entYear != 0 && classNum != null && !classNum.isEmpty()) {
            List<Student> students = stuDao.filter(school, entYear, classNum, true);
            req.setAttribute("students", students);
        }

        // 成績登録画面にフォワード
        req.getRequestDispatcher("/exam/exam_regist.jsp").forward(req, resp);
    }

    /**
     * POSTリクエスト: 成績データの登録処理
     */
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            resp.sendRedirect("/score_manage/login");
            return;
        }

        // リクエストパラメータから学生番号と点数の配列を取得
        String[] studentNos = req.getParameterValues("no");
        String[] points = req.getParameterValues("point");

        // 科目、クラス、回数などの共通情報を取得
        String subjectCd = req.getParameter("subject_cd");
        String classNum = req.getParameter("class_num");
        int examNo = Integer.parseInt(req.getParameter("exam_no")); // 回数を仮にexam_noとする

        // 登録するExamオブジェクトのリストを作成
        List<Exam> examList = new ArrayList<>();

        // 科目オブジェクトを作成
        Subject subject = new Subject();
        subject.setCd(subjectCd);
        subject.setSchool(teacher.getSchool());

        for (int i = 0; i < studentNos.length; i++) {
            // 点数が入力されているデータのみを対象とする
            if (points[i] != null && !points[i].isEmpty()) {
                Exam exam = new Exam();

                Student student = new Student();
                student.setNo(studentNos[i]);

                exam.setStudent(student);
                exam.setSubject(subject);
                exam.setSchool(teacher.getSchool());
                exam.setNo(examNo);
                exam.setPoint(Integer.parseInt(points[i]));
                exam.setClassNum(classNum);

                examList.add(exam);
            }
        }

        // ExamDaoでデータベースに保存
        ExamDao examDao = new ExamDao();
        // DAOにListで一括保存するメソッドがあると仮定
        boolean result = examDao.save(examList);

        req.setAttribute("result", result);

        // 完了画面にフォワード
        req.getRequestDispatcher("../main/GRMU002.jsp").forward(req, resp);
    }
}