package SubjectMain;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;

@WebServlet("/main/subjectDeleteExecute")
public class SubjectDeleteExecuteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cdStr = request.getParameter("cd"); // JSPからStringとして取得

        if (cdStr == null || cdStr.isEmpty()) {
            System.err.println("DEBUG: 科目コードがnullまたは空です。");
            request.setAttribute("errorMessage", "削除対象の科目コードが指定されていません。");
            response.sendRedirect(request.getContextPath() + "/main/subjectList");
            return;
        }

        SubjectDao subjectDao = new SubjectDao();
        Subject targetSubject = null;
		try {
			targetSubject = subjectDao.findByCd(cdStr);
		} catch (Exception e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} // 科目コードでSubjectオブジェクトを取得

        if (targetSubject == null) {
            System.err.println("DEBUG: 削除対象の科目が見つかりませんでした: " + cdStr);
            request.setAttribute("errorMessage", "指定された科目が見つかりませんでした。削除できませんでした。");
            response.sendRedirect(request.getContextPath() + "/main/subjectList");
            return;
        }

        boolean success = false; // 削除成否フラグを初期化
        try {
            // ★Subjectオブジェクトをdeleteメソッドに渡す
            success = subjectDao.delete(targetSubject); // deleteの戻り値で成否を判断
        } catch (SQLException e) {
            System.err.println("DEBUG: 科目削除データベースエラー: " + e.getMessage());
            e.printStackTrace();
            success = false; // 例外発生時は失敗
        }


        if (success) {
            response.sendRedirect(request.getContextPath() + "/main/subjectDeleteComplete");
        } else {
            request.setAttribute("errorMessage", "科目の削除に失敗しました。データベースエラーが発生したか、処理が無効でした。");
            // 削除失敗の場合、確認画面に戻るためにtargetSubjectを再設定
            request.setAttribute("targetSubject", targetSubject); // 取得済みのtargetSubjectを再利用
            request.getRequestDispatcher("/main/SBJM006.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/main/SBJM007.jsp").forward(request, response);
    }
}