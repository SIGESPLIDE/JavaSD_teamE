package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Exam;
import bean.School;
import bean.Student;
import bean.Subject;

/**
 * 成績の検索・登録・更新・削除・データの取得するメソッドの定義
 *
 * @author a_suzuki
 */

public class ExamDao extends dao {

	/**
	 * Examテーブルからデータを取得するためのベースとなるSQLクエリ。
	 * STUDENTテーブルとSUBJECTテーブルをJOINして、学生名や科目名も取得する。
	 */
	private String baseSql = "SELECT s.ent_year, t.student_no, s.name AS student_name, t.subject_cd, sub.name AS subject_name, t.school_cd, t.no, t.point, t.class_num FROM test t "
			+ "JOIN student s ON t.student_no = s.no AND t.school_cd = s.school_cd "
			+ "JOIN subject sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd ";

	/**
	 * 主キー（学生、科目、学校、回数）を指定して成績情報を1件取得する
	 *
	 * @param student
	 *            生徒情報を含むStudentオブジェクト
	 * @param subject
	 *            科目情報を含むSubjectオブジェクト
	 * @param school
	 *            学校情報を含むSchoolオブジェクト
	 * @param no
	 *            テストの回数
	 * @return 検索結果のExamオブジェクト。見つからない場合はnull
	 * @throws Exception
	 *             データベースアクセス中にエラーが発生した場合
	 */
	public Exam get(Student student, Subject subject, School school, int no) throws Exception {
	    Exam exam = null;
	    String sql = baseSql + "WHERE t.student_no = ? AND t.subject_cd = ? AND t.school_cd = ? AND t.no = ?";

	    // ★★★ すべてのリソースを try() の中で定義 ★★★
	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        // パラメータの設定
	        statement.setString(1, student.getNo());
	        statement.setString(2, subject.getCd());
	        statement.setString(3, school.getCd());
	        statement.setInt(4, no);

	        // ResultSetもこの中で定義
	        try (ResultSet rs = statement.executeQuery()) {
	            // 検索結果の処理
	            if (rs.next()) {
	                exam = new Exam();
	                exam.setStudent(student);
	                exam.setSubject(subject);
	                exam.setSchool(school);
	                exam.setNo(rs.getInt("no"));

	                int pointValue = rs.getInt("point");
	                if (rs.wasNull()) {
	                    exam.setPoint(-1); // または exam.setPoint(null) ※Integerの場合
	                } else {
	                    exam.setPoint(pointValue);
	                }

	                exam.setClassNum(rs.getString("class_num"));
	            }
	        }
	    } catch (Exception e) {
	        throw e; // エラーは上位にスロー
	    }
	    // ★★★ finallyブロックは完全に不要になる ★★★

