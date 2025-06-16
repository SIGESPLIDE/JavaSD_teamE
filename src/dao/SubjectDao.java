package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.Subject;

/**
 *
 * @author r_katabe
 *
 */

public class SubjectDao  extends dao {

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
     * @param student 更新する学生情報
     * @return 更新に成功した行数
     * @throws Exception
     * @author s_saito, k_nohara
     */
    public int update(Subject subject) throws Exception {
        int rows = 0;
        String sql = "UPDATE SUBJECT SET NAME = ? WHERE SHCOOL_CD = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            st.setString(1, subject.getName());
            st.setString(2, subject.getSchool().toString());
            
            rows = st.executeUpdate();
        }
        return rows;
    }

}