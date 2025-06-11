package bean;

import java.io.Serializable;

/**
 * 試験情報を格納するbean
 * 対応テーブル：TEST
 * jspファイルのExam.*** にそれぞれ対応
 * @author s_saito
 */
public class Exam implements Serializable {
	private Student student;
	private String classNum;
	private Subject subject;
	private School school;
	private int no;
	private int point;

	/**
	 * 
	 * @return Exam.student
	 */
	public Student getStudent() {
		return student;
	}
	/**
	 * @param student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 *  @return Exam.classNum
	 */
	public String getClassNum() {
		return classNum;
	}
	/**
	 * @param classNum
	 */
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	/**
	 *  @return Exam.subject
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * @param subject
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 *  @return Exam.school
	 */
	public School getSchool() {
		return school;
	}
	/**
	 * @param school
	 */
	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 *  @return Exam.no
	 */
	public int getNo() {
		return no;
	}
	/**
	 * @param no
	 */
	public void setNo(int no) {
		this.no = no;
	}

	/**
	 *  @return Exam.point
	 */
	public int getPoint() {
		return point;
	}
	/**
	 * @param point
	 */
	public void setPoint(int point) {
		this.point = point;
	}
}
