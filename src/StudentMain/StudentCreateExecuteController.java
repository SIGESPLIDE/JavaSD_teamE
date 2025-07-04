package StudentMain;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;

@WebServlet(urlPatterns = {"/main/StudentCreateExcute"})
public class StudentCreateExecuteController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインチェック
        if (teacher == null) {
            res.sendRedirect(req.getContextPath() + "/main/LOGI001.jsp");
            return;
        }

        req.setCharacterEncoding("UTF-8");

        String entYearStr = req.getParameter("ent_year");
        String studentNo = req.getParameter("no");
        String studentName = req.getParameter("name");
        String classNum = req.getParameter("class_num");

        StudentDao studentDao = new StudentDao();
        Map<String, String> errors = new HashMap<>();

        try {
            // === バリデーション ===
            if (entYearStr == null || entYearStr.equals("0")) {
                errors.put("ent_year", "入学年度を選択してください");
            }

            if (studentNo == null || studentNo.isEmpty()) {
                errors.put("no_empty", "このフィールドを入力してください。");
            }

            else if (studentDao.get(studentNo) != null) {
                errors.put("no_duplicate", "学生番号が重複しています");
            }

            if (studentName == null || studentName.isEmpty()) {
                // JSP側でプレースホルダーを切り替えるためのエラーキー
                errors.put("name_empty_placeholder", "氏名を入力してください");
                // JSP側でツールチップを表示するためのエラーキー
                errors.put("name_empty_tooltip", "このフィールドを入力してください。");
            }
            // エラーが1件でもあれば、登録画面に戻る
            if (!errors.isEmpty()) {
                req.setAttribute("ent_year", entYearStr);
                req.setAttribute("no", studentNo);
                req.setAttribute("name", studentName);
                req.setAttribute("class_num", classNum);
                req.setAttribute("errors", errors);

                // 画面表示に必要なリストを再設定
                LocalDate today = LocalDate.now();
                int currentYear = today.getYear();
                List<Integer> entYearList = new ArrayList<>();
                for (int i = 0; i < 11; i++) {
                    entYearList.add(currentYear - i);
                }
                List<String> classNumSet = studentDao.filterClassNum(teacher.getSchool());

                req.setAttribute("ent_year_list", entYearList); // JSP側の名前に合わせる
                req.setAttribute("class_num_list", classNumSet);

                req.getRequestDispatcher("/main/STDM002.jsp").forward(req, res);
                return;
            }

            // エラーがない場合、Studentインスタンスを生成してDBに保存
            Student student = new Student();
            student.setNo(studentNo);
            student.setName(studentName);
            student.setEntYear(Integer.parseInt(entYearStr));
            student.setClassNum(classNum);
            student.setAttend(true); // 在学中をデフォルト
            student.setSchool(teacher.getSchool());

            studentDao.save(student);

            // 完了画面にフォワード
            req.getRequestDispatcher("/main/STDM003.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "システムエラーが発生しました。詳細は管理者にお問い合わせください。"); // エラーキーを統一
            req.getRequestDispatcher("/main/ERRO001.jsp").forward(req, res); // 汎用エラーページに飛ばす方が良い場合も
        }
    }
}