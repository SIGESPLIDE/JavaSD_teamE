package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;

/**
 * クラス番号に関するデータベースアクセスオブジェクト。
 *
 * @author s_saito
 */
public class ClassNumDao extends dao {

    /**
     * 指定された学校の全てのユニークなクラス番号を取得します。
     *
     * @param school クラス番号を取得する対象の学校オブジェクト (学校コードが必須)。
     * @return 取得したクラス番号のリスト（文字列形式）。
     * @throws Exception データベースアクセスエラーが発生した場合。
     */
    public List<String> filter(School school) throws Exception {
        List<String> classNumList = new ArrayList<>();
        // CLASS_NUM カラムは STUDENT テーブルに存在し、
        // SCHOOL_CD で絞り込む必要があると仮定します。
        // DISTINCT を使用して重複するクラス番号を除外します。
        String sql = "SELECT DISTINCT CLASS_NUM FROM STUDENT WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";

        // Schoolオブジェクトと学校コードのnullチェック
        if (school == null || school.getCd() == null || school.getCd().isEmpty()) {
            throw new IllegalArgumentException("学校オブジェクトまたは学校コードが指定されていません。");
        }

        try (Connection con = getConnection(); // 親クラスのgetConnection()メソッドを呼び出し
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd()); // プレースホルダに学校コードを設定

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    // 結果セットからクラス番号を取得し、リストに追加
                    classNumList.add(rs.getString("CLASS_NUM"));
                }
            }
        } catch (SQLException e) {
            // SQL 例外が発生した場合のログ出力や再スロー
            System.err.println("データベースエラー: クラス番号の取得に失敗しました。 " + e.getMessage());
            throw e; // 例外を再スロー
        }
        return classNumList;
    }
}