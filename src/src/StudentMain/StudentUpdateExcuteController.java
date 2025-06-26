package StudentMain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import dao.StudentDao;
import tool.CommonServlet;

public class StudentUpdateExcuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 更新処理はPOSTで行うためGETは未使用
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// リクエストパラメータ取得
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");
		String schoolCd = req.getParameter("school_cd");

		// パラメータの変換と検証
		int entYear = Integer.parseInt(entYearStr);
		boolean isAttend = "true".equalsIgnoreCase(isAttendStr) || "1".equals(isAttendStr);

		// Student オブジェクトの生成とセット
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);

		School school = new School();
		school.setCd(schoolCd);
		student.setSchool(school);

		// データベース更新処理
		StudentDao dao = new StudentDao();
		dao.update(student); // updateメソッドがStudentDaoにある前提

		// 更新完了後、一覧画面にリダイレクト
		resp.sendRedirect("STDM001");
	}
}
