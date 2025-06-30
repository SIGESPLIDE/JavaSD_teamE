package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;

public class SchoolDao extends dao {
	// idで指定された学校インスタンスを返す
    // 存在しなかったらnullが返る
    public School get(String no) throws Exception {
        School school = new School();
        // DBに接続
        Connection connection = getConnection();
        // SQLの準備をする変数
        PreparedStatement statement = null;

        try {
            // SQL文をセット 学校番号で絞り込み
            statement = connection.prepareStatement("select * from school where cd=?");
            // SQL文に学校番号を入れる
            statement.setString(1, no);
            // SQL文を実行
            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                // 検索に引っかかる学校があった場合
                // 学校インスタンスに検索結果をセット
                school.setCd(rSet.getString("cd"));
                school.setName(rSet.getString("name"));
            } else {
                // 検索に一件も引っかからなかった場合
                // 学校インスタンスにnullをセット
                school = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // SQL文を終了
            if (statement != null) {
                try{
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            // DBを切断
            if (connection != null) {
                try{
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return school;
    }
}
