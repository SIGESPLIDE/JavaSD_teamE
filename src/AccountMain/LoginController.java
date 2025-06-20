package AccountMain;

import javax.servlet.annotation.WebServlet;
// 必要なクラスをインポートします
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.CommonServlet; // CommonServletをインポート

/**
 *
 * @author k_takahashi
 *
 */
@WebServlet("/login.action")
public class LoginController extends CommonServlet {

    /**
     * GETリクエスト（URL直接入力など）でアクセスされた場合にログイン画面を表示します。
     */
    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // ログイン画面（LOGI001.jsp）にフォワードします
        req.getRequestDispatcher("/main/LOGI001.jsp").forward(req, resp);
    }

    /**
     * POSTリクエスト（ログインフォームの送信）でアクセスされた場合にログイン処理を実行します。
     */
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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
            e.printStackTrace();
            // エラーメッセージを設定してログイン画面に戻す
            req.setAttribute("errorMessage", "システムエラーが発生しました。");
            req.getRequestDispatcher("/main/LOGI001.jsp").forward(req, resp);
            return;
        }

        // 認証結果に基づいて画面遷移を制御
        if (teacher != null) {
            // ログイン成功の場合
            // セッションを取得（なければ新規作成）
            HttpSession session = req.getSession();
            // セッションにログインしたユーザーの情報を保存
            session.setAttribute("user", teacher);

            // ログイン後のメニュー画面（MMNU001.jsp）にフォワード
            req.getRequestDispatcher("/main/MMNU001.jsp").forward(req, resp);
        } else {
            // ログイン失敗の場合
            // エラーメッセージをリクエストスコープに設定
        	req.setAttribute("errorMessage", "ログインに失敗しました。IDまたはパスワードが正しくありません。");

            // 再度ログイン画面（LOGI001.jsp）にフォワード
            // 失敗したときに入力したIDをフォームに保持したい場合は、以下も追加
            req.setAttribute("id", id);
            req.getRequestDispatcher("/main/LOGI001.jsp").forward(req, resp);
        }
    }
}