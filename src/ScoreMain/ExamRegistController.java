package ScoreMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// ★ beanパッケージのクラスをインポート
import bean.Exam; // TestからExamに変更
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
// ★ daoパッケージのクラスをインポート
import dao.ClassNumDao;
import dao.ExamDao; // TestDaoからExamDaoに変更
import dao.StudentDao;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/main/ExamRegist"})
public class ExamRegistController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	    HttpSession session = req.getSession();
	    Teacher teacher = (Teacher) session.getAttribute("user");

	    if (teacher == null) {
	        resp.sendRedirect(req.getContextPath() + "/main/LOGI001.jsp");
	        return;
	    }

	    School school = teacher.getSchool();

	    // DAOのインスタンス化
	    StudentDao studentDao = new StudentDao();
	    ClassNumDao classNumDao = new ClassNumDao();
	    SubjectDao subjectDao = new SubjectDao();
	    ExamDao examDao = new ExamDao();

	    // --- ドロップダウン用データ取得 ---
	    List<Integer> entYearList = studentDao.filterBasic(school, true).stream()
	        .map(Student::getEntYear).distinct().sorted().collect(Collectors.toList());
	    List<String> classList = classNumDao.filter(school);
	    List<Subject> subjectList = subjectDao.filter(school);

	    req.setAttribute("entYearList", entYearList);
	    req.setAttribute("classList", classList);
	    req.setAttribute("subjectList", subjectList);

	    // --- 検索条件の取得 ---
	    String entYearStr = req.getParameter("f1");
	    String classNum = req.getParameter("f2");
	    String subjectCd = req.getParameter("f3");
	    String testNoStr = req.getParameter("f4");

	    // --- 検索ボタンが押されたかの判定（f1～f4のいずれかが送られていれば検索実行とみなす） ---
	    boolean isSearchAttempted = entYearStr != null || classNum != null || subjectCd != null || testNoStr != null;

	    if (isSearchAttempted) {
	        // --- 入力がすべて揃っているかチェック ---
	        if (entYearStr == null || entYearStr.isEmpty() ||
	            classNum == null || classNum.isEmpty() ||
	            subjectCd == null || subjectCd.isEmpty() ||
	            testNoStr == null || testNoStr.isEmpty()) {

	            req.setAttribute("errorMessage", "入学年度とクラスと科目と回数を選択してください。");
	        } else {
	            try {
	                int entYear = Integer.parseInt(entYearStr);
	                int testNo = Integer.parseInt(testNoStr);

	                List<Student> studentsInClass = studentDao.filterAllCond(school, entYear, classNum, true);

	                Subject subject = new Subject();
	                subject.setCd(subjectCd);
	                List<Exam> scores = examDao.filter(entYear, classNum, subject, testNo, school);

	                Map<String, Exam> scoreMap = scores.stream()
	                        .collect(Collectors.toMap(
	                                exam -> exam.getStudent().getNo(),
	                                exam -> exam
	                        ));

	                List<Exam> examResults = new ArrayList<>();
	                for (Student student : studentsInClass) {
	                    Exam rowData;
	                    if (scoreMap.containsKey(student.getNo())) {
	                        rowData = scoreMap.get(student.getNo());
	                    } else {
	                        rowData = new Exam();
	                        rowData.setStudent(student);
	                        rowData.setSubject(subject);
	                        rowData.setSchool(school);
	                        rowData.setClassNum(classNum);
	                        rowData.setNo(testNo);
	                        rowData.setPoint(0);

	                    }
	                    examResults.add(rowData);
	                }

	                req.setAttribute("examResults", examResults);
	                req.setAttribute("selectedEntYear", entYear);
	                req.setAttribute("selectedClassNum", classNum);
	                req.setAttribute("selectedSubjectCd", subjectCd);
	                req.setAttribute("selectedTestNo", testNo);
	            } catch (Exception e) {
	                e.printStackTrace();
	                req.setAttribute("errorMessage", "データの取得中にエラーが発生しました。");
	            }
	        }
	    }

	    req.getRequestDispatcher("/main/GRMU001.jsp").forward(req, resp);
	}



	@Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/main/LOGI001.jsp");
            return;
        }

        School school = teacher.getSchool();
        ExamDao examDao = new ExamDao();

        // フォームから必要なデータを取得
        String subjectCd = req.getParameter("f3_registered");
        String testNoStr = req.getParameter("f4_registered");
        String[] studentNos = req.getParameterValues("student_no");

        // ★★★ 登録/更新用リストと、削除用リストを準備 ★★★
        List<Exam> upsertList = new ArrayList<>();
        List<Exam> deleteList = new ArrayList<>();

        boolean isSuccess = true; // 初期値をtrueに

        try {
            int testNo = (testNoStr != null && !testNoStr.isEmpty()) ? Integer.parseInt(testNoStr) : 0;

            if (studentNos != null) {
                for (String studentNo : studentNos) {
                    String pointStr = req.getParameter("point_" + studentNo);
                    Exam exam = new Exam(); // 共通のExamオブジェクトを生成

                    // --- 共通のキー情報をセット ---
                    Student student = new Student();
                    student.setNo(studentNo);
                    exam.setStudent(student);

                    Subject subject = new Subject();
                    subject.setCd(subjectCd);
                    exam.setSubject(subject);

                    exam.setSchool(school);
                    exam.setNo(testNo);
                    exam.setClassNum(req.getParameter("class_num_" + studentNo));

                    // ★★★ 点数入力欄が空かどうかで処理を分岐 ★★★
                    if (pointStr != null && !pointStr.isEmpty()) {
                        // 【登録・更新処理】点数が入力されている場合
                        int point = Integer.parseInt(pointStr);
                        exam.setPoint(point);
                        upsertList.add(exam); // 登録/更新リストに追加
                    } else {
                        // 【削除処理】点数が空の場合
                        deleteList.add(exam); // 削除リストに追加
                    }
                }
            }

            // ★★★ DAOのメソッドを呼び出す ★★★
            // 登録/更新対象があれば実行
            if (!upsertList.isEmpty()) {
                isSuccess = examDao.upsert(upsertList);
            }
            // 削除対象があれば実行（isSuccessが既にfalseなら実行しない）
            if (isSuccess && !deleteList.isEmpty()) {
                isSuccess = examDao.delete(deleteList); // ★新しいdeleteメソッドを呼び出す
            }

        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "点数に数字以外の文字が入力されています。確認してください。");
            isSuccess = false; // エラーなので失敗
            doGet(req, resp); // 検索画面に戻す
            return;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        if (isSuccess) {
            resp.sendRedirect(req.getContextPath() + "/main/GRMU002.jsp");
        } else {
            req.setAttribute("errorMessage", "データベース処理中にエラーが発生しました。");
            doGet(req, resp); // 失敗時も検索画面に戻す
        }
    }
}