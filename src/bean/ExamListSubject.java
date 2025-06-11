package bean;

import java.io.Serializable;
import java.util.Map;


/**
 * 試験の科目情報を格納するbean
 * jspファイルのExamListSubject.*** にそれぞれ対応
 *  * @author s_saito
 */
public class ExamListSubject implements Serializable {
	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private Map<Integer,Integer > points;

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
