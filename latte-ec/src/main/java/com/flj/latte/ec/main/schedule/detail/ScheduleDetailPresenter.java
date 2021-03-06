package com.flj.latte.ec.main.schedule.detail;

import android.widget.Toast;

import com.flj.latte.app.Latte;
import com.flj.latte.util.log.LatteLogger;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by LB-john on 2018/4/18.
 */

public class ScheduleDetailPresenter {

    public static void saveAttendance(String scheduleId, String userId) {
        Attendance attendance = new Attendance();
        attendance.setScheduleId(scheduleId);
        attendance.setUserId(userId);
        attendance.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LatteLogger.d("attendance", s);
                    Toast.makeText(Latte.getApplicationContext(), "登记出勤成功", Toast.LENGTH_SHORT).show();
                } else {
                    LatteLogger.d("attendance", "e : " + e);
                }
            }
        });
    }

}
