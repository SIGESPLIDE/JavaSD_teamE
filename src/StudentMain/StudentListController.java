package StudentMain;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/main/STDM001" })
public class StudentListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String errorMessage = null;

        HttpSession session = req.getSession();
        Teacher user = (Teacher) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.action");
            return;
        }

        School school = user.getSchool();

        String entYearStr = req.getParameter("entYear");
        String classNum = req.getParameter("classId");
        String isAttendStr = req.getParameter("isEnrolled");

        Integer entYear = null;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                errorMessage = "入学年度が不正な形式です。";
                req.setAttribute("errorMessage", errorMessage);
                forwardToPage(req, resp, school, entYearStr, classNum, isAttendStr);
                return;
            }
        }

        // クラスのみ指定された場合のエラーチェック
        if ((entYear == null) && (classNum != null && !classNum.isEmpty())) {
            errorMessage = "クラスを指定する場合は入学年度も指定してください。";
            req.setAttribute("errorMessage", errorMessage);
            forwardToPage(req, resp, school, entYearStr, classNum, isAttendStr);
            return;
        }

        boolean isAttend = (isAttendStr != null && isAttendStr.equals("1"));

        StudentDao studentDao = new StudentDao();
        List<Student> studentList = null;

        try {
            studentList = studentDao.filterAllCond(school, entYear, classNum, isAttend);
        } catch (Exception e) {
            errorMessage = "学生情報の取得中にエラーが発生しました。";
        }

        // クラスリスト取得
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(school);

        // 各値をリクエストスコープにセット
        req.setAttribute("students", studentList);
        req.setAttribute("classList", classList);
        req.setAttribute("errorMessage", errorMessage);

        req.setAttribute("entYear", entYearStr);
        req.setAttribute("classId", classNum);
        req.setAttribute("isEnrolled", isAttendStr);

        RequestDispatcher rd = req.getRequestDispatcher("STDM001.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        doGet(req, resp);
    }

    // 共通のJSP転送処理（エラー時用）
    private void forwardToPage(HttpServletRequest req, HttpServletResponse resp, School school,
                               String entYearStr, String classNum, String isAttendStr) throws Exception {
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(school);

        req.setAttribute("classList", classList);
        req.setAttribute("entYear", entYearStr);
        req.setAttribute("classId", classNum);
        req.setAttribute("isEnrolled", isAttendStr);

        RequestDispatcher rd = req.getRequestDispatcher("STDM001.jsp");
        rd.forward(req, resp);
    }
}
