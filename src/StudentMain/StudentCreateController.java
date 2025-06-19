package StudentMain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import tool.CommonServlet;

@WebServlet("/student/create")
public class StudentCreateController extends CommonServlet {

    /**
     * GETリクエストを処理し、学生登録画面にフォワードする
     */
    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインしていない、または学校情報がない場合はログインページにリダイレクト
        if (teacher == null || teacher.getSchool() == null) {
            resp.sendRedirect("/score_manage/login"); // ログインページのURLを仮定
            return;
        }
        
        // 登録フォームのJSPにフォワード
        // ※クラス番号のリストなど、フォームに必要なデータがあればここでDAOから取得してリクエストにセットする
        req.getRequestDispatcher("/student/student_create.jsp").forward(req, resp);
    }

    /**
     * POSTリクエストはGETリクエストと同じ処理に流す
     */
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 基本的にはGETで画面表示するため、POSTで呼ばれた場合も同じ画面を表示する
        get(req, resp);
    }
}