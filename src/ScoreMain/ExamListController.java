package ScoreMain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TeacherDao;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/main/ExamList"})
public class ExamListController extends CommonServlet {

	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	// 現在のセッションを取得（存在しない場合は新規作成）
//        HttpSession session = req.getSession();
//        // Teacherオブジェクトを取得
//        Teacher teacher = (Teacher) session.getAttribute("session_user");
//
//        // teacherがnullの場合はログイン画面にリダイレクト
//        if (teacher == null) {
//            resp.sendRedirect(req.getContextPath() + "/login.action");
//            return;
//        }


    	// テスト用コード　あとで消してください
    	TeacherDao teacherDao = new TeacherDao();
    	Teacher teacher = new Teacher();
    	teacher = teacherDao.get("admin");

        // 所属している学校をTeacherオブジェクトから取得
        School school = teacher.getSchool();

        /**
         * DB持ってくるよ
         */
        StudentDao studentdao = new StudentDao();
        List<Student>studentList = studentdao.filterBasic(school, true);

        //学生リストから入学年度を重複なく抽出してソートする
        List<Integer> entYearList = studentList.stream().map(Student::getEntYear)
        		.distinct()
        		.sorted()
        		.collect(Collectors.toList());

        // クラスの一覧をもらう
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(school);
        // StringをclassNumにカエル
        List<ClassNum> classNumList = new ArrayList<>();
        for (String classNumStrs : classList){
        	ClassNum classNum = new ClassNum();
            classNum.setClass_num(classNumStrs);
            classNumList.add(classNum);
        }

        // 科目の一覧をもらう
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList =  subjectDao.filter();

        // 受け取った一覧をJSPに渡す
        req.setAttribute("entYearList", entYearList);
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);

        // 成績参照画面にjump！！！
        req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);

     // GETメソッドの最後に以下を追加

        // パラメータ取得
        String entYear = req.getParameter("enrollmentYear");
        String classNum = req.getParameter("class");
        String subjectCd = req.getParameter("subject");

        // 学生をフィルタリング（簡易的にクラスと入学年度で絞る）
        List<Student> filteredList = studentList.stream()
            .filter(s -> (entYear == null || entYear.isEmpty() || String.valueOf(s.getEntYear()).equals(entYear)))
            .filter(s -> (classNum == null || classNum.isEmpty() || s.getClassNum().equals(classNum)))
            .collect(Collectors.toList());

        // フィルタ済み学生リストをJSPへ
        req.setAttribute("studentList", filteredList);

        // 選択状態保持のためパラメータもセット
        req.setAttribute("entYear", entYear);
        req.setAttribute("classNum", classNum);
        req.setAttribute("subjectCd", subjectCd);

    }




    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//    ここには何も書かない。
    }

    /**
     * modeパラメータで表示モードを切り替え
     * mode=subject → 科目別、 mode=student → 学生別、それ以外 → フィルタなし全件表示
     */
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//    ここには何も書かない。
    }

}
