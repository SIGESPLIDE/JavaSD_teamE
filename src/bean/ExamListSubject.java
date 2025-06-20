package bean;

import java.io.Serializable;
import java.util.Map;

/**
 * 試験の科目情報を格納するbean jspファイルのExamListSubject.*** にそれぞれ対応
 * 
 * @param entYear
 *            学生の入学した年
 * @param studentNo
 *            学生番号
 * @param studentName
 *            学生名
 * @param classNum
 *            学生の所属クラス名
 * @param points
 *            成績
 * @author s_saito
 */
public class ExamListSubject implements Serializable {
	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private Map<Integer, Integer> points;

	/**
	 * 
	 * @return ExamListSubject.entYear
	 */
	public int getEntYear() {
		return entYear;
	}

	/**
	 * 
	 * @param entYear
	 */
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

	/**
	 * 
	 * @return ExamListSubject.studentNo
	 */
	public String getStudentNo() {
		return studentNo;
	}

	/**
	 * 
	 * @param studentNo
	 */
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	/**
	 * 
	 * @return ExamListSubject.studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * 
	 * @param studentName
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * 
	 * @return ExamListSubject.classNum
	 */
	public String getClassNum() {
		return classNum;
	}

	/**
	 * 
	 * @param classNum
	 */
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	/**
	 * 
	 * @return ExamListSubject.points
	 */
	public Map<Integer, Integer> getPoints() {
		return points;
	}

	/**
	 * 
	 * @param points
	 */
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}
}
