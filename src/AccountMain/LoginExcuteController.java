package AccountMain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.CommonServlet;

/**
 * ログイン処理を「実行」するコントローラー。
 * ログインフォームからPOSTリクエストを受け取って認証を行う。
 */

/**
 *
 * @author k_takahashi
 */
@WebServlet("/login-execute.action")
public class LoginExcuteController extends CommonServlet {

    /**
     * このコントローラーに直接GETリクエストが来た場合（URL直打ちなど）は、
     * ログイン画面を表示するLoginControllerにリダイレクトする。
     */
    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.sendRedirect("login.action");
    }

    /**
     * ログインフォームからのPOSTリクエストを処理する。
     */
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	HttpSession session = req.getSession();

    	// JSPからのリクエストの文字コードをUTF-8に設定
        req.setCharacterEncoding("UTF-8");

        // リクエストパラメータからIDとパスワードを取得
        String id = req.getParameter("id");
        String password = req.getParameter("password");

        TeacherDao dao = new TeacherDao();
        Teacher teacher = null;

        try {
            // DAOのloginメソッドを呼び出し、認証を行う
            teacher = dao.login(id, password);
        } catch (Exception e) {
            // データベース接続などでエラーが発生した場合
            req.getRequestDispatcher("/ERRO001.jsp").forward(req, resp);
            return;
        }

        // 認証結果に基づいて画面遷移を制御
        if (teacher != null) {
            // ログイン成功の場合
            // セッションにログインしたユーザーの情報を保存
            session.setAttribute("user", teacher);

            // ログイン後のメニュー画面（MenuController）にリダイレクト
            resp.sendRedirect("menu.action");
        } else {
            // ログイン失敗の場合
            // エラーメッセージと入力されたIDをリクエストスコープに設定
            req.setAttribute("errorMessage", "IDまたはパスワードが確認できませんでした");
            req.setAttribute("id", id);

            // 再度ログイン画面（LOGI001.jsp）にフォワード
            req.getRequestDispatcher("/main/LOGI001.jsp").forward(req, resp);
        }
    }

}