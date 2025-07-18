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
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.CommonServlet;


/**
 *
 * @author y_yasui
 *
 */
@WebServlet(urlPatterns = { "/main/subject" })
public class SubjectListController extends CommonServlet {

	private Teacher teacher;
	private School school;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		this.execute(req, resp);
		SubjectDao dao = new SubjectDao();


		List<Subject> list = dao.filter(school);
		System.out.println(list);


		req.setAttribute("subjects", list);

		RequestDispatcher rd = req.getRequestDispatcher("SBJM001.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

	}

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        HttpSession session = req.getSession();
        // Teacherオブジェクトを取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // teacherがnullの場合はログイン画面にリダイレクト
        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/login.action");

            return;
        }
        school = teacher.getSchool();
    }
}