package com.diabin.fastec.example.music;

/**
 * Created by john on 2017/11/20.
 */

public class Music {
    private String nameEnglish;
    private String nameChinese;

    public Music(String nameEnglish, String nameChinese) {
        this.nameEnglish = nameEnglish;
        this.nameChinese = nameChinese;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameChinese() {
        return nameChinese;
    }

    public void setNameChinese(String nameChinese) {
        this.nameChinese = nameChinese;
    }

    @Override
    public String toString() {
        return "Music{" +
                "nameEnglish='" + nameEnglish + '\'' +
                ", nameChinese='" + nameChinese + '\'' +
                '}';
    }
}
