package StudentMain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/main/STDM002" })
public class StudentListController extends CommonServlet {

	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		// リクエストパラメータの取得（nullチェックあり）
		String schoolCd = req.getParameter("school_cd");
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");

		// DAOとSchool準備
		StudentDao studentDao = new StudentDao();
		School school = new School();
		school.setCd(schoolCd != null ? schoolCd : schoolCd);

		boolean isAttend = (isAttendStr != null && isAttendStr.equals("true"));
		List<Student> studentList;
		System.out.println("DEBUG001");
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

		RequestDispatcher rd = req.getRequestDispatcher("STDM001");
		rd.forward(req, resp);
	}

	/**
	 * @author s_saito
	 * @param entYearParam 取得した文字型の状態の入学年度
	 */
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // 文字化け対策（GETリクエストの場合でも念のため）
    req.setCharacterEncoding("UTF-8");

    // 検索条件の取得
    Integer entYear = null;
    String entYearParam = req.getParameter("entYear");
    if (entYearParam != null && !entYearParam.isEmpty() && !entYearParam.equals("0")) {
        try {
            entYear = Integer.parseInt(entYearParam);
        } catch (NumberFormatException e) {
            // 数値変換エラーの場合
            req.setAttribute("errorMessage", "入学年度が不正です。");
            RequestDispatcher dispatcher = req.getRequestDispatcher("main/student_search.jsp");
            dispatcher.forward(req, resp);
            return;
        }
    }

    School school = new School();
    String classNum = req.getParameter("classId");
    String studentName = req.getParameter("studentName");
    boolean isAttend = (boolean)req.getParameter("isEnrolled").equals("1");
    
    school.setCd(req.getParameter("cd"));

    List<Student> studentList = new ArrayList<>();
    String errorMessage = null;

    // 検索ボタンが押されたかどうかを識別するパラメータ（JSP側で条件表示を制御するため）
    // formのhidden inputで <input type="hidden" name="action" value="search"> のように追加するか、
    // 単純に検索パラメータが一つでもあれば検索されたとみなす
    if (entYearParam != null || classNum != null || studentName != null || isAttend == true) {
        try {
            StudentDao studentDao = new StudentDao();
            studentList = studentDao.filterAllCond(school, entYear, classNum, isAttend);
            // 検索が実行されたことを示すフラグをセット
            req.setAttribute("action", "search"); 
        } catch (SQLException e) {
            errorMessage = "データベースエラーが発生しました: " + e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            errorMessage = "予期せぬエラーが発生しました: " + e.getMessage();
            e.printStackTrace();
        }
    }

    // 検索結果とエラーメッセージをリクエスト属性に設定
    req.setAttribute("studentList", studentList);
    req.setAttribute("entYearList", entYear);
    req.setAttribute("classList",classNum);
    req.setAttribute("errorMessage", errorMessage);

    // 同じJSPにフォワード
    RequestDispatcher dispatcher = req.getRequestDispatcher("student_search.jsp");
    dispatcher.forward(req, resp);
}
	
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 今回はGETで条件取得のためPOST未使用
		System.out.println("DEBUG003");

	}
}
