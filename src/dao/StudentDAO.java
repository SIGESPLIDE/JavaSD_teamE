package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDAO extends dao {
    private String baseSql = "SELECT no, name, ent_year, class_num, is_attend, school_id, school_year FROM STUDENT";

    public Student get(String no) throws SQLException {
        String sql = baseSql + " WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, no);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setNo(rs.getString("no"));
        s.setName(rs.getString("name"));
        s.setEntYear(rs.getInt("ent_year"));
        s.setClassNum(rs.getString("class_num"));
        s.setAttend(rs.getBoolean("is_attend"));
        School sch = new School();
        sch.setId(rs.getInt("school_id"));
        s.setSchool(sch);
        s.setSchoolYear(rs.getInt("school_year"));
        return s;
    }

    private List<Student> postFilter(ResultSet rs, School school) throws SQLException {
        List<Student> list = new ArrayList<>();
        while (rs.next()) {
            Student s = mapRow(rs);
            s.setSchool(school);
            list.add(s);
        }
        return list;
    }

    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws SQLException {
        String sql = baseSql + " WHERE school_id=? AND ent_year=? AND class_num=? AND is_attend=?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, school.getId());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setBoolean(4, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                return postFilter(rs, school);
            }
        }
    }

    public List<Student> filter(School school, int entYear, boolean isAttend) throws SQLException {
        String sql = baseSql + " WHERE school_id=? AND ent_year=? AND is_attend=?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, school.getId());
            st.setInt(2, entYear);
            st.setBoolean(3, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                return postFilter(rs, school);
            }
        }
    }

    public List<Student> filter(School school, boolean isAttend) throws SQLException {
        String sql = baseSql + " WHERE school_id=? AND is_attend=?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, school.getId());
            st.setBoolean(2, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                return postFilter(rs, school);
            }
        }
    }

    public boolean save(Student stu) throws SQLException {
        String sqlUpdate = "UPDATE STUDENT SET name=?, ent_year=?, class_num=?, is_attend=?, school_id=?, school_year=? WHERE no=?";
        String sqlInsert = "INSERT INTO STUDENT(no,name,ent_year,class_num,is_attend,school_id,school_year) VALUES(?,?,?,?,?,?,?)";

        try (Connection con = getConnection()) {
            PreparedStatement st;
            if (get(stu.getNo()) != null) {
                st = con.prepareStatement(sqlUpdate);
                st.setString(7, stu.getNo());
            } else {
                st = con.prepareStatement(sqlInsert);
            }
            st.setString(1, stu.getName());
            st.setInt(2, stu.getEntYear());
            st.setString(3, stu.getClassNum());
            st.setBoolean(4, stu.isAttend());
            st.setInt(5, stu.getSchool().getId());
            st.setInt(6, stu.getSchoolYear());
            int count = st.executeUpdate();
            st.close();
            return count > 0;
        }
    }

    public boolean delete(Student stu) throws SQLException {
        String sql = "DELETE FROM STUDENT WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, stu.getNo());
            return st.executeUpdate() > 0;
        }
    }
}
