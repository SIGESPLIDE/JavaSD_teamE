package StudentMain;

import java.util.List;

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
public class StudentUpdateExcuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// GETは使わない
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			// リクエストパラメータ取得
			String no = req.getParameter("no");
			String name = req.getParameter("name");
			String entYearStr = req.getParameter("ent_year");
			String classNum = req.getParameter("class_num");
			String isAttendStr = req.getParameter("is_attend");
			String schoolCd = req.getParameter("school_cd");

			// 文字列 → 整数、ブール変換
			int entYear = Integer.parseInt(entYearStr);
			boolean isAttend = "true".equalsIgnoreCase(isAttendStr) || "1".equals(isAttendStr);

			// ▼ バリデーション（名前またはクラスが未入力）
			if (name == null || name.trim().isEmpty() || classNum == null || classNum.trim().isEmpty()) {
				req.setAttribute("errorMessage", "氏名とクラスの両方を入力してください。");

				// 入力された内容を保持して戻す
				Student student = new Student();
				student.setNo(no);
				student.setName(name);
				student.setEntYear(entYear);
				student.setClassNum(classNum);
				student.setAttend(isAttend);

				School school = new School();
				school.setCd(schoolCd);
				student.setSchool(school);

				req.setAttribute("student", student);

				// クラスリスト取得（エラー時にも必要）
				ClassNumDao classNumDao = new ClassNumDao();
				List<String> classList = classNumDao.filter(school);
				req.setAttribute("classList", classList);

				RequestDispatcher rd = req.getRequestDispatcher("STDM004.jsp");
				rd.forward(req, resp);
				return;
			}

			// ▼ 正常な場合は更新処理
			Student student = new Student();
			student.setNo(no);
			student.setName(name);
			student.setEntYear(entYear);
			student.setClassNum(classNum);
			student.setAttend(isAttend);

			School school = new School();
			school.setCd(schoolCd);
			student.setSchool(school);

			StudentDao dao = new StudentDao();
			dao.update(student);

			RequestDispatcher rd = req.getRequestDispatcher("STDM005.jsp");
			rd.forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();

			// エラー時にも必要なデータを渡して戻す
			req.setAttribute("errorMessage", "更新処理でエラーが発生しました");

			// 失敗しても最低限studentとclassListを渡すように
			String no = req.getParameter("no");
			String entYearStr = req.getParameter("ent_year");
			String name = req.getParameter("name");
			String classNum = req.getParameter("class_num");
			String isAttendStr = req.getParameter("is_attend");
			String schoolCd = req.getParameter("school_cd");

			int entYear = 0;
			try {
				entYear = Integer.parseInt(entYearStr);
			} catch (NumberFormatException ignored) {}

			boolean isAttend = "true".equalsIgnoreCase(isAttendStr) || "1".equals(isAttendStr);

			Student student = new Student();
			student.setNo(no);
			student.setName(name);
			student.setEntYear(entYear);
			student.setClassNum(classNum);
			student.setAttend(isAttend);

			School school = new School();
			school.setCd(schoolCd);
			student.setSchool(school);

			req.setAttribute("student", student);

			// クラスリスト再取得
			ClassNumDao classNumDao = new ClassNumDao();
			List<String> classList = classNumDao.filter(school);
			req.setAttribute("classList", classList);

			RequestDispatcher rd = req.getRequestDispatcher("STDM004.jsp");
			rd.forward(req, resp);
		}
	}
}
