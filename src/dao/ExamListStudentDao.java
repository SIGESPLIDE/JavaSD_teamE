package dao;

import java.sql.Connection;
import java.sql.DriverManager; // DriverManagerをインポート
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // SQLExceptionをインポート
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExamListStudent; // ExamListStudentクラスが存在すると仮󠁴
import bean.Student; // Studentクラスが存在すると仮定
import tool.CommonServlet;

public class ExamListStudentDao extends CommonServlet {

    // データベース接続情報 (適宜修正してください)
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/TEAM_E"; // データベースURL
    private static final String DB_USER = "your_username"; // データベースユーザー名
    private static final String DB_PASSWORD = "your_password"; // データベースパスワード

    // クラスのプロパティとしてbaseSqlを定義
    private String baseSql = "SELECT * FROM EXAM_LIST_STUDENT"; // テーブル名をExamListStudentに合わせて修正

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // GETリクエスト時の処理（今回は実装不要ですが、例として空のまま残します）
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // POSTリクエスト時の処理（今回は実装不要ですが、例として空のまま残します）
    }

    /**
     * ResultSetからExamListStudentのリストを生成します。
     * @param rSet データベースからのResultSet
     * @return ExamListStudentのリスト
     * @throws SQLException データベースアクセスエラー
     */
    public List<ExamListStudent> postFilter(ResultSet rSet) throws SQLException {
        List<ExamListStudent> examListStudents = new ArrayList<>();
        while (rSet.next()) {
            ExamListStudent els = new ExamListStudent();

            examListStudents.add(els);
        }
        return examListStudents;
    }

    /**
     * 指定された学生情報に基づいてExamListStudentのリストをフィルタリングします。
     * @param studentNo フィルタリングの基準となる学生番号
     * @return フィルタリングされたExamListStudentのリスト
     * @throws SQLException データベースアクセスエラー
     */
    public List<ExamListStudent> filter(String studentNo) throws SQLException {
        List<ExamListStudent> filteredList = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            // データベース接続の確立
            con = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // baseSqlに加えて、学生番号でフィルタリングする条件を追加
            String sql = baseSql + " WHERE student_no = ?"; // 適宜SQLを修正してください
            st = con.prepareStatement(sql);
            st.setString(1, studentNo); // 直接学生番号をセット

            rs = st.executeQuery();
            filteredList = postFilter(rs); // postFilterメソッドを再利用してResultSetからリストを生成

        } catch (SQLException e) {
            // エラーをログに記録するか、より詳細なハンドリングを行う
            e.printStackTrace();
            throw e; // 例外を再スロー
        } finally {
            // リソースのクローズ処理
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignore) { /* ignore */ }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ignore) { /* ignore */ }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) { /* ignore */ }
            }
        }
        return filteredList;
    }

    /**
     * 指定されたStudentオブジェクトに基づいてExamListStudentのリストをフィルタリングします。
     * (オーバーロードされたメソッド)
     * @param student フィルタリングの基準となる学生オブジェクト
     * @return フィルタリングされたExamListStudentのリスト
     * @throws SQLException データベースアクセスエラー
     */
    public List<ExamListStudent> filter(Student student) throws SQLException {
        // Studentオブジェクトから学生番号を取得し、filter(String studentNo)を呼び出す
        return filter(student.getNo());
    }
}