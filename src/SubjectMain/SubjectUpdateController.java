package SubjectMain;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

public class SubjectUpdateController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	    try {
	        // パラメータからCDを取得
	        String cd = req.getParameter("cd");

	        // DAOから該当の科目データを取得
	        SubjectDao dao = new SubjectDao();
	        Subject subject = dao.findByCd(cd);  // findByCdは前述の通り

	        // 取得したSubjectをリクエストにセット
	        req.setAttribute("subject", subject);

	        // JSPへフォワード（パスは適切に変更）
	        RequestDispatcher rd = req.getRequestDispatcher("SBJM1.jsp");
	        rd.forward(req, resp);

	    } catch (Exception e) {
	        throw new ServletException(e);

	    }
	}
}

