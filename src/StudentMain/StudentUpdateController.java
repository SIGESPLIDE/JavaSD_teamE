package StudentMain;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.CommonServlet;

/**
 *
 * @author k_nohara
 *
 */
@WebServlet(urlPatterns = { "/main/studentUpdate" })
public class StudentUpdateController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// GETは編集画面表示用などに使用する想定
		try {
			  HttpSession session = req.getSession();
			 Teacher teacher = (Teacher) session.getAttribute("user");

		        // 1. ログインチェック (必須)
		        if (teacher == null || teacher.getSchool() == null) {
		            // エラーメッセージをリクエストスコープにセットしてエラーページにフォワードする、などが望ましい
		            req.setAttribute("error", "ログイン情報が無効です。再度ログインしてください。");
		            req.getRequestDispatcher("/main/error.jsp").forward(req, resp); // 例: エラー表示用JSP
		            return;
		        }

		        String no = req.getParameter("no");
		        if (no == null || no.isEmpty()) {
		            no = req.getParameter("studentNo");  // 念のため対応
		        }
		        if (no == null || no.isEmpty()) {
		            throw new Exception("学生コードが指定されていません");
		        }


			StudentDao dao = new StudentDao();
			Student student = dao.findByNo(no);

			if (student == null) {
				throw new Exception("指定された学生が見つかりません");
			}

			ClassNumDao classNumDao = new ClassNumDao();
			School school = teacher.getSchool();
			List<String> classList = classNumDao.filter(school);

			req.setAttribute("classList", classList);


			req.setAttribute("student", student);

			RequestDispatcher rd = req.getRequestDispatcher("STDM004.jsp");
			rd.forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();

			throw new ServletException(e);
		}
	}

	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        // 更新処理は別サーブレットで実装する想定Add commentMore actions
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POSTメソッドはサポートされていません");
    }
}
