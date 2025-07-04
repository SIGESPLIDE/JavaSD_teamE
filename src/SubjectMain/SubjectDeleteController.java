package SubjectMain; // パッケージ名は適宜合わせてください

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;

@WebServlet("/main/subjectDelete")
public class SubjectDeleteController extends HttpServlet {

    /**
     * GETリクエストを受け取り、削除確認画面を表示します。
     * URLのクエリパラメータから科目コード(cd)を受け取ります。
     * 例: /main/subjectDelete?cd=A02
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログインユーザーの情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインしていない場合はエラーまたはログインページへ
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // 仮のログインページ
            return;
        }
        School school = teacher.getSchool();

        // リクエストパラメータから科目コードを取得
        String cd = request.getParameter("cd");

        SubjectDao subjectDao = new SubjectDao();
        Subject subject = null;

        try {
            // 科目コードと学校コードで科目を1件取得
            subject = subjectDao.get(cd, school);
        } catch (Exception e) {
            e.printStackTrace();
            // エラーハンドリング（エラーページに飛ばすなど）
            request.setAttribute("error", "データベースエラーが発生しました。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // 取得した科目情報をリクエストスコープにセット
        request.setAttribute("subject", subject);

        // 削除確認画面 (JSP) にフォワード
        // このJSPは「〇〇を削除しますか？」と表示し、削除実行用のフォームを持つ
        request.getRequestDispatcher("/main/SBJM006.jsp").forward(request, response);
    }

    /**
     * このサーブレットは確認画面表示用なので、POSTは基本的に使わない。
     * 間違ってPOSTされた場合はGETに処理を回すか、エラーとする。
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}