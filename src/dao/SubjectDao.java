package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;
import bean.Subject;

/**
 *
 * @author r_katabe
 *
 */

public class SubjectDao extends dao {

	/**
	 *
	 * @param cd
	 * @return
	 * @throws Exception
	 * @author k_nohara
	 */

	public Subject findByCd(String cd) throws Exception {
		Subject subject = null;
		String sql = "SELECT * FROM SUBJECT WHERE CD = ?";

		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, cd);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				subject = new Subject();
				subject.setCd(rs.getString("CD"));
				subject.setName(rs.getString("NAME"));

				// School を生成してセット
				School school = new School();
				school.setCd(rs.getString("SHCOOL_CD")); // カラム名要確認
				subject.setSchool(school);
			}

		}

		return subject;
	}

	public void delete(int id) throws Exception {
		try (Connection con = getConnection()) {
			String sql = "DELETE FROM TEAM_E WHERE id = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		}
	}

	/**
	 * 学生情報を更新する
	 *
	 * @param student
	 *            更新する学生情報
	 * @return 更新に成功した行数
	 * @throws Exception
	 * @author s_saito, k_nohara
	 */
	public int update(Subject subject) throws Exception {
		int rows = 0;
		String sql = "UPDATE SUBJECT SET NAME = ? WHERE SHCOOL_CD = ?";
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, subject.getName());
			st.setString(2, subject.getSchool().toString());

			rows = st.executeUpdate();
		}
		return rows;
	}

	/**
	 *
	 * @param subject
	 * @return
	 * @throws Exception
	 * @author a_suzuki
	 *
	 */

	public boolean save(Subject subject) throws Exception {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    int line = 0;

	    // 常にINSERT文を実行する
	    String insertSql = "INSERT INTO subject(cd, name, school_cd) VALUES(?, ?, ?)";

	    try {
	        connection = getConnection();
	        // ★改善点1: INSERTのみなので、1文のSQLなら自動コミットのままでも良い。
	        // ただし、明示的なトランザクション管理は堅牢なコードの基本なので、
	        // このまま残すことを推奨します。
	        connection.setAutoCommit(false);

	        statement = connection.prepareStatement(insertSql);

	        // プレースホルダ (?) に値をセット
	        statement.setString(1, subject.getCd());
	        statement.setString(2, subject.getName());
	        statement.setString(3, subject.getSchool().getCd());

	        // INSERT文を実行
	        line = statement.executeUpdate();

	        // ★改善点2: 処理が成功したので、トランザクションをコミット
	        connection.commit();

	    } catch (Exception e) {
	        // ★改善点3: エラーが発生したら、変更をロールバック
	        if (connection != null) {
	            try {
	                connection.rollback();
	            } catch (SQLException sqle) {
	                sqle.printStackTrace(); // ロールバック失敗時のエラーもログに残す
	            }
	        }
	        // エラーを呼び出し元にスローして通知
	        throw e;
	    } finally {
	        // ★改善点4: リソースを確実に解放する
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                sqle.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                // 自動コミットモードを元に戻す（コネクションプール利用時に重要）
	                connection.setAutoCommit(true);
	                connection.close();
	            } catch (SQLException sqle) {
	                sqle.printStackTrace();
	            }
	        }
	    }

	    // 1件以上のデータが登録されたかを返す
	    return line > 0;
	}
}