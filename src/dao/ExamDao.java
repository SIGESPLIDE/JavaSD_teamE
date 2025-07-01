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
 *
 * @author a_suzuki
 *
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
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			// SQLクエリの準備
			String sql = baseSql + "WHERE t.student_no = ? AND t.subject_cd = ? AND t.school_cd = ? AND t.no = ?";
			statement = connection.prepareStatement(sql);

			// パラメータの設定
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);

			// クエリの実行
			rs = statement.executeQuery();

			// 検索結果の処理
			if (rs.next()) {
				exam = new Exam();
				exam.setStudent(student);
				exam.setSubject(subject);
				exam.setSchool(school);
				exam.setNo(rs.getInt("no"));
				exam.setPoint(rs.getInt("point"));
				exam.setClassNum(rs.getString("class_num"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// リソースの解放
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
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

				// Studentオブジェクトの生成と設定
				Student student = new Student();
				student.setNo(rs.getString("student_no"));
				student.setName(rs.getString("student_name"));
				student.setEntYear(rs.getInt("ent_year"));

				// Subjectオブジェクトの生成と設定
				Subject subject = new Subject();
				subject.setCd(rs.getString("subject_cd"));
				subject.setName(rs.getString("subject_name"));

				// Examオブジェクトのプロパティ設定
				exam.setStudent(student);
				exam.setSubject(subject);
				exam.setSchool(school);
				exam.setNo(rs.getInt("no"));
				exam.setPoint(rs.getInt("point"));
				exam.setClassNum(rs.getString("class_num"));

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
	 * Examオブジェクトのリストを一括で保存（INSERTまたはUPDATE）する。 このメソッド内でトランザクション管理を完結させる。
	 *
	 * @param list
	 *            保存するExamオブジェクトのリスト
	 * @return すべての保存処理が成功した場合にtrueを返す
	 * @throws Exception
	 */
	public boolean save(List<Exam> list) throws Exception {
		Connection connection = null;
		boolean allSuccess = true;

		try {
			// 1. データベース接続を取得
			connection = getConnection();
			// 2. トランザクションを開始 (オートコミットを無効化)
			connection.setAutoCommit(false);

			// リスト内の各Examオブジェクトを処理
			for (Exam exam : list) {
				// 1件ずつ保存する内部メソッドを呼び出す
				if (!saveSingle(exam, connection)) {
					// 1件でも失敗したら、フラグをfalseにしてループを抜ける
					allSuccess = false;
					break;
				}
			}

			// 3. 最終的な結果に応じてコミットまたはロールバック
			if (allSuccess) {
				connection.commit(); // すべて成功したら、変更を確定
			} else {
				connection.rollback(); // 失敗があったら、すべての変更を取り消し
			}
		} catch (Exception e) {
			// 4. 例外発生時もロールバック
			if (connection != null) {
				connection.rollback();
			}
			throw e; // エラーを呼び出し元に通知
		} finally {
			// 5. データベース接続を必ず閉じる
			if (connection != null) {
				try {
					// オートコミット設定を元に戻す
					connection.setAutoCommit(true);
					// コネクションを閉じる
					connection.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		}
		return allSuccess;
	}

	/**
	 * Examオブジェクト1件を保存する内部メソッド (private)。 既存データがあればUPDATE、なければINSERTを行う。
	 * トランザクション管理のため、Connectionを引数で受け取る。
	 *
	 * @param exam
	 *            保存するExamオブジェクト
	 * @param connection
	 *            データベース接続
	 * @return 処理が成功した場合にtrueを返す
	 * @throws Exception
	 */
	private boolean saveSingle(Exam exam, Connection connection) throws Exception {
		PreparedStatement statement = null;
		ResultSet rs = null;
		int count = 0;

		try {
			// 【変更点①】既存レコードの確認を get() から SELECT COUNT(*) に変更
			String checkSql = "SELECT COUNT(*) FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
			statement = connection.prepareStatement(checkSql);
			statement.setString(1, exam.getStudent().getNo());
			statement.setString(2, exam.getSubject().getCd());
			statement.setString(3, exam.getSchool().getCd());
			statement.setInt(4, exam.getNo());

			rs = statement.executeQuery();
			rs.next();
			int recordCount = rs.getInt(1);

			// 使用済みのPreparedStatementとResultSetを閉じる
			rs.close();
			statement.close();

			if (recordCount > 0) {
				// レコードが存在する場合：UPDATE
				String sql = "UPDATE test SET point = ?, class_num = ? WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, exam.getPoint());
				statement.setString(2, exam.getClassNum());
				statement.setString(3, exam.getStudent().getNo());
				statement.setString(4, exam.getSubject().getCd());
				statement.setString(5, exam.getSchool().getCd());
				statement.setInt(6, exam.getNo());
			} else {
				// レコードが存在しない場合：INSERT
				String sql = "INSERT INTO test(student_no, subject_cd, school_cd, no, point, class_num) VALUES(?, ?, ?, ?, ?, ?)";
				statement = connection.prepareStatement(sql);
				statement.setString(1, exam.getStudent().getNo());
				statement.setString(2, exam.getSubject().getCd());
				statement.setString(3, exam.getSchool().getCd());
				statement.setInt(4, exam.getNo());
				statement.setInt(5, exam.getPoint());
				statement.setString(6, exam.getClassNum());
			}

			// SQLの実行
			count = statement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return count > 0;
	}

	/**
	 * 成績情報を削除する（単一レコード） このメソッドは外部から渡されたConnectionを使用し、トランザクション管理は呼び出し元で行う
	 *
	 * @param Exam
	 *            削除するExamオブジェクト
	 * @param connection
	 *            データベース接続
	 * @return 削除に成功した場合はtrue, 失敗した場合はfalse
	 * @throws Exception
	 */
	public boolean delete(Exam exam, Connection connection) throws Exception {
		PreparedStatement statement = null;
		int count = 0;

		try {
			String sql = "DELETE FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, exam.getStudent().getNo());
			statement.setString(2, exam.getSubject().getCd());
			statement.setString(3, exam.getSchool().getCd());
			statement.setInt(4, exam.getNo());

			count = statement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return count > 0;
	}

	/**
	 * 複数の成績情報を一括で削除する このメソッド内でトランザクション管理を行う。
	 *
	 * @param list
	 *            削除するExamオブジェクトのリスト
	 * @return すべての削除が成功した場合はtrue, 失敗した場合はfalse
	 * @throws Exception
	 */
	public boolean delete(List<Exam> list) throws Exception {
		Connection connection = getConnection();
		boolean result = true;

		try {
			// トランザクションを開始
			connection.setAutoCommit(false);

			for (Exam exam : list) {
				if (!delete(exam, connection)) {
					// 1件でも失敗したらループを抜ける
					result = false;
					break;
				}
			}

			if (result) {
				// すべて成功した場合のみコミット
				connection.commit();
			} else {
				// 1件でも失敗した場合はロールバック
				connection.rollback();
			}

		} catch (Exception e) {
			// 例外発生時もロールバック
			try {
				connection.rollback();
			} catch (SQLException sqle) {
				throw sqle;
			}
			throw e;
		} finally {
			// オートコミットを元に戻し、コネクションをクローズ
			try {
				connection.setAutoCommit(true);
			} catch (SQLException sqle) {
				throw sqle;
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return result;
	}

}
