package SubjectMain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import tool.CommonServlet;

/**
 *
 * @author a_suzuki
 *
 */

@WebServlet("/subject/create/controller")
public class SubjectCreateController extends CommonServlet {

    /**
     * GETリクエストを処理し、科目登録画面にフォワードする
     */
    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインしていない場合はログインページにリダイレクト
        if (teacher == null) {
            resp.sendRedirect("/score_manage/login"); // ログインページのURLを仮定
            return;
        }

        // 科目登録フォームのJSPにフォワード
        // 画面表示に必要なデータがあればここでDAOから取得してリクエストにセットするが、
        // 今回は単純に画面を表示するだけと想定
        req.getRequestDispatcher("/subject/subject_create.jsp").forward(req, resp);
    }

    /**
     * POSTリクエストはGETリクエストと同じ処理に流す
     * (フォームの表示のみを行うため)
     */
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        get(req, resp);
    }
}