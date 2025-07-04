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

@WebServlet(urlPatterns = { "/main/SubjectCreateExcute" })
public class SubjectCreateExecuteController extends CommonServlet {

	/**
	 * POSTリクエストを処理し、科目情報をデータベースに登録する
	 */
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// ... (文字コード設定、セッションチェックはそのまま) ...
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/main/Login.action");
			return;
		}

		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		// ★★★ 1. 文字数チェックを追加 ★★★
		if (cd == null || cd.length() != 3) {
			// エラーメッセージをリクエストスコープにセット
			req.setAttribute("error", "科目コードは3文字で入力してください");

			// 入力値をフォームに戻すためにリクエストスコープにセット
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);

			// 科目登録画面（入力画面）にフォワードで戻す
			req.getRequestDispatcher("/main/SBJM002.jsp").forward(req, resp);
			return; // 処理を終了
		}

		// ★★★ 2. 重複チェック（既存のロジック） ★★★
		SubjectDao subjectDao = new SubjectDao();
		Subject existingSubject = subjectDao.get(cd, teacher.getSchool());

		if (existingSubject != null) {
			// エラーメッセージをリクエストスコープにセット（画像に合わせて修正）
			req.setAttribute("error", "科目コードが重複しています");

			// 入力値をフォームに戻すためにリクエストスコープにセット
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);

			// 科目登録画面（入力画面）にフォワードで戻す
			req.getRequestDispatcher("/main/SBJM002.jsp").forward(req, resp);
			return; // 処理を終了
		}

		// ----- 以下、すべてのチェックをパスした場合の処理（変更なし） -----

		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());

		boolean result = false;
		try {
			result = subjectDao.save(subject);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "データベース登録中にエラーが発生しました。");
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.getRequestDispatcher("/main/SBJM002.jsp").forward(req, resp);
			return;
		}

		req.setAttribute("result", result);
		req.setAttribute("subject_name", name);
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

		resp.sendRedirect(req.getContextPath() + "/JavaSD/main/SBJM002.jsp");
	}
}
