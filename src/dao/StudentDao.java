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

    public List<Student> filterAllCond(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        String sql = baseSql + " WHERE SCHOOL_CD = ? AND ENT_YEAR = ? AND CLASS_NUM = ? AND IS_ATTEND = ?";
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
}
