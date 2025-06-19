package StudentMain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet("/student/create_execute")
public class StudentCreateExcuteController extends CommonServlet {

    /**
     * POSTリクエストを処理し、学生情報をデータベースに登録する
     */
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // リクエストの文字コードを設定
        req.setCharacterEncoding("UTF-8");

        // セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインしていない場合は処理を中断
        if (teacher == null) {
            resp.sendRedirect("/score_manage/login"); // ログインページのURLを仮定
            return;
        }

        // リクエストパラメータの取得
        String entYearStr = req.getParameter("ent_year");
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String classNum = req.getParameter("class_num");

        // Studentオブジェクトの生成
        Student student = new Student();
        student.setEntYear(Integer.parseInt(entYearStr));
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);
        student.setSchool(teacher.getSchool()); // ログインユーザーの学校情報をセット
        student.setAttend(true); // 新規登録時は在学中にする

        // DAOを使ってデータベースに保存
        StudentDao sDao = new StudentDao();
        boolean result = sDao.save(student);

        // 結果に応じてリクエストスコープにデータをセット
        req.setAttribute("result", result);
        req.setAttribute("student_name", name);

        // 登録完了画面にフォワード
        req.getRequestDispatcher("/student/student_create_done.jsp").forward(req, resp);
    }

    /**
     * GETリクエストでアクセスされた場合は、登録画面にリダイレクトする
     */
    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/student/create");
    }
}
