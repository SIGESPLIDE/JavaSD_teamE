package bean;

import java.io.Serializable;

/**
 * 学生情報を保持するクラス。
 * 学籍番号、名前、入学年度、クラス番号、出席状態、所属学校を管理する。
 * @author k_nohara
 */
public class Student implements Serializable {

    /** 学籍番号 */
    private String no;

    /** 学生の名前 */
    private String name;

    /** 入学年度 */
    private int entYear;

    /** クラス番号 */
    private String classNum;

    /** 出席しているかどうか（true: 出席、false: 欠席） */
    private boolean isAttend;

    /** 所属する学校情報（別クラス School で管理） */
    private School school;

    /**
     * 学籍番号を取得する
     * @return Student.no
     */
    public String getNo() {
        return no;
    }

    /**
     * 学籍番号を設定する
     * @param no
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * 名前を取得する
     * @return Student.name
     */
    public String getName() {
        return name;
    }

    /**
     * 名前を設定する
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 入学年度を取得する
     * @return Student.entYear
     */
    public int getEntYear() {
        return entYear;
    }

    /**
     * 入学年度を設定する
     * @param entYear
     */
    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    /**
     * クラス番号を取得する
     * @return Student.classNum
     */
    public String getClassNum() {
        return classNum;
    }

    /**
     * クラス番号を設定する
     * @param classNum
     */
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    /**
     * 出席状態を取得する（true: 出席）
     * @return Student.isAttend
     */
    public boolean isAttend() {
        return isAttend;
    }

    /**
     * 出席状態を設定する
     * @param isAttend
     */
    public void setAttend(boolean isAttend) {
        this.isAttend = isAttend;
    }

    /**
     * 所属する学校情報を取得する
     * @return Student.school
     */
    public School getSchool() {
        return school;
    }

    /**
     * 所属する学校情報を設定する
     * @param school
     */
    public void setSchool(School school) {
        this.school = school;
    }
}
