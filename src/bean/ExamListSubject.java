package bean;

import java.io.Serializable;
import java.util.Map;



public class ExamListSubject implements Serializable {

	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private Map<Integer,Integer > points;
}
