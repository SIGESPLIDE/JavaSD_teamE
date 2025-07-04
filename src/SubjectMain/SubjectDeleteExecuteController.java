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

@WebServlet("/main/subjectDeleteExecute")
public class SubjectDeleteExecuteController extends HttpServlet {

    /**
     * POSTリクエストを受け取り、科目の削除処理を実行します。
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログインユーザーの情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインしていない場合はエラーまたはログインページへ
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        School school = teacher.getSchool();

        // フォームから送信された科目コードを取得
        String cd = request.getParameter("cd");

        // 削除対象のSubjectオブジェクトを作成
        Subject subjectToDelete = new Subject();
        subjectToDelete.setCd(cd);
        subjectToDelete.setSchool(school); // ★学校情報をセットすることが重要

        SubjectDao subjectDao = new SubjectDao();
        boolean success = false;

        try {
            // ★Subjectオブジェクトを引数にしてdeleteメソッドを呼び出す
            success = subjectDao.delete(subjectToDelete);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        if (success) {
            // 成功した場合、削除完了画面 (JSP) にフォワード
            request.getRequestDispatcher("/main/SBJM007.jsp").forward(request, response);
        } else {
            // 失敗した場合、エラーメッセージをセットして一覧などに戻る
            request.setAttribute("error", "科目の削除に失敗しました。");
            // 例えば科目一覧サーブレットにリダイレクト
            response.sendRedirect(request.getContextPath() + "/main/subjectList"); // 仮のURL
        }
    }

    /**
     * このサーブレットは削除実行用なので、直接GETでアクセスされた場合は
     * 一覧画面に戻すなど、不正なアクセスとみなすのが一般的。
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/main/subjectList"); // 仮のURL
    }
}