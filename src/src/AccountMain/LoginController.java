package AccountMain;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ... (他のimport)
import tool.CommonServlet;
/**
 *
 * @author k_takahashi
 *
 */

@WebServlet("/login.action")
public class LoginController extends CommonServlet {

    /**
     * GETリクエストでアクセスされた場合にログイン画面を表示します。
     */
    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // ログイン画面（LOGI001.jsp）にフォワードします
        req.getRequestDispatcher("/main/LOGI001.jsp").forward(req, resp);
    }

    /**
     * postメソッドは不要になるため削除するか、
     * GETメソッドを呼び出すように単純化します。
     */
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        get(req, resp);
    }
}