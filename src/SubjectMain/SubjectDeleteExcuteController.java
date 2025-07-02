package SubjectMain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import tool.CommonServlet;

public class SubjectDeleteExcuteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 削除対象の科目情報を取得して確認画面に表示
        String subjectId = req.getParameter("id");
        SubjectDao dao = new SubjectDao();
        Subject subject = dao.findById(subjectId);

        req.setAttribute("subject", subject);
        req.getRequestDispatcher("/WEB-INF/view/subject_delete_confirm.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 科目IDを取得して削除処理を実行
        String subjectId = req.getParameter("id");
        SubjectDao dao = new SubjectDao();
        dao.delete(subjectId);

        // 削除完了画面へリダイレクト
        resp.sendRedirect("SubjectDeleteDoneController.java");
    }
}
