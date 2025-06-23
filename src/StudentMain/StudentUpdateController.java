package StudentMain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import dao.StudentDao;
import tool.CommonServlet;

public class StudentUpdateController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// GETは編集画面表示用などに使用する想定
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// フォームからのパラメータ取得
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");
		String schoolCd = req.getParameter("school_cd");

		// 入学年度と在学状態を変換
		int entYear = Integer.parseInt(entYearStr);
		boolean isAttend = "true".equals(isAttendStr) || "1".equals(isAttendStr);

		// Student オブジェクトを作成
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);

		School school = new School();
		school.setCd(schoolCd);
		student.setSchool(school);

		// DAOで更新
		StudentDao dao = new StudentDao();
		dao.update(student);  // ※ updateメソッドが StudentDao に実装されている前提

		// リダイレクトまたは結果画面へ
		resp.sendRedirect("STDM001");  // 学生一覧に戻すなど
	}
}
