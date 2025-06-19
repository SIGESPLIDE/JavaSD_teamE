package SubjectMain;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

/**
 *
 * @author k_nohara
 *
 */
@WebServlet(urlPatterns = {"/main/subjectUpdateExecute"})
public class SubjectUpdateExcuteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // GETリクエストは使用しない
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            // フォームから値を取得
            String cd = req.getParameter("subjectCode");
            String name = req.getParameter("subjectName");
            String schoolCd = req.getParameter("SCHOOL_CD"); // 必要に応じてフォームにhidden追加

            // バリデーション（科目名が空かチェック）
            if (name == null || name.trim().isEmpty()) {
                req.setAttribute("errorMessage", "科目名は必須項目です。");

                // 入力内容を戻す
                Subject subject = new Subject();
                subject.setCd(cd);
                subject.setName(name);
                School school = new School();
                school.setCd(schoolCd);
                subject.setSchool(school);
                req.setAttribute("subject", subject);

                // 入力画面に戻る
                RequestDispatcher rd = req.getRequestDispatcher("SBJM004.jsp");
                rd.forward(req, resp);
                return;
            }

            // 正常な場合は更新処理
            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);
            School school = new School();
            school.setCd(schoolCd);
            subject.setSchool(school);

            SubjectDao dao = new SubjectDao();
            int result = dao.update(subject);

            req.setAttribute("updateResult", result);

            // 完了画面に遷移
            RequestDispatcher rd = req.getRequestDispatcher("SBJM005.jsp");
            rd.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "更新処理でエラーが発生しました");
            RequestDispatcher rd = req.getRequestDispatcher("SBJM004.jsp");
            rd.forward(req, resp);
        }
    }
}
