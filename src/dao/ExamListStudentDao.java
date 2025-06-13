package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ExamListStudent; // ExamListStudentクラスが存在すると仮定
import bean.Student;         // Studentクラスが存在すると仮定

/**
 * 学生ごとの成績一覧を扱うためのDAOクラス。
 * DAOクラスを継承しています。
 */
public class ExamListStudentDao extends dao{

    // ベースとなるSQLクエリ
    private String baseSql = "SELECT * FROM EXAM_LIST_STUDENT"; // ビューまたはテーブル名を指定

    /**
     * ResultSetからExamListStudentのリストを生成します。
     * @param rSet データベースからのResultSet
     * @return ExamListStudentのリスト
     * @throws SQLException データベースアクセスエラー
     */
    public List<ExamListStudent> postFilter(ResultSet rSet) throws SQLException {
        List<ExamListStudent> list = new ArrayList<>();
        try {
            // ResultSetをループしてデータを取得
            while (rSet.next()) {
                ExamListStudent els = new ExamListStudent();
                // ResultSetから各列の値を取得し、ExamListStudentオブジェクトにセット
                // ※カラム名は実際のテーブル/ビュー定義に合わせて修正してください
                els.setSubjectName(rSet.getString("subject_name"));
                els.setSubjectCd(rSet.getString("subject_cd"));
                els.setNum(rSet.getInt("num"));
                els.setPoint(rSet.getInt("point"));

                list.add(els);
            }
        } catch (SQLException e) {
            // エラーをログに出力するなど、適切なエラーハンドリングを行う
            e.printStackTrace();
            throw e; // 例外を呼び出し元にスロー
        }
        return list;
    }

    /**
     * 指定された学生番号に基づいて成績一覧をフィルタリングします。
     * @param studentNo フィルタリングの基準となる学生番号
     * @return フィルタリングされたExamListStudentのリスト
     * @throws Exception データベースアクセスエラーなど
     */
    public List<ExamListStudent> filter(String studentNo) throws Exception {
        List<ExamListStudent> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            // スーパークラスDAOからデータベース接続を取得
            con = getConnection();

            // SQLクエリを準備 (学生番号で絞り込み)
            String sql = baseSql + " WHERE student_no = ?";
            st = con.prepareStatement(sql);
            st.setString(1, studentNo);

            // クエリを実行
            rs = st.executeQuery();

            // ResultSetからリストを生成
            list = postFilter(rs);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // リソースをクローズ
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 指定されたStudentオブジェクトに基づいて成績一覧をフィルタリングします。
     * (オーバーロードされたメソッド)
     * @param student フィルタリングの基準となる学生オブジェクト
     * @return フィルタリングされたExamListStudentのリスト
     * @throws Exception データベースアクセスエラーなど
     */
    public List<ExamListStudent> filter(Student student) throws Exception {
        // Studentオブジェクトから学生番号を取得し、filter(String studentNo)を呼び出す
        return filter(student.getNo());
    }
}

