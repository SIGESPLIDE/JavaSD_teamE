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

	public Student get(String no) throws Exception {
		String sql = baseSql + " WHERE NO = ?";
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, no);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					Student s = new Student();
					s.setNo(rs.getString("NO"));
					s.setName(rs.getString("NAME"));
					s.setEntYear(rs.getInt("ENT_YEAR"));
					s.setClassNum(rs.getString("CLASS_NUM"));
					s.setAttend(rs.getBoolean("IS_ATTEND"));

					School sch = new School();
					sch.setCd(rs.getString("SCHOOL_CD"));
					s.setSchool(sch);

					return s;
				}
			}
		}
		return null;
	}

	public List<Student> filterAllCond(School school, Integer entYear, String classNum, boolean isAttendChecked) throws Exception {
	    List<Student> list = new ArrayList<>();

	    // ベースSQL
	    StringBuilder sql = new StringBuilder(baseSql + " WHERE SCHOOL_CD = ?");
	    List<Object> params = new ArrayList<>();
	    params.add(school.getCd());

	    // 入学年度指定あり
	    if (entYear != null) {
	        sql.append(" AND ENT_YEAR = ?");
	        params.add(entYear);
	    }

	    // クラス指定あり
	    if (classNum != null && !classNum.isEmpty()) {
	        sql.append(" AND CLASS_NUM = ?");
	        params.add(classNum);
	    }

	    // 在学中チェックあり（チェックボックスON時のみ）
	    if (isAttendChecked) {
	        sql.append(" AND IS_ATTEND = TRUE");
	    }

	    try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql.toString())) {
	        for (int i = 0; i < params.size(); i++) {
	            st.setObject(i + 1, params.get(i));
	        }

	        try (ResultSet rs = st.executeQuery()) {
	            while (rs.next()) {
	                Student s = new Student();
	                s.setNo(rs.getString("NO"));
	                s.setName(rs.getString("NAME"));
	                s.setEntYear(rs.getInt("ENT_YEAR"));
	                s.setClassNum(rs.getString("CLASS_NUM"));
	                s.setAttend(rs.getBoolean("IS_ATTEND"));

	                School sch = new School();
	                sch.setCd(rs.getString("SCHOOL_CD"));
	                s.setSchool(sch);

	                list.add(s);
	            }
	        }
	    }
	    return list;
	}



	public List<Student> filterYear(School school, int entYear, boolean isAttend) throws Exception {
		String sql = baseSql + " WHERE SCHOOL_CD = ? AND ENT_YEAR = ? AND IS_ATTEND = ?";

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

	public List<Student> filterBasic(School school, boolean isAttend) throws Exception {
		String sql = baseSql + " WHERE SCHOOL_CD = ? AND IS_ATTEND = ?";

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
	 * 学生情報をデータベースに登録します。
	 *
	 * @param student
	 *            登録する学生情報
	 * @return 登録に成功した場合はtrue, 失敗した場合はfalse
	 * @throws Exception
	 * @author a_suzuki
	 */

	public boolean save(Student student) throws Exception {
		String sql = "INSERT INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD) VALUES (?, ?, ?, ?, ?, ?)";
		int count = 0;
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, student.getNo());
			st.setString(2, student.getName());
			st.setInt(3, student.getEntYear());
			st.setString(4, student.getClassNum());
			st.setBoolean(5, student.isAttend());
			st.setString(6, student.getSchool().getCd());
			// executeUpdate()は更新された行数を返す
			count = st.executeUpdate();
		}
		// 1行挿入できれば成功
		return count > 0;
	}

	/**
	 * 【追加】指定された学校に所属するクラス番号の一覧を重複なく取得します。 画面項目定義書の要件を満たすために追加しました。
	 *
	 * @param school
	 *            学校情報
	 * @return クラス番号のリスト
	 * @throws Exception
	 * @author a_suzuki
	 */
	public List<String> filterClassNum(School school) throws Exception {
		String sql = "SELECT CLASS_NUM FROM CLASS_NUM WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";
		List<String> list = new ArrayList<>();
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, school.getCd());
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					list.add(rs.getString("CLASS_NUM"));
				}
			}
		}
		return list;
	}

	public Student findByNo(String no) throws Exception {
		Student student = null;
		String sql = "SELECT * FROM STUDENT WHERE NO = ?";

		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, no);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				student = new Student();
				student.setNo(rs.getString("NO"));
				student.setName(rs.getString("NAME"));
				student.setEntYear(rs.getInt("ENT_YEAR"));
				student.setClassNum(rs.getString("CLASS_NUM"));
				student.setAttend(rs.getBoolean("IS_ATTEND"));

				// School を生成してセット
				School school = new School();
				school.setCd(rs.getString("SCHOOL_CD")); // カラム名要確認
				student.setSchool(school);

			}

		}
		return student;
	}
}