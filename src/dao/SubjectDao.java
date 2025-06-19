package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	            school.setCd(rs.getString("SHCOOL_CD"));  // カラム名要確認
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
        String sql = "UPDATE SUBJECT SET NAME = ? WHERE SHCOOL_CD = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, subject.getName());
            st.setString(2, subject.getSchool().toString());

            rows = st.executeUpdate();
        }
        return rows;
    }

    /**
     *
     * @param school
     * @return
     * @throws Exception
     * @author a_suzuki
     */

    public List<Subject> filter(School school) throws Exception {
        // 結果を格納するリストを初期化
        List<Subject> list = new ArrayList<>();

        // データベースリソースの変数を定義
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            // データベースに接続
            connection = getConnection();

            // SQL文を準備 (指定された学校コードの科目をすべて選択)
            String sql = "SELECT * FROM subject WHERE school_cd = ?";
            statement = connection.prepareStatement(sql);

            // プレースホルダに学校コードをセット
            statement.setString(1, school.getCd());

            // SQLを実行し、結果セットを取得
            rs = statement.executeQuery();

            // 結果セットをループ処理
            while (rs.next()) {
                // 1件分の科目データを持つSubjectオブジェクトを生成
                Subject subject = new Subject();

                // ResultSetから各カラムの値を取得し、Subjectオブジェクトにセット
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));

                // 引数で受け取ったSchoolオブジェクトをセット
                subject.setSchool(school);

                // リストにSubjectオブジェクトを追加
                list.add(subject);
            }
        } catch (Exception e) {
            // エラーが発生した場合は、呼び出し元に例外をスロー
            throw e;
        } finally {
            // データベースリソースを解放
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }

        // 取得した科目のリストを返す
        return list;
    }

}