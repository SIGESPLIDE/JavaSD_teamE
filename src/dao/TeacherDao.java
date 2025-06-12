package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Teacher;

/**
 *
 * @author k_takahashi
 *
 */
public class TeacherDao extends dao {
	/**
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Teacher get(String id) throws Exception {
		Teacher Teacher = null;

		try (Connection con = getConnection()) {
			String sql = "SELECT * FROM TEACHER WHERE NAME = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Teacher = new Teacher();
				Teacher.setId(rs.getString("id"));
				Teacher.setPassword(rs.getString("password"));

	}
		}
		return Teacher;

	}
	/**
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Teacher findById(String id) throws Exception{
		Teacher Teacher = null;

		try (Connection con = getConnection()) {
			String sql = "SELECT * FROM TEACHER WHERE NAME = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Teacher = new Teacher();
				Teacher.setId(rs.getString("id"));
				Teacher.setPassword(rs.getString("password"));

			}
		}

		return Teacher;
	}
	/**
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean existsid(String id) throws Exception {
		boolean exists = false;

		try (Connection con = getConnection()) {
			String sql = "SELECT COUNT(*) FROM TEACHER WHERE NAME = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				exists = true;
			}
		}

		return exists;
	}
}
