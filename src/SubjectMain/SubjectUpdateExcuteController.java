package SubjectMain;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

public class SubjectUpdateExcuteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // GETは使用しない
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        try {
            // 入力値を取得
            String cd = req.getParameter("CD");  // 主キーはStringで扱うべき
            String name = req.getParameter("NAME");
            String schoolCd = req.getParameter("SCHOOL_CD");

            // Subjectオブジェクトに詰める
            Subject subject = new Subject();
            subject.setCd(cd);  // int ではなく String として扱う
            subject.setName(name);

            // School オブジェクトを作成してセット
            School school = new School();
            school.setCd(schoolCd);
            subject.setSchool(school);

            // DAOで更新処理
            SubjectDao dao = new SubjectDao();
            int result = dao.update(subject);

            // 結果をスコープに格納
            req.setAttribute("updateResult", result);

            // 完了画面へ遷移
            RequestDispatcher rd = req.getRequestDispatcher("SBJM004.jsp");
            rd.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "更新処理でエラーが発生しました");
            RequestDispatcher rd = req.getRequestDispatcher("SBJM005.jsp");
            rd.forward(req, resp);
        }

    }
}
