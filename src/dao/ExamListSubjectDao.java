package dao;

import java.sql.ResultSet;
import java.util.List;

import bean.ExamListSubject;
import bean.School;
import bean.Subject;

/**
 * 
 * @author s_saito, k_nohara
 *
 */
public class ExamListSubjectDao extends dao {
	
	/**
	 * 
	 * @param 工事中
	 */
	//private String baseSql = "SELECT ENT_YEAR, CLASS_NUM,  "
			
	public List<ExamListSubject> postFilter(ResultSet rSet) {
		return null;
	}
	
	public List<ExamListSubject> filter(int entYear, String classNum, Subject subject, School school) {
		return null;
	}
}
