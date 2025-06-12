package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;



public class SubjectDao  extends dao {

	public void delete(int id) throws Exception {
		try (Connection con = getConnection()) {
			String sql = "DELETE FROM TEAM_E WHERE id = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		}
	}

}