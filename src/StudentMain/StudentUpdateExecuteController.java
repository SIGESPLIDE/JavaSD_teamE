package StudentMain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/main/studentUpdateExecute" })
public class StudentUpdateExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// GETは使わない
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8");

		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");
		String schoolCd = req.getParameter("school_cd");

		int entYear = Integer.parseInt(entYearStr);
		boolean isAttend = "true".equalsIgnoreCase(isAttendStr) || "1".equals(isAttendStr);

		// 入力値を保持するためのStudent生成
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		School school = new School();
		school.setCd(schoolCd);
		student.setSchool(school);

		// バリデーション
		Map<String, String> errors = new HashMap<>();
		if (name == null || name.trim().isEmpty()) {
			errors.put("name", "氏名を入力してください。");
		}
		if (classNum == null || classNum.trim().isEmpty()) {
			errors.put("class_num", "クラスを選択してください。");
		}

		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.setAttribute("student", student);

			ClassNumDao classNumDao = new ClassNumDao();
			List<String> classList = classNumDao.filter(school);
			req.setAttribute("classList", classList);

			RequestDispatcher rd = req.getRequestDispatcher("STDM004.jsp");
			rd.forward(req, resp);
			return;
		}

		try {
			StudentDao dao = new StudentDao();
			dao.update(student);

			RequestDispatcher rd = req.getRequestDispatcher("STDM005.jsp");
			rd.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "更新処理でエラーが発生しました");

			req.setAttribute("student", student);
			ClassNumDao classNumDao = new ClassNumDao();
			List<String> classList = classNumDao.filter(school);
			req.setAttribute("classList", classList);

			RequestDispatcher rd = req.getRequestDispatcher("STDM004.jsp");
			rd.forward(req, resp);
		}
	}
}
