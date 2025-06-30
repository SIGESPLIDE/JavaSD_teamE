package ScoreMain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.ExamListSubject;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.ExamListSubjectDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TeacherDao;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/main/ExamListSubject"})
public class ExamListSubjectExecuteController extends CommonServlet {

//	private Teacher teacher;
//	private School school;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// ログイン確認とTeacherインスタンスからschoolを受け取る
		// 本番用コード
//				this.execute(req, resp);

        // テスト用コード（本番ではセッションから取得）
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = teacherDao.get("admin");
        School school = teacher.getSchool();

				SubjectDao subjectDao = new SubjectDao();

				// サーブレットから値を受け取る DB検索用
				String entYearStr = (String)req.getAttribute("entYear");
				int entYear = Integer.parseInt(entYearStr);
				String classNum = (String)req.getAttribute("classNum");
				String subjectCd = (String)req.getAttribute("subjectCd");
				Subject subject = subjectDao.get(subjectCd, school);

				// 受け取った検索条件を使って検索を実行
				ExamListSubjectDao testListSubjectDao = new ExamListSubjectDao();
				// こいつの中にはstudentMapのTestListSubjectが入ってる
				List<ExamListSubject> testListSubject = testListSubjectDao.filter(entYear, classNum, subject, school);


				if (testListSubject != null) {
					// testListSubjectリストを、各要素のstudentNo(学生番号)の昇順でソートする
					testListSubject.sort(Comparator.comparing(ExamListSubject::getStudentNo));
				}

				// 検索結果が入ったリストを渡す
				req.setAttribute("testListSubject",testListSubject);


				// ドロップダウン保存用の処理
				// 入学年度一覧を受け取る ログインしている先生の学校コードを入れる
				StudentDao studentDao = new StudentDao();
				List<Student> studentList=studentDao.filterBasic(school, true);

				// 学生リストから入学年度を重複なく抽出し、ソートする
				List<Integer> entYearList = studentList.stream().map(Student::getEntYear)
						.distinct()                    // 重複を除去する
					    .sorted()                      // 昇順にソートする
					    .collect(Collectors.toList()); // 結果をListに変換する

				// クラス一覧を受け取る
				ClassNumDao classNumDao = new ClassNumDao();

				List<Subject> subjectList = subjectDao.filter(school);
				List<String> classList = classNumDao.filter(school);

				// StringリストをClassNumオブジェクトのリストに変換
				List<ClassNum> classNumList = new ArrayList<>();
				for (String classNumStrs : classList) {
					ClassNum classNumForPullDown = new ClassNum();
					classNumForPullDown.setClass_num(classNumStrs);
					classNumList.add(classNumForPullDown);
				}



				// 受け取った一覧をjspに渡す
				req.setAttribute("entYearList", entYearList);
				req.setAttribute("classNumList", classNumList);
				req.setAttribute("subjectList", subjectList);



		        // JSPが「選択状態の保持」と「科目名表示」のために使う全ての値をセットする
				req.setAttribute("entYear", entYearStr);    // String型の入学年度
				req.setAttribute("classNum", classNum);       // String型のクラス番号
				req.setAttribute("subjectCd", subjectCd);     // String型の科目コード
				req.setAttribute("subject", subject);         // 科目名表示用のSubjectオブジェクト


				req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		get(req, resp);

	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception{
//    	 //現在のセッションを取得（存在しない場合は新規作成）
//      HttpSession session = req.getSession();
//      // Teacherオブジェクトを取得
//      Teacher teacher = (Teacher) session.getAttribute("session_user");
//
//      // teacherがnullの場合はログイン画面にリダイレクト
//      if (teacher == null) {
//          resp.sendRedirect(req.getContextPath() + "/login.action");
//
//          return;
//      }
//      school = teacher.getSchool();
	}
}
