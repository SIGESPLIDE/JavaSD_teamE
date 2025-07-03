package SubjectMain;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = {"/main/SubjectDeleteDone"})

public class SubjectDeleteDoneController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 削除完了画面を表示
        req.getRequestDispatcher("/WebContent/main/SBJM007").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // リクエストから科目IDを取得
        int subjectId = Integer.parseInt(req.getParameter("id"));

        // DAOを使って削除処理を実行
        SubjectDao dao = new SubjectDao();
        dao.delete(subjectId);

        // 削除完了画面へリダイレクト
        resp.sendRedirect("SBJM007");
    }
}
