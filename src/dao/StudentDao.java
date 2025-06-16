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
 * @author y_yasui
 */
public class StudentDao extends dao {
    private String baseSql = "SELECT no, name, ent_year, class_num, is_attend, school_id, school_year FROM STUDENT";

    public Student get(String no) throws Exception {
        String sql = baseSql + " WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, no);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getString("no"));
                    s.setName(rs.getString("name"));
                    s.setEntYear(rs.getInt("ent_year"));
                    s.setClassNum(rs.getString("class_num"));
                    s.setAttend(rs.getBoolean("is_attend"));

                    School sch = new School();
                    sch.setCd(rs.getString("school_id"));
                    s.setSchool(sch);

                    s.setEntYear(rs.getInt("school_year"));
                    return s;
                }
            }
        }
        return null;
    }

    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        String sql = baseSql + " WHERE school_id = ? AND ent_year = ? AND class_num = ? AND is_attend = ?";
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setBoolean(4, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getString("no"));
                    s.setName(rs.getString("name"));
                    s.setEntYear(rs.getInt("ent_year"));
                    s.setClassNum(rs.getString("class_num"));
                    s.setAttend(rs.getBoolean("is_attend"));
                    s.setSchool(school);
                    s.setEntYear(rs.getInt("school_year"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
        String sql = baseSql + " WHERE school_id = ? AND ent_year = ? AND is_attend = ?";
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setBoolean(3, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getString("no"));
                    s.setName(rs.getString("name"));
                    s.setEntYear(rs.getInt("ent_year"));
                    s.setClassNum(rs.getString("class_num"));
                    s.setAttend(rs.getBoolean("is_attend"));
                    s.setSchool(school);
                    s.setEntYear(rs.getInt("school_year"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    public List<Student> filter(School school, boolean isAttend) throws Exception {
        String sql = baseSql + " WHERE school_id = ? AND is_attend = ?";
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setBoolean(2, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getString("no"));
                    s.setName(rs.getString("name"));
                    s.setEntYear(rs.getInt("ent_year"));
                    s.setClassNum(rs.getString("class_num"));
                    s.setAttend(rs.getBoolean("is_attend"));
                    s.setSchool(school);
                    s.setEntYear(rs.getInt("school_year"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    public boolean save(Student stu) throws Exception {
        String sqlUpdate = "UPDATE STUDENT SET name=?, ent_year=?, class_num=?, is_attend=?, school_id=?, school_year=? WHERE no=?";
        String sqlInsert = "INSERT INTO STUDENT(no, name, ent_year, class_num, is_attend, school_id, school_year) VALUES(?,?,?,?,?,?,?)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 (get(stu.getNo()) != null) ? sqlUpdate : sqlInsert
             )) {
            st.setString(1, stu.getName());
            st.setInt(2, stu.getEntYear());
            st.setString(3, stu.getClassNum());
            st.setBoolean(4, stu.isAttend());
            st.setString(5, stu.getSchool().getCd());
            st.setInt(6, stu.getEntYear());
            if (st.toString().startsWith("UPDATE")) { // simple check to set no
                st.setString(7, stu.getNo());
            } else {
                st.setString(7, stu.getNo());
            }
            return st.executeUpdate() > 0;
        }
    }

    public boolean delete(Student stu) throws Exception {
        String sql = "DELETE FROM STUDENT WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, stu.getNo());
            return st.executeUpdate() > 0;
        }
    }

    /**
     *
     * @param student
     * @return
     * @throws Exception
     * @author  s_saito k_nohara
     */
    public boolean update(Student student) throws Exception {
        boolean success = false;
        // NOを条件に、NAME, CLASS_NUM, ATTENDを更新するSQL
        String sql = "UPDATE STUDENT SET NAME = ?, CLASS_NUM = ?, ATTEND = ? WHERE NO = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, student.getName());
            st.setInt(2, Integer.parseInt(student.getClassNum()));
            st.setBoolean(3, student.isAttend());
            st.setInt(4, Integer.parseInt(student.getNo())); // WHERE句の条件

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }
        }
        return success;
    }
}
