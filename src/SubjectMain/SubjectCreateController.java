package SubjectMain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.CommonServlet;

/**
 *
 * @author a_suzuki
 *
 */


// 実行(Execute)ではなく、この機能(Create)を統括するサーブレットなので、この名前とURLが適切
@WebServlet(urlPatterns={"/main/SubjectCreate"})
public class SubjectCreateController extends CommonServlet {

    /**
     * GETリクエスト: 科目登録画面(SBJM002.jsp)を表示する
     */
    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // ログインチェック
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/main/LOGI001.jsp");
            return;
        }

        // 登録画面へフォワード
        req.getRequestDispatcher("/main/SBJM002.jsp").forward(req, resp);
    }

    /**
     * POSTリクエスト: 科目情報の登録処理を行い、結果に応じて画面遷移させる
     */
    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);

        // ログインチェック
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/main/LOGI001.jsp");
            return;
        }

        Teacher teacher = (Teacher) session.getAttribute("user");
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        // 入力値の検証
        if (cd == null || cd.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "科目コードと科目名は必須です。");
            req.setAttribute("cd", cd); // 入力値をフォームに戻す
            req.setAttribute("name", name);
            req.getRequestDispatcher("/main/SubjectCreate").forward(req, resp);
            return;
        }

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        SubjectDao dao = new SubjectDao();
        boolean result = false;
        String error = null;

        try {
            // DB保存処理
            result = dao.save(subject);
        } catch (Exception e) {
            // DAOで例外が発生した場合 (DB接続エラー、SQLエラーなど)
            e.printStackTrace(); // 開発中は必ずコンソールでログを確認！
            error = "データベース処理中に予期せぬエラーが発生しました。";
        }

        // 結果に応じた画面遷移
        if (result) {
            // 成功: 完了画面へリダイレクト (二重登録防止のため)
            // 完了メッセージをセッションに一時的に保存（フラッシュスコープ）
            session.setAttribute("flash_message", "科目「" + name + "」を登録しました。");
            resp.sendRedirect(req.getContextPath() + "/main/subjectCreateDone"); // 完了画面表示用の別URLへ
        } else {
            // 失敗: 入力画面へフォワード
            if (error == null) {
                // 例外は発生しなかったが、saveがfalseを返した場合 (主キー重複など)
                error = "科目コード「" + cd + "」は既に使用されています。";
            }
            req.setAttribute("error", error);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("/main/SBJM002.jsp").forward(req, resp);
        }
    }
}