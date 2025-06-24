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


@WebServlet(urlPatterns={"/main/SubjectCreateExcute"})
public class SubjectCreateExcuteController extends CommonServlet {

    /**
     * POSTリクエストを処理し、科目情報をデータベースに登録する
     */

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // リクエストの文字コードを設定
        req.setCharacterEncoding("UTF-8");

        // セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインしていない場合は処理を中断し、ログインページにリダイレクト
        if (teacher == null) {
            resp.sendRedirect("/main/LOGI001.jsp"); // ログインページのURLを仮定
            return;
        }
        System.out.println("DEBUG-001");
        // リクエストパラメータの取得
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        // Subjectオブジェクトの生成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool()); // ログインユーザーが所属する学校情報をセット

        // DAOを使ってデータベースに保存
        boolean result = false;
        try {
            SubjectDao subjectDao = new SubjectDao();
            result = subjectDao.save(subject);
        } catch (Exception e) {
            // コンソールにエラーの詳細を出力（デバッグに必須）
            e.printStackTrace();

            // エラーが発生したことをリクエストにセットして、ユーザーに知らせることもできる
            req.setAttribute("error", "データベース登録中にエラーが発生しました。");
        }
        System.out.println("DEBUG-002");
        // 結果に応じてリクエストスコープにデータをセット
        req.setAttribute("result", result);
        req.setAttribute("subject_name", name);

        // 登録完了画面にフォワード
        req.getRequestDispatcher("/main/SBJM003.jsp").forward(req, resp);
    }

    /**
     * GETリクエストで直接アクセスされた場合は、科目登録画面にリダイレクトする
     */

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 科目登録画面のURLを仮定
        // (SubjectCreateControllerのような画面表示用サーブレットが存在すると想定)
        System.out.println("DEBUG-003");

        resp.sendRedirect(req.getContextPath() + "/main/SBJM002");
    }
}
