package bean;

import java.io.Serializable;

/**
 * 教員情報を保持するクラス。 教員ID、パスワード、名前、所属学校を管理する。
 * 
 * @author k_nohara
 */

public class Teacher implements Serializable {

	/** 教員ID（ログインに使用） */
	private String id;

	/** パスワード（認証に使用） */
	private String password;

	/** 教員の名前 */
	private String name;

	/** 所属する学校情報 */
	private School school;

	/**
	 * 教員IDを取得する
	 * 
	 * @return Teacher.id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 教員IDを設定する
	 * 
	 * @param i
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * パスワードを取得する
	 * 
	 * @return Teacher.password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定する
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 教員の名前を取得する
	 * 
	 * @return Teacher.name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 教員の名前を設定する
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 所属する学校情報を取得する
	 * 
	 * @return Teacher.school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * 所属する学校情報を設定する
	 * 
	 * @param school
	 */
	public void setSchool(School school) {
		this.school = school;
	}
}
