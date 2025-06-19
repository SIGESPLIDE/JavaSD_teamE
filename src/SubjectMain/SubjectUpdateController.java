package SubjectMain;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

/**
 *
 * @author k_nohara
 *
 */
@WebServlet(urlPatterns = { "/main/subjectUpdate" })
public class SubjectUpdateController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {

            String cd = req.getParameter("cd");
            if (cd == null || cd.isEmpty()) {
                throw new Exception("科目コードが指定されていません");
            }


            SubjectDao dao = new SubjectDao();
            Subject subject = dao.findByCd(cd);

            if (subject == null) {
                throw new Exception("指定された科目が見つかりません");
            }


            req.setAttribute("subject", subject);


            RequestDispatcher rd = req.getRequestDispatcher("SBJM004.jsp");
            rd.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();

            throw new ServletException(e);
        }
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 更新処理は別サーブレットで実装する想定
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POSTメソッドはサポートされていません");
    }
}
