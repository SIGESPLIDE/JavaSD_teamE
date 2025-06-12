package bean;

import java.io.Serializable;

/**
 * 学校情報を表すクラス。
 * @author k_nohara
 */
public class School implements Serializable {

    /**
     * 学校コード
     */
    private String cd;

    /**
     * 学校名
     */
    private String name;

    /**
     * 学校コードを取得する
     * @return School.cd
     */
    public String getCd() {
        return cd;
    }

    /**
     * 学校コードを設定する
     * @param cd
     */
    public void setCd(String cd) {
        this.cd = cd;
    }

    /**
     * 学校名を取得する
     * @return School.name
     */
    public String getName() {
        return name;
    }

    /**
     * 学校名を設定する
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
