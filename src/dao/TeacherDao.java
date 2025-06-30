package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;
import bean.Teacher;

/**
 * TEACHERテーブルへのアクセスを管理するDAOクラス。
 */

/**
 *
 * @author k_takahashi
 *
 */
public class TeacherDao extends dao {

	/**
	 * IDを基に教員情報を1件取得する。 関連する学校情報も同時に取得する。
	 *
	 * @param <id>
	 *            検索する教員のID
	 * @return Teacher: Teacherオブジェクト。該当データがない場合はnullを返す。
	 * @throws Exception
	 *             データベース接続やSQL実行時例外
	 */
	public Teacher get(String id) throws Exception {
		Teacher teacher = null;
		// SQL文: TEACHERテーブルとSCHOOLテーブルを結合して必要な情報をすべて取得
		String sql = "SELECT T.ID, T.PASSWORD, T.NAME, T.SCHOOL_CD, S.NAME AS SCHOOL_NAME " + "FROM TEACHER T "
				+ "LEFT JOIN SCHOOL S ON T.SCHOOL_CD = S.CD " + // LEFT
																// JOINで学校情報がなくても教員は取得可能に
				"WHERE T.ID = ?";

		// try-with-resources ですべてのリソースを自動クローズする
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setString(1, id);

			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					// 結果が見つかった場合、Teacherオブジェクトを生成
					teacher = new Teacher();
					teacher.setId(rs.getString("ID"));
					teacher.setPassword(rs.getString("PASSWORD"));
					teacher.setName(rs.getString("NAME"));

					// 関連するSchoolオブジェクトも生成してセットする
					School school = new School();
					school.setCd(rs.getString("SCHOOL_CD"));
					school.setName(rs.getString("SCHOOL_NAME"));
					teacher.setSchool(school);
				}
			}
		} catch (SQLException e) {
			// エラーログを出力するなど
			e.printStackTrace();
			throw e; // 例外を呼び出し元にスローする
		}

		return teacher;
	}

	/**
	 * IDとパスワードを使用してログイン認証を行う。 認証が成功した場合、完全な教員情報（学校情報を含む）を返す。
	 *
	 * @param <id>
	 *            教員ID
	 * @param <password>
	 *            パスワード
	 * @return Teacher: Teacherオブジェクト。認証に失敗した場合はnullを返す。
	 * @throws Exception
	 *             データベース接続やSQL実行時例外
	 */

	// ※一時的に型をTeacherからList＜Teacher＞に変えています
	public Teacher login(String id, String password) throws Exception {
		//List<Teacher> list = new ArrayList<>();
		Teacher teacher = null;
		String sql = "SELECT T.ID, T.PASSWORD, T.NAME, T.SCHOOL_CD, S.NAME AS SCHOOL_NAME " + "FROM TEACHER T "
				+ "LEFT JOIN SCHOOL S ON T.SCHOOL_CD = S.CD " + "WHERE T.ID = ? AND T.PASSWORD = ?";
		/**try(Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {
					while (rs.next()) {
						Teacher teacher = new Teacher();
						teacher = new Teacher();
						teacher.setId(rs.getString("ID"));
						teacher.setPassword(rs.getString("PASSWORD"));
						teacher.setName(rs.getString("NAME"));

						School school = new School();
						school.setCd(rs.getString("SCHOOL_CD"));
						school.setName(rs.getString("SCHOOL_NAME"));
						teacher.setSchool(school);
						list.add(teacher);
					}
				} */
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setString(1, id);
			st.setString(2, password);

			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					// 認証成功
					teacher = new Teacher();
					teacher.setId(rs.getString("ID"));
					teacher.setPassword(rs.getString("PASSWORD"));
					teacher.setName(rs.getString("NAME"));

					School school = new School();
					school.setCd(rs.getString("SCHOOL_CD"));
					school.setName(rs.getString("SCHOOL_NAME"));
					teacher.setSchool(school);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return teacher;
	}
}