package StudentMain;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/main/STDM001" })
public class StudentListController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		// リクエストパラメータの取得（nullチェックあり）
		String schoolCd = req.getParameter("school_cd"); // ← 修正
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");

		// DAOとSchool準備
		StudentDao studentDao = new StudentDao();
		School school = new School();
		school.setCd(schoolCd != null ? schoolCd : schoolCd); // ← 修正

		boolean isAttend = (isAttendStr != null && isAttendStr.equals("true"));
		List<Student> studentList;

		// 条件に応じて filter メソッドを呼び分け
		if (entYearStr != null && classNum != null) {
			int entYear = Integer.parseInt(entYearStr);
			studentList = studentDao.filterAllCond(school, entYear, classNum, isAttend);
		} else if (entYearStr != null) {
			int entYear = Integer.parseInt(entYearStr);
			studentList = studentDao.filterYear(school, entYear, isAttend);
		} else {
			studentList = studentDao.filterBasic(school, isAttend);
		}

		req.setAttribute("students", studentList);

		RequestDispatcher rd = req.getRequestDispatcher("STDM001.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 今回はGETで条件取得のためPOST未使用
	}
}
