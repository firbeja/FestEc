package com.flj.latte.ec.main.schedule.detail;

import cn.bmob.v3.BmobObject;

/**
 * Created by LB-john on 2018/3/16.
 */

public class EventsUserState extends BmobObject {

    //日程总表里 每个事件的 Id(objectId)
    private String scheduleId;

    //此时间的 此用户的 Id(objectId)
    private String userId;

    //此用户 在 此事件的 状态 例：1:报名enter 2:请假leave 3:待定pending
    private String state;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
