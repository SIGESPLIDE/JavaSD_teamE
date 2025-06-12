package dao;

<<<<<<< HEAD
/**
 * 
 * @author y_yasui
 *
 */
public class SubjectDao {
=======
import java.sql.Connection;
import java.sql.PreparedStatement;
>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git



public class SubjectDao  extends dao {

	public void delete(int id) throws Exception {
		try (Connection con = getConnection()) {
			String sql = "DELETE FROM products WHERE id = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		}
	}

}