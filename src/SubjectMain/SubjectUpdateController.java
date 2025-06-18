package SubjectMain;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;



@WebServlet(urlPatterns = { "/main/SBJM001" })
public class SubjectUpdateController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // GETリクエストでもpostメソッドを使って処理する
        post(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            String cd = req.getParameter("cd");
            if (cd == null || cd.isEmpty()) {
                throw new Exception("科目コードが指定されていません");
            }

            SubjectDao dao = new SubjectDao();
            Subject subject = dao.findByCd(cd);

            req.setAttribute("subject", subject);
            RequestDispatcher rd = req.getRequestDispatcher("/SBJM001.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
