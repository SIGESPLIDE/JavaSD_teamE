package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

/**
 * DAO for Student entity
 */
public class StudentDao extends dao {
    private String baseSql = "SELECT NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD FROM STUDENT";

<<<<<<< HEAD
	public Student get(String no) throws Exception {
        String sql = baseSql + " WHERE NO = ?";
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, no);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					Student s = new Student();
=======
    public Student get(String no) throws Exception {
        String sql = baseSql + " WHERE NO = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, no);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git
                    s.setNo(rs.getString("NO"));
                    s.setName(rs.getString("NAME"));
                    s.setEntYear(rs.getInt("ENT_YEAR"));
                    s.setClassNum(rs.getString("CLASS_NUM"));
                    s.setAttend(rs.getBoolean("IS_ATTEND"));

<<<<<<< HEAD
					School sch = new School();
                    sch.setCd(rs.getString("SCHOOL_CD"));
					s.setSchool(sch);
=======
                    School sch = new School();
                    sch.setCd(rs.getString("SCHOOL_CD"));
                    s.setSchool(sch);
>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git

<<<<<<< HEAD
					return s;
				}
			}
		}
		return null;
	}
=======
                    return s;
                }
            }
        }
        return null;
    }
>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git

    public List<Student> filterAllCond(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        String sql = baseSql + " WHERE SCHOOL_CD = ? AND ENT_YEAR = ? AND CLASS_NUM = ? AND IS_ATTEND = ?";
<<<<<<< HEAD
		List<Student> list = new ArrayList<>();
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, school.getCd());
			st.setInt(2, entYear);
			st.setString(3, classNum);
			st.setBoolean(4, isAttend);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Student s = new Student();
                    s.setNo(rs.getString("NO"));
                    s.setName(rs.getString("NAME"));
                    s.setEntYear(rs.getInt("ENT_YEAR"));
                    s.setClassNum(rs.getString("CLASS_NUM"));
                    s.setAttend(rs.getBoolean("IS_ATTEND"));
					s.setSchool(school);
					list.add(s);
				}
			}
		}
		return list;
	}
=======
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setBoolean(4, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getString("NO"));
                    s.setName(rs.getString("NAME"));
                    s.setEntYear(rs.getInt("ENT_YEAR"));
                    s.setClassNum(rs.getString("CLASS_NUM"));
                    s.setAttend(rs.getBoolean("IS_ATTEND"));
                    s.setSchool(school);
                    list.add(s);
                }
            }
        }
        return list;
    }
>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git

    public List<Student> filterYear(School school, int entYear, boolean isAttend) throws Exception {
        String sql = baseSql + " WHERE SCHOOL_CD = ? AND ENT_YEAR = ? AND IS_ATTEND = ?";
<<<<<<< HEAD
		List<Student> list = new ArrayList<>();
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, school.getCd());
			st.setInt(2, entYear);
			st.setBoolean(3, isAttend);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Student s = new Student();
                    s.setNo(rs.getString("NO"));
                    s.setName(rs.getString("NAME"));
                    s.setEntYear(rs.getInt("ENT_YEAR"));
                    s.setClassNum(rs.getString("CLASS_NUM"));
                    s.setAttend(rs.getBoolean("IS_ATTEND"));
					s.setSchool(school);
					list.add(s);
				}
			}
		}
		return list;
	}
=======
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setBoolean(3, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getString("NO"));
                    s.setName(rs.getString("NAME"));
                    s.setEntYear(rs.getInt("ENT_YEAR"));
                    s.setClassNum(rs.getString("CLASS_NUM"));
                    s.setAttend(rs.getBoolean("IS_ATTEND"));
                    s.setSchool(school);
                    list.add(s);
                }
            }
        }
        return list;
    }
>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git

    public List<Student> filterBasic(School school, boolean isAttend) throws Exception {
        String sql = baseSql + " WHERE SCHOOL_CD = ? AND IS_ATTEND = ?";
<<<<<<< HEAD
		List<Student> list = new ArrayList<>();
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, school.getCd());
			st.setBoolean(2, isAttend);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Student s = new Student();
                    s.setNo(rs.getString("NO"));
                    s.setName(rs.getString("NAME"));
                    s.setEntYear(rs.getInt("ENT_YEAR"));
                    s.setClassNum(rs.getString("CLASS_NUM"));
                    s.setAttend(rs.getBoolean("IS_ATTEND"));
					s.setSchool(school);
					list.add(s);
				}
			}
		}
		return list;
	}
    public void update(Student student) throws Exception {
    	String sql = "UPDATE STUDENT SET NAME=?, ENT_YEAR=?, CLASS_NUM=?, IS_ATTEND=?, SCHOOL_CD=? WHERE NO=?";
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
    		st.setString(1, student.getName());
    		st.setInt(2, student.getEntYear());
    		st.setString(3, student.getClassNum());
    		st.setBoolean(4, student.isAttend());
    		st.setString(5, student.getSchool().getCd());
    		st.setString(6, student.getNo());
    		st.executeUpdate();
		}
	}

	/**
	 *
	 * @param student
	 * @return
	 * @throws Exception
     * @author s_saito
	 */
    public boolean save(Student student) throws Exception {
    	return false; // テスト用　消してもいいよ
	}
=======
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setBoolean(2, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getString("NO"));
                    s.setName(rs.getString("NAME"));
                    s.setEntYear(rs.getInt("ENT_YEAR"));
                    s.setClassNum(rs.getString("CLASS_NUM"));
                    s.setAttend(rs.getBoolean("IS_ATTEND"));
                    s.setSchool(school);
                    list.add(s);
                }
            }
        }
        return list;
    }
    public void update(Student student) throws Exception {
    	String sql = "UPDATE STUDENT SET NAME=?, ENT_YEAR=?, CLASS_NUM=?, IS_ATTEND=?, SCHOOL_CD=? WHERE NO=?";
    	try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
    		st.setString(1, student.getName());
    		st.setInt(2, student.getEntYear());
    		st.setString(3, student.getClassNum());
    		st.setBoolean(4, student.isAttend());
    		st.setString(5, student.getSchool().getCd());
    		st.setString(6, student.getNo());
    		st.executeUpdate();
    	}
    }

    /**
     *
     * @param student
     * @return
     * @throws Exception
     * @author s_saito
     */
    public boolean save(Student student) throws Exception {
    	return false; // テスト用　消してもいいよ
    }
>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git
}
