package com.flj.latte.ec.main.schedule.detail;

import cn.bmob.v3.BmobObject;

/**
 * Created by LB-john on 2018/4/18.
 */

public class Attendance extends BmobObject{

    private String scheduleId;

    private String userId;

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
}
