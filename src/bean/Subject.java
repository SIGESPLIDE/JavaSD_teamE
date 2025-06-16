package bean;

import java.io.Serializable;

/**
 * 科目情報を保持するクラス。
 * 教科コード、教科名、所属学校を管理する。
 * @author k_nohara
 */
public class Subject implements Serializable {

    /** 教科コード */
    private String cd;

    /** 教科名 */
    private String name;

    /** 所属する学校情報 */
    private String school;

    /**
     * 教科コードを取得する
     * @return Subject.cd
     */
    public String getCd() {
        return cd;
    }

    /**
     * 教科コードを設定する
     * @param cd
     */
    public void setCd(String cd) {
        this.cd = cd;
    }

    /**
     * 教科名を取得する
     * @return Subject.name
     */
    public String getName() {
        return name;
    }

    /**
     * 教科名を設定する
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 所属する学校情報を取得する
     * @return Subject.school
     */
    public String getSchool() {
        return school;
    }

    /**
     * 所属する学校情報を設定する
     * @param school
     */
    public void setSchool(String school) {
        this.school = school;
    }
}
