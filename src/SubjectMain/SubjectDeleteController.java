package SubjectMain;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = {"/main/SubjectDelete"})
public class SubjectDeleteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String subjectId = req.getParameter("id");
		int subjectId_num = Integer.parseInt(req.getParameter("id"));

		if (subjectId == null || subjectId.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Subject ID is required.");
			return;
		}

		try {
			// DAOを使って削除処理を行う（例: SubjectDAO.delete(subjectId);）
			SubjectDao dao = new SubjectDao();
			dao.delete(subjectId_num);

			// 削除後、一覧ページなどにリダイレクト
			resp.sendRedirect("subjectList");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete subject.");
		}
	}
}
