package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.ExamListSubject;
import bean.School;
import bean.Subject;

/**
 *
 * @author y_yasui
 *
 */
public class ExamListSubjectDao extends dao {

	// testテーブルとstudentテーブルのjoin

	private String baseSql = "select s.ent_year, t.class_num, s.no as student_no, s.name as student_name, t.no as test_no, t.point from student as s inner join test as t on s.no = t.student_no";

	// 検索結果の格納作業をする

	private List<ExamListSubject> postFilter(ResultSet rSet) throws Exception {

		// 学生番号をキーにして、学生情報を集約する為のMap

		Map<String, ExamListSubject> studentMap = new HashMap<>();

		try {

			while (rSet.next()) {

				// 現在の行の学生番号を取得

				String studentNo = rSet.getString("student_no");

				// 現在の行のテスト回数を取得

				int testNo = rSet.getInt("test_no");

				// 現在の行の得点を取得

				int point = rSet.getInt("point");

				// この学生がまだMapに登録されていない場合(その学生の最初のデータ)

				if (!studentMap.containsKey(studentNo)) {

					// 新しいTestListSubjectを作成

					ExamListSubject studentData = new ExamListSubject();

					// 入学年度をセット

					studentData.setEntYear(rSet.getInt("ent_year"));

					// クラス番号をセット

					studentData.setClassNum(rSet.getString("class_num"));

					// 学生番号をセット

					studentData.setStudentNo(studentNo);

					// 学生氏名をセット

					studentData.setStudentName(rSet.getString("student_name"));

					// 点数を格納するための新しいMap<回数,点数>を作成してセット

					studentData.setPoints(new HashMap<>());

					// 作成した学生データを、学生番号をキーにしてstudentMapに登録

					studentMap.put(studentNo, studentData);

				}

				// studentMapから(新規または既存の学生データを取得

				ExamListSubject currentStudent = studentMap.get(studentNo);

				// その学生の点数Mapに、今回のテスト結果を追加

				currentStudent.getPoints().put(testNo, point);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		// Mapに集約された全てのTestListSubjectオブジェクトをListに変換して返す

		return new ArrayList<>(studentMap.values());
	}

	// 入学年度、クラス番号、科目、学校で検索をかける

	public List<ExamListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

		List<ExamListSubject> list = new ArrayList<>();

		// DBに接続

		Connection connection = getConnection();

		// SQL文を準備する変数

		PreparedStatement statement = null;

		ResultSet rSet = null;

		// SQLの条件 入学年度、クラス番号、科目、学校による条件付け

		String condition = " where s.school_cd=? and s.ent_year=? and t.class_num=? and t.subject_cd=?";

		// SQL文のソート

		String order = " order by s.no asc, t.no asc";

		try {

			// SQL文をセット 学校と学生番号による絞り込み

			statement = connection.prepareStatement(

					baseSql + condition + order);

			// SQLに学校を入れる

			statement.setString(1, school.getCd());

			// SQL文に入学年度を入れる

			statement.setInt(2, entYear);

			// SQLにクラス番号を入れる

			statement.setString(3, classNum);

			// SQLに科目番号を入れる

			statement.setString(4, subject.getCd());

			// SQLの実行

			rSet = statement.executeQuery();

			// リストへの格納処理を実行

			list = postFilter(rSet);

		} catch (Exception e) {

			throw e;

		} finally {

			// SQL文の入力を終了

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException sqle) {

					throw sqle;

				}

			}

			// DBを切断

			if (connection != null) {

				try {

					connection.close();

				} catch (SQLException sqle) {

					throw sqle;

				}

			}

		}

		return list;

	}

}