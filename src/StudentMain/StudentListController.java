package StudentMain;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import dao.ClassNumDao; // ClassNumDaoをインポート
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/main/STDM001" })
public class StudentListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String errorMessage = null;

        HttpSession session = req.getSession();
        School userSchool = (School) session.getAttribute("userSchool");

        if (userSchool == null || userSchool.getCd() == null || userSchool.getCd().isEmpty()) {
            errorMessage = "学校情報が取得できませんでした。再度ログインしてください。";
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher rd = req.getRequestDispatcher("/error.jsp");
            rd.forward(req, resp);
            return;
        }

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
                RequestDispatcher rd = req.getRequestDispatcher("STDM001.jsp");
                rd.forward(req, resp);
                return;
            }
        }

        boolean isAttend = (isAttendStr != null && isAttendStr.equals("1"));

        StudentDao studentDao = new StudentDao();
        List<Student> studentList = null;


      //  School userSchool = (School) session.getAttribute("user");
      // try {
         //   studentList = studentDao.filterAllCond(school, entYear, classNum, isAttend);
     //   } catch (Exception e) {
         //   errorMessage = "学生情報の取得中にエラーが発生しました: " + e.getMessage();
        //    e.printStackTrace();
      //  }

        try {
            studentList = studentDao.filterAllCond(userSchool, entYear, classNum, isAttend);
        } catch (Exception e) {
            errorMessage = "学生情報の取得中にエラーが発生しました: " + e.getMessage();
            e.printStackTrace();
        }


        // ここから修正・変更する部分
        // ClassNumDaoを使ってクラスリストをDBから取得
        ClassNumDao classNumDao = new ClassNumDao(); // ClassNumDaoのインスタンスを作成
        List<String> classList = classNumDao.filter(userSchool); // filterメソッドを呼び出してクラスリストを取得
        // ここまで修正・変更する部分


        req.setAttribute("students", studentList);
        req.setAttribute("errorMessage", errorMessage);

        // 検索条件をJSPのフォームに再表示するためにセット
        req.setAttribute("entYear", entYearStr);
        req.setAttribute("classId", classNum);
        req.setAttribute("isEnrolled", isAttendStr);

        // クラスリストをリクエストスコープにセット
        req.setAttribute("classList", classList);


        RequestDispatcher rd = req.getRequestDispatcher("STDM001.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        doGet(req, resp);
    }
}