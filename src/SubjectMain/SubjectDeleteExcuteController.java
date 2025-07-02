package SubjectMain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

public class SubjectDeleteExcuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    String subjectId = req.getParameter("id");

    if (subjectId != null && !subjectId.isEmpty()) {
        // データベースから科目を削除する処理（仮のDAOクラスを使用）
        SubjectDAO dao = new SubjectDAO();
        boolean success = dao.delete(subjectId);

        if (success) {
            req.setAttribute("message", "科目を削除しました。");
        } else {
            req.setAttribute("message", "科目の削除に失敗しました。");
        }
    } else {
        req.setAttribute("message", "科目IDが指定されていません。");
    }

    // 結果を表示するJSPへフォワード
    forward(req, resp, "subject_delete_done.jsp");
}
}