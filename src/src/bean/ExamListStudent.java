package bean;

import java.io.Serializable;

/**
 * 
 * @author s_saito
 *
 */
public class ExamListStudent implements Serializable {
	private String subjectName;
	private String subjectCd;
	private int num;
	private int point;

	/**
	 * 
	 * @return ExamListStudent.subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * 
	 * @param subjectName
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * 
	 * @return ExamListStudent.subjectCd
	 */
	public String getSubjectCd() {
		return subjectCd;
	}

	/**
	 * 
	 * @param subjectCd
	 */
	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	/**
	 * 
	 * @return ExamListStudent.num
	 */

	public int getNum() {
		return num;
	}

	/**
	 * 
	 * @param num
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * 
	 * @return ExamListStudent.point
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * 
	 * @param point
	 */
	public void setPoint(int point) {
		this.point = point;
	}
}
