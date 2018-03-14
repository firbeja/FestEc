package com.flj.latte.ec.main.schedule;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by LB-john on 2018/3/14.
 */


public class Schedule extends BmobObject {

    //种类 友谊赛 正式赛 训练 活动
    private String type;
    //类型
    private String category;
    //对手
    private String opponent;
    //赛事名称
    private String competition;
    //主题
    private String theme;
    //训练标题
    private String headlines;
    //日期时间
    private BmobDate dateAndTime;
    //时长
    private String duration;
    //地址
    private String location;
    //简介
    private String summary;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getHeadlines() {
        return headlines;
    }

    public void setHeadlines(String headlines) {
        this.headlines = headlines;
    }

    public BmobDate getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(BmobDate dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
