package StudentMain;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.StudentDao;

/**
*
* @author a_suzuki
*
*/

@WebServlet(urlPatterns = {"/main/StudentCreate"})
public class StudentCreateController extends HttpServlet {

@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. ログインチェック (必須)
        if (teacher == null || teacher.getSchool() == null) {
            // エラーメッセージをリクエストスコープにセットしてエラーページにフォワードする、などが望ましい
            req.setAttribute("error", "ログイン情報が無効です。再度ログインしてください。");
            req.getRequestDispatcher("/main/ERRO001.jsp").forward(req, res); // 例: エラー表示用JSP
            return;
}

        // 2. データの準備
        // 入学年度リスト
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        List<Integer> entYearList = new ArrayList<>(); // JSPと名前を合わせる
        for (int i = 0; i < 11; i++) {
            entYearList.add(currentYear - i);
        }

        // クラス番号リスト
        List<String> classNumList = null;
        StudentDao studentDao = new StudentDao();
        try {
            classNumList = studentDao.filterClassNum(teacher.getSchool());
        } catch (Exception e) {
            e.printStackTrace();
            // 例外が発生した場合、classNumListはnullのままになる
            classNumList = Collections.emptyList(); // nullの代わりに空のリストをセットする（安全策）
            req.setAttribute("error", "クラス情報の取得中にエラーが発生しました。");
}

        // 3. リクエストスコープにデータをセット
        req.setAttribute("ent_year_list", entYearList);
        req.setAttribute("class_num_list", classNumList);

        // 4. JSPへフォワード
        req.getRequestDispatcher("/main/STDM002.jsp").forward(req, res);
    }
}