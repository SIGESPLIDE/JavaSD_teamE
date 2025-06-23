package SubjectMain;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

@WebServlet(urlPatterns={"/main"})
public class test extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
        // 科目登録画面のURLを仮定
        // (SubjectCreateControllerのような画面表示用サーブレットが存在すると想定)
        System.out.println("=============================================================================================");

        System.out.println("DEBUG-003");

		RequestDispatcher rd = req.getRequestDispatcher("SBJM001.jsp");

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
