package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

/**
 *
 * @author r_katabe
 *
 */


public class SubjectDao  extends dao {

	/**
	 *
	 * @param cd
	 * @return
	 * @throws Exception
	 * @author k_nohara
	 */

	public List<Subject> findAll() throws Exception {
	    List<Subject> list = new ArrayList<>();
	    String sql = "SELECT * FROM SUBJECT";

	    try (Connection con = getConnection();
	         PreparedStatement st = con.prepareStatement(sql);
	         ResultSet rs = st.executeQuery()) {

	        while (rs.next()) {
	            Subject subject = new Subject();
	            subject.setCd(rs.getString("CD"));
	            subject.setName(rs.getString("NAME"));

	            School school = new School();
	            school.setCd(rs.getString("SCHOOL_CD"));
	            subject.setSchool(school);

	            list.add(subject);
	        }
	    }
	    return list;
	}


	public Subject findByCd(String cd) throws Exception {
	    Subject subject = null;
	    String sql = "SELECT * FROM SUBJECT WHERE CD = ?";

	    try (Connection con = getConnection();
	         PreparedStatement st = con.prepareStatement(sql)) {

	        st.setString(1, cd);
	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {
	            subject = new Subject();
	            subject.setCd(rs.getString("CD"));
	            subject.setName(rs.getString("NAME"));

	            // School を生成してセット
	            School school = new School();
	            school.setCd(rs.getString("SCHOOL_CD"));
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
     * @param student 更新する学生情報
     * @return 更新に成功した行数
     * @throws Exception
     * @author s_saito, k_nohara
     */
	public int update(Subject subject) throws Exception {
	    int rows = 0;
	    String sql = "UPDATE SUBJECT SET NAME = ?, SCHOOL_CD = ? WHERE CD = ?";

	    try (Connection con = getConnection();
	         PreparedStatement st = con.prepareStatement(sql)) {

	        st.setString(1, subject.getName());
	        st.setString(2, subject.getSchool().getCd());
	        st.setString(3, subject.getCd());

	        rows = st.executeUpdate();
	    }

	    return rows;
	}


}