package SubjectMain;

<<<<<<< HEAD
=======
/**
 *
 * @author k_nohara
 */
import java.util.List;

>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import tool.CommonServlet;


/**
 *
 * @author y_yasui
 *
 */
@WebServlet(urlPatterns = { "/main/subject" })
public class SubjectListController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		SubjectDao dao = new SubjectDao();
<<<<<<< HEAD
//		List<Subject> list = dao.findAll();
=======
		List<Subject> list = dao.filter();
>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git

//		req.setAttribute("subjects", list);

		RequestDispatcher rd = req.getRequestDispatcher("SBJM001.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

	}
}