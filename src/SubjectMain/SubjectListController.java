package SubjectMain;

/**
 *
 * @author k_nohara
 */
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/main/subject" })

public class SubjectListController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		SubjectDao dao = new SubjectDao();
		List<Subject> list = dao.findAll();

		req.setAttribute("subjects", list);

		RequestDispatcher rd = req.getRequestDispatcher("SBJM001.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

	}
}