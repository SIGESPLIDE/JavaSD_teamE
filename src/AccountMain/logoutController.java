package AccountMain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.CommonServlet;

/**
 * ログアウト処理を行うコントローラー
 *@author k_takahashihor
 */
@WebServlet("/main/logout")
public class logoutController extends CommonServlet {

    /**
     * GETリクエストでアクセスされた際にログアウト処理を実行します。
     * ログアウトリンクからのアクセスは通常こちらで処理されます。
     */
    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 現在のセッションを取得します（存在しない場合はnullを返す）
        HttpSession session = req.getSession(false);

        // セッションが存在する場合（ログインしている場合）
        if (session != null) {
            // セッションを無効化します。これがログアウト処理の本体です。
            session.invalidate();
        }
        

        // ログアウト完了画面（LOGOUT.jsp）にフォワード（画面遷移）します。
        req.getRequestDispatcher("/main/LOGO001.jsp").forward(req, resp);
    }

    /**
     * POSTリクエストが来た場合も、GETと同じ処理を実行するようにします。
     */
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GETメソッドを呼び出して同じ処理をさせます。
        get(req, resp);
    }
}