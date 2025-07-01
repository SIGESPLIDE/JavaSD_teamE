package ScoreMain;
 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import bean.School;
import bean.Teacher;
import dao.TeacherDao;
import tool.CommonServlet;
 
/**
*
* @author y_yasui
*
*/
@WebServlet(urlPatterns={"/main/ExamList"})
public class ExamListController extends CommonServlet {
 
//	private Teacher teacher;
//	private School school;
 
	@Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // テスト用コード（本番ではセッションから取得）
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = teacherDao.get("admin");
 
        // teacherがnullの場合はログイン画面にリダイレクト
 
        School school = teacher.getSchool();
 
//        List<ExamListStudent> scoreList = new ArrayList<>();
//        for (Student s : filtered) {
//            ExamListStudent ex = new ExamListStudent();
//            ex.setSubjectCd(subjectCd);
//            ex.setSubjectName(subjectName);
//            ex.setNum(Integer.parseInt(s.getNo())); // 学生番号
//            ex.setPoint(0); // 仮の点数（後でExamDao等で取得）
//            scoreList.add(ex);
//        }
 
        // JSPに渡す
//        req.setAttribute("scoreList", scoreList);
        req.setAttribute("subjectName", subjectName);
 
        // 成績参照画面にjump！！！
        req.getRequestDispatcher("GRMR002.jsp").forward(req, resp);
 
    }
 
 
    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // ここには何も書かない。
 
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = teacherDao.get("admin");
 
    	String check=req.getParameter("f");
 
    	if("sj".equals(check)){
    		String entYear = req.getParameter("f1");
    		String classNum = req.getParameter("f2");
    		String subjectCd = req.getParameter("f3");
 
 
    		System.out.println(entYear);
    		System.out.println(classNum);
    		System.out.println(subjectCd);
 
    		if(entYear.isEmpty()|| classNum.isEmpty() || subjectCd.isEmpty()){
    			req.setAttribute("sjError", "入学年度とクラスと科目を選択してください");
 
    			req.setAttribute("entYear", entYear);
    			req.setAttribute("classNum", classNum);
    			req.setAttribute("subjectCd", subjectCd);
 
    			this.get(req, resp);
 
    			return;
    		}
 
 
    		req.setAttribute("entYear",entYear);
    		req.setAttribute("classNum", classNum);
    		req.setAttribute("subjectCd", subjectCd);
 
    		req.getRequestDispatcher("/main/ExamListSubject").forward(req, resp);
    	}else{
    		req.getRequestDispatcher("/main/examliststudent").forward(req, resp);
    	}
    }
 
    /**
     * modeパラメータで表示モードを切り替え
     * mode=subject → 科目別、 mode=student → 学生別、それ以外 → フィルタなし全件表示
     */
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        HttpSession session = req.getSession();
//        // Teacherオブジェクトを取得
//        Teacher teacher = (Teacher) session.getAttribute("session_user");
//
//        // teacherがnullの場合はログイン画面にリダイレクト
//        if (teacher == null) {
//            resp.sendRedirect(req.getContextPath() + "/login.action");
//
//            return;
//        }
//        school = teacher.getSchool();
    }
    }