	    return exam;
	}

	/**
	 * ResultSetからExamオブジェクトのリストを作成するプライベートヘルパーメソッド
	 *
	 * @param rs
	 *            データベースからの検索結果セット
	 * @return Examオブジェクトのリスト
	 * @throws Exception
	 *             処理中にエラーが発生した場合
	 */

	private List<Exam> postFilter(ResultSet rs, School school) throws Exception {
	    List<Exam> list = new ArrayList<>();
	    try {
	        while (rs.next()) {
	            Exam exam = new Exam();

	            Student student = new Student();
	            student.setNo(rs.getString("student_no"));
	            student.setName(rs.getString("student_name"));
	            student.setEntYear(rs.getInt("ent_year"));

	            Subject subject = new Subject();
	            subject.setCd(rs.getString("subject_cd"));
	            subject.setName(rs.getString("subject_name"));

	            exam.setStudent(student);
	            exam.setSubject(subject);
	            exam.setSchool(school);
	            exam.setNo(rs.getInt("no"));
	            exam.setClassNum(rs.getString("class_num"));

	            // ★★★ 修正: 重複したsetPointを削除し、ロジックを一本化 ★★★
	            int pointValue = rs.getInt("point");
	            if (rs.wasNull()) {
	                // DBの値がNULLなら、未入力を示す「-1」をセット
	                exam.setPoint(-1);
	            } else {
	                // 値がある場合はそのままセット
	                exam.setPoint(pointValue);
	            }

	            list.add(exam);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	/**
	 * 指定された条件で成績情報をフィルタリングして取得する
	 *
	 * @param entYear
	 *            入学年度
	 * @param classNum
	 *            クラス番号
	 * @param subject
	 *            科目オブジェクト
	 * @param num
	 *            テストの回数 (UML図の'num'を'no'として解釈)
	 * @param school
	 *            学校オブジェクト
	 * @return フィルタリングされたExamオブジェクトのリスト
	 * @throws Exception
	 *             データベースアクセス中にエラーが発生した場合
	 */
	public List<Exam> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Exam> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;

		// WHERE句を動的に構築
		String condition = "WHERE s.ent_year = ? AND s.class_num = ? AND t.school_cd = ?";
		String order = " ORDER BY t.student_no, t.subject_cd";

		// 科目が指定されている場合
		if (subject != null && subject.getCd() != null && !subject.getCd().isEmpty()) {
			condition += " AND t.subject_cd = ?";
		}
		// 回数が指定されている場合 (0以上)
		if (num > 0) {
			condition += " AND t.no = ?";
		}

		try {
			statement = connection.prepareStatement(baseSql + condition + order);
			int count = 1;
			statement.setInt(count++, entYear);
			statement.setString(count++, classNum);
			statement.setString(count++, school.getCd());

			if (subject != null && subject.getCd() != null && !subject.getCd().isEmpty()) {
				statement.setString(count++, subject.getCd());
			}
			if (num > 0) {
				statement.setInt(count++, num);
			}

			rs = statement.executeQuery();
			list = postFilter(rs, school);

		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return list;
	}

	/**
     * 成績データを保存または更新（UPSERT）する。
     * - 指定された回数のデータが存在すればUPDATE、なければINSERTする。
     * - このメソッド内でトランザクション管理を行う。
     * @param exams 保存または更新する試験データのリスト
     * @return 処理が成功した場合はtrue
     * @throws Exception
     */
    public boolean upsert(List<Exam> exams) throws Exception {
        if (exams == null || exams.isEmpty()) {
            return true;
        }

        Connection connection = getConnection();
        connection.setAutoCommit(false);
        boolean allSuccess = true;

        try {
            for (Exam exam : exams) {
                // 1. データが既に存在するかチェック (回数も条件に含める)
                String checkSql = "SELECT COUNT(*) FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
                int recordCount = 0;
                try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                    checkStmt.setString(1, exam.getStudent().getNo());
                    checkStmt.setString(2, exam.getSubject().getCd());
                    checkStmt.setString(3, exam.getSchool().getCd());
                    checkStmt.setInt(4, exam.getNo());
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next()) {
                            recordCount = rs.getInt(1);
                        }
                    }
                }

                // 2. 存在有無に応じてINSERTまたはUPDATE
                if (recordCount == 0) {
                    // 新規登録 (INSERT)
                    String insertSql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                        insertStmt.setString(1, exam.getStudent().getNo());
                        insertStmt.setString(2, exam.getSubject().getCd());
                        insertStmt.setString(3, exam.getSchool().getCd());
                        insertStmt.setInt(4, exam.getNo());
                        insertStmt.setInt(5, exam.getPoint());
                        insertStmt.setString(6, exam.getClassNum());
                        insertStmt.executeUpdate();
                    }
                } else {
                    // 更新 (UPDATE)
                    String updateSql = "UPDATE test SET point = ? WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, exam.getPoint());
                        updateStmt.setString(2, exam.getStudent().getNo());
                        updateStmt.setString(3, exam.getSubject().getCd());
                        updateStmt.setString(4, exam.getSchool().getCd());
                        updateStmt.setInt(5, exam.getNo());
                        updateStmt.executeUpdate();
                    }
                }
            }
            connection.commit();
        } catch (Exception e) {
            allSuccess = false;
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return allSuccess;
    }

    public boolean delete(List<Exam> list) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            String sql = "DELETE FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
            statement = connection.prepareStatement(sql);

            for (Exam exam : list) {
                statement.setString(1, exam.getStudent().getNo());
                statement.setString(2, exam.getSubject().getCd());
                statement.setString(3, exam.getSchool().getCd());
                statement.setInt(4, exam.getNo());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return true;
    }

}
