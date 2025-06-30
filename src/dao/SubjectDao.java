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
    */

   public class SubjectDao extends dao {

	// idで指定した科目を科目インスタンスにして一件返す
	    // 存在しなかったらnullが入る
	    public Subject get(String cd, School school) throws Exception {
	        Subject subject = new Subject();
	        // DBに接続
	        Connection connection = getConnection();
	        // SQLの準備をする変数
	        PreparedStatement statement = null;

	        try {
	            // SQL文をセット
	            statement = connection.prepareStatement("select * from subject where cd=? and school_cd=?");
	            // SQL文に科目番号を入れる
	            statement.setString(1, cd);
	            statement.setString(2, school.getCd());
	            // SQL文を実行
	            ResultSet rSet = statement.executeQuery();
	            // 学校Daoを初期化 科目インスタンスに学校コードをセットするため
	            SchoolDao schoolDao = new SchoolDao();

	            if (rSet.next()) {
	                // 検索に引っかかった科目がある場合
	                // 科目インスタンスにその検索結果をセット
	                subject.setCd(rSet.getString("cd"));
	                subject.setName(rSet.getString("name"));
	                // 検索で引っかかった科目テーブルから学校番号を持ってきて、セット
	                subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
	            } else {
	                // 検索に一件も引っかからなかった場合
	                // 科目インスタンスにnullをセット
	                subject = null;
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
	        return subject;
	    }

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

	       try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
	    	   st.setString(1, cd);
	    	   ResultSet rs = st.executeQuery();

	    	   if (rs.next()) {
	    		   subject = new Subject();
			       subject.setCd(rs.getString("CD"));
			       subject.setName(rs.getString("NAME"));

			       // School を生成してセット
			       School school = new School();
			       school.setCd(rs.getString("SCHOOL_CD")); // カラム名要確認
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
        * 科目情報を更新する
        * @param subject 更新対象の科目
        * @return 更新行数
        * @throws Exception
        */

	   public int update(Subject subject) throws Exception {
		   int rows = 0;
           String sql = "UPDATE SUBJECT SET NAME = ? WHERE SCHOOL_CD = ?"; // ← 修正済み

           try (Connection con = getConnection();
        		   PreparedStatement st = con.prepareStatement(sql)) {
        	   st.setString(1, subject.getName());
        	   st.setString(2, subject.getSchool().getCd()); // ← 修正済み（toString → getCd）
        	   rows = st.executeUpdate();

           }
           return rows;





	   }
       /**
        * 全科目を取得する
        * @return 科目リスト
        * @throws Exception
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
                   school.setCd(rs.getString("SCHOOL_CD")); // ← 修正済み
                   subject.setSchool(school);
                   list.add(subject);
	            }
	        }
	        return list;
	    }

	    /**
	     * 【★新規追加するメソッド★】
	     * 指定された学校に所属する科目のみを取得する
	     * ExamRegistController から呼び出される。
	     * @param school 絞り込み対象の学校オブジェクト
	     * @return 絞り込まれた科目リスト
	     * @throws Exception
	     */
	    public List<Subject> filter(School school) throws Exception {
	        List<Subject> list = new ArrayList<>();
	        // SQLにWHERE句を追加して学校で絞り込む
	        String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?";

	        try (Connection con = getConnection();
	             PreparedStatement st = con.prepareStatement(sql)) {

	            // プレースホルダに学校コードをセット
	            st.setString(1, school.getCd());

	            try (ResultSet rs = st.executeQuery()) {
	                while (rs.next()) {
	                    Subject subject = new Subject();
	                    subject.setCd(rs.getString("CD"));
	                    subject.setName(rs.getString("NAME"));
	                    // Schoolオブジェクトは引数で渡されたものをそのままセットできる
	                    subject.setSchool(school);
	                    list.add(subject);
	                }
			   }

		   }
		   return list;

	   }

	   /**
	    *
	    * @param subject
	    * @return
	    * @throws Exception
	    */

	   public boolean save(Subject subject) throws Exception {
	        Connection connection = getConnection();
	        PreparedStatement statement = null;
	        int count = 0; // 実行結果（更新された行数）を格納する変数

	        try {
	            // SQLインジェクション対策のため、PreparedStatementを使用
	            statement = connection.prepareStatement(
	                "INSERT INTO subject(cd, name, school_cd) VALUES(?, ?, ?)");

	            // プレースホルダ（?）に値をセット
	            statement.setString(1, subject.getCd());
	            statement.setString(2, subject.getName());
	            statement.setString(3, subject.getSchool().getCd());

	            // INSERT文を実行し、更新された行数を取得
	            count = statement.executeUpdate();

	        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
	            // 主キー(cd)が重複した場合にこの例外が発生することが多い
	            // この場合はエラーとしてログに出力し、falseを返すことでコントローラーに失敗を伝える
	            System.err.println("主キー制約違反: 科目コード " + subject.getCd() + " は既に存在します。");
	            return false; // 登録失敗
	        } catch (Exception e) {
	            // その他のSQL例外や接続エラーなど
	            e.printStackTrace();
	            throw e; // 想定外のエラーは上位に投げる
	        } finally {
	            // リソースを解放
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

   }


