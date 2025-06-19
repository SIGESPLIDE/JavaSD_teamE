package dao;

import bean.Student;
import bean.Subject;
import bean.School;

public class TestDao extends dao {

	public Test get(Student student, Subject subject, School school, int no) {
		
		Student students = new Student();
		Subject subjects = new Subject();
		School schools = new School();
		
		return Test;
	}
	
	public List<Test> postFilter(ResultSet rSet, School school) {
		
	}
}
