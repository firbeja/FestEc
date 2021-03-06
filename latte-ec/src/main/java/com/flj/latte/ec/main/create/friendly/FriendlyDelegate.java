package com.flj.latte.ec.main.create.friendly;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.app.AccountManager;
import com.flj.latte.delegates.bottom.BottomItemDelegate;
import com.flj.latte.ec.main.create.HistoryType;
import com.flj.latte.ec.main.create.train.location.LocationDelegate;
import com.flj.latte.ec.main.schedule.Schedule;
import com.flj.latte.ec.main.schedule.ScheduleType;
import com.flj.latte.ui.date.DateDialogUtil;
import com.flj.latte.util.log.LatteLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by LB-john on 2018/3/15.
 */

public class FriendlyDelegate extends BottomItemDelegate {
    private String[] mCategory = new String[]{"羽毛球 单打", "羽毛球 双打", "其他"};
    private String[] mDuration = new String[]{"0.5小时", "1.0小时", "1.5小时", "2.0小时", "2.5小时", "3.0小时", "3.5小时", "4.0小时", "4.5小时", "5.0小时"};

    @BindView(R2.id.tv_train_category)
    TextView tvTrainCategory = null;

    @BindView(R2.id.tv_train_opponent)
    TextView tvTrainOpponent = null;
    @BindView(R2.id.tv_train_date)
    TextView tvTrainDate = null;
    @BindView(R2.id.tv_train_time)
    TextView tvTrainTime = null;
    @BindView(R2.id.tv_train_duration)
    TextView tvTrainDuration = null;
    @BindView(R2.id.tv_train_location)
    TextView tvTrainLocation = null;
    @BindView(R2.id.et_train_summary)
    EditText etTrainSummary = null;


    @OnClick(R2.id.rl_train_category)
    void onClickCategory() {
        getChoicesDialog(mCategory, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = mCategory[which];
                tvTrainCategory.setText(category);
                dialog.dismiss();
            }
        });
    }



    @OnClick(R2.id.rl_train_opponent)
    void onClickOpponent() {
        LocationDelegate delegate = LocationDelegate.create(HistoryType.OPPONENT);
        delegate.setLocationListener(new LocationDelegate.ILocationListener() {
            @Override
            public void locationChange(String location) {
                tvTrainOpponent.setText(location);
            }
        });
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @OnClick(R2.id.rl_train_date)
    void onClickDate() {
        final DateDialogUtil dateDialogUtil = new DateDialogUtil();
        dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
            @Override
            public void onDateChange(String date) {
                tvTrainDate.setText(date);
            }
        });
        dateDialogUtil.showDateDialog(getContext());
    }

    @OnClick(R2.id.rl_train_time)
    void onClickTime() {
        final DateDialogUtil dateDialogUtil = new DateDialogUtil();
        dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
            @Override
            public void onDateChange(String date) {
                tvTrainTime.setText(date);
            }
        });
        dateDialogUtil.showTimeDialog(getContext());
    }

    @OnClick(R2.id.rl_train_duration)
    void onClickDuration() {
        getChoicesDialog(mDuration, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = mDuration[which];
                tvTrainDuration.setText(category);
                dialog.dismiss();
            }
        });
    }

    @OnClick(R2.id.rl_train_location)
    void onClickLocation() {
        LocationDelegate delegate = LocationDelegate.create(HistoryType.LOCATION);
        delegate.setLocationListener(new LocationDelegate.ILocationListener() {
            @Override
            public void locationChange(String location) {
                tvTrainLocation.setText(location);
            }
        });
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @OnClick(R2.id.btn_train_create)
    void onClickTrainCreate() {
        if (AccountManager.checkIsPermission()){
            TrainCreate();
        }
    }

    public void TrainCreate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String category = tvTrainCategory.getText().toString();
        String date = tvTrainDate.getText().toString();
        String time = tvTrainTime.getText().toString();
        String dateAndTime = date + " " + time;
        String duration = tvTrainDuration.getText().toString();
        String location = tvTrainLocation.getText().toString();
        String summary = etTrainSummary.getText().toString();

        String opponent = tvTrainOpponent.getText().toString();

        Schedule schedule = new Schedule();
        try {
            Date date1 = dateFormat.parse(dateAndTime);
            BmobDate bmobDate = new BmobDate(date1);
            schedule.setDateAndTime(bmobDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        schedule.setType(ScheduleType.FRIENDLY_MATCH);
        schedule.setCategory(category);
        schedule.setDuration(duration);
        schedule.setLocation(location);
        schedule.setSummary(summary);

        schedule.setOpponent(opponent);

        schedule.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    LatteLogger.d("Train Save", "=======   " + e.toString() + "----------------- " + s);
                } else {
                    LatteLogger.d("aaa");
                }
            }
        });
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_friendly;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    private void getChoicesDialog(String[] strings, DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setSingleChoiceItems(strings, 0, listener);
        builder.show();
    }
}
