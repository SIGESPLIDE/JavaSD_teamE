package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends dao { // 親クラスのDaoを継承

    /**
     * 【新規登録用】
     * 科目情報をデータベースに保存（INSERT）する。
     * コントローラーの`result = dao.save(subject);`から呼び出される。
     *
     * @param subject 保存する科目情報が格納されたSubjectオブジェクト
     * @return 登録に成功した場合はtrue、失敗（主キー重複など）した場合はfalseを返す
     * @throws Exception データベース接続やSQL実行に関する例外
     */
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection(); // 親クラスからDB接続を取得
        PreparedStatement statement = null;
        int count = 0; // 実行結果（挿入された行数）を格納する変数

        try {
            // SQLインジェクション対策のため、PreparedStatementを使用
            statement = connection.prepareStatement(
                "INSERT INTO subject(cd, name, school_cd) VALUES(?, ?, ?)");

            // プレースホルダ（?）に値をセット
            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getName());
            statement.setString(3, subject.getSchool().getCd());

            // INSERT文を実行し、挿入された行数を取得
            count = statement.executeUpdate();

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            // 主キー(cd)が重複した場合にこの例外が発生することが多い
            // この場合、登録は失敗しているので、falseを返すことでコントローラーに通知する
            System.err.println("主キー制約違反: 科目コード '" + subject.getCd() + "' は既に存在します。");
            return false; // 登録失敗
        } catch (Exception e) {
            // その他のSQL例外や接続エラーなど
            e.printStackTrace();
            throw e; // 想定外のエラーは呼び出し元に投げる
        } finally {
            // リソースを確実に解放
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ignore) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {}
            }
        }

        // countが1以上（1行が挿入された）であれば成功
        return count > 0;
    }

    /**
     * 科目コード(cd)をキーに科目情報を一件取得する。
     * （このメソッドは他の機能で使われる可能性があるため残しておきます）
     */
    public Subject findByCd(String cd) throws Exception {
        Subject subject = null;
        String sql = "SELECT * FROM SUBJECT WHERE CD = ?";

        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, cd);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                subject = new Subject();
                subject.setCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));

                School school = new School();
                school.setCd(rs.getString("SCHOOL_CD"));
                subject.setSchool(school);
            }
        }
        return subject;
    }


    /**
     * 全科目を取得する。
     * （このメソッドは他の機能で使われる可能性があるため残しておきます）
     */
    public List<Subject> filter() throws Exception {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM SUBJECT";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));

                School school = new School();
                school.setCd(rs.getString("SCHOOL_CD"));
                subject.setSchool(school);

                list.add(subject);
            }
        }
        return list;
    }
}