package com.flj.latte.ec.main.schedule.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.app.AccountManager;
import com.flj.latte.app.MyUser;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ec.main.schedule.Schedule;
import com.flj.latte.util.log.LatteLogger;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by LB-john on 2018/3/16.
 */

public class ScheduleDetailDelegate extends LatteDelegate {

    @BindView(R2.id.tv_schedule_detail_enter)
    TextView tvEnter = null;
    @BindView(R2.id.tv_schedule_detail_leave)
    TextView tvLeave = null;
    @BindView(R2.id.tv_schedule_detail_pending)
    TextView tvPending = null;

    @OnClick(R2.id.tv_schedule_detail_enter)
    void onClickEnter() {

        LatteLogger.d("Detail", "onClickEnter() --- 1"
                + "  ;  \nmObjectId: " + mObjectId
                + "  ;  \nmyUserId: " + myUserId
                + "  ;  \nmState: " + mState
                + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                + "  ;  \nisFirst: " + isFirst);

        if (!isFirst) {

            LatteLogger.d("Detail", "onClickEnter() --- 1 --- if (!isFirst)"
                    + "  ;  \nmObjectId: " + mObjectId
                    + "  ;  \nmyUserId: " + myUserId
                    + "  ;  \nmState: " + mState
                    + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                    + "  ;  \nisFirst: " + isFirst);

            saveState(ENTER);

            LatteLogger.d("Detail", "onClickEnter() --- 1 --- 在saveState(ENTER) 之后 ， initState()之前"
                    + "  ;  \nmObjectId: " + mObjectId
                    + "  ;  \nmyUserId: " + myUserId
                    + "  ;  \nmState: " + mState
                    + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                    + "  ;  \nisFirst: " + isFirst);

            initState();

            LatteLogger.d("Detail", "onClickEnter() --- 1 --- initState()之后"
                    + "  ;  \nmObjectId: " + mObjectId
                    + "  ;  \nmyUserId: " + myUserId
                    + "  ;  \nmState: " + mState
                    + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                    + "  ;  \nisFirst: " + isFirst);

        }



        if (!mState.equals(ENTER)) {
            //更新 EventUserState 表 的状态，更新为 报名 enter
            updateState(ENTER);
            initState();
        }
    }

    @OnClick(R2.id.tv_schedule_detail_leave)
    void onCLickLeave() {

        if (!isFirst) {
            saveState(ENTER);
            initState();
        }

        if (!mState.equals(LEAVE)) {
            updateState(LEAVE);
            initState();
        }
    }

    @OnClick(R2.id.tv_schedule_detail_pending)
    void onClickPending() {

        if (!isFirst) {
            saveState(ENTER);
            initState();
        }

        if (!mState.equals(PENDING)) {
            updateState(PENDING);
            initState();
        }
    }

    private static final String ENTER = "enter";
    private static final String LEAVE = "leave";
    private static final String PENDING = "pending";
    private static final String ARG_SCHEDULE_OBJECT_ID = "ARG_SCHEDULE_OBJECT_ID";
    private static final String ERRORMESSAGE = "errorCode:9015,errorMsg:java.lang.IndexOutOfBoundsException: Index: 0, Size: 0";

    //事件的objectId
    private String mObjectId = "";
    private String myUserId = AccountManager.getMyUserId();
    private static String mState;
    private static String eventsUserStateObjectId;
    private static boolean isFirst;

    public static ScheduleDetailDelegate create(String objectId) {
        final Bundle args = new Bundle();
        args.putString(ARG_SCHEDULE_OBJECT_ID, objectId);
        final ScheduleDetailDelegate delegate = new ScheduleDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mObjectId = args.getString(ARG_SCHEDULE_OBJECT_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_schedule_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        LatteLogger.d("Detail", "onBindView  ()"
                + "  ;  \nmObjectId: " + mObjectId
                + "  ;  \nmyUserId: " + myUserId
                + "  ;  \nmState: " + mState
                + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                + "  ;  \nisFirst: " + isFirst);

        initState();

        LatteLogger.d("Detail", "onBindView  ()  --- 运行了 initState() 之后"
                + "  ;  \nmObjectId: " + mObjectId
                + "  ;  \nmyUserId: " + myUserId
                + "  ;  \nmState: " + mState
                + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                + "  ;  \nisFirst: " + isFirst);
    }

    //更新状态 报名 请假 待定
    private void updateState(String userState) {
        EventsUserState eventsUserState = new EventsUserState();
        eventsUserState.setState(userState);
        eventsUserState.update(eventsUserStateObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(_mActivity, "操作成功", Toast.LENGTH_SHORT).show();
                } else {
                    LatteLogger.d("aaaaaa", "e ======= " + e.toString());
                    Toast.makeText(_mActivity, "操作失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //初始化 EventUserState 表，插入一条记录 事件-用户-待定
    private void saveState(String stateUser) {
        mState = stateUser;
        EventsUserState state = new EventsUserState();
        state.setScheduleId(mObjectId);
        if (myUserId != null && !myUserId.isEmpty()) {
            state.setUserId(myUserId);
        }
        state.setState(stateUser);
        state.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    isFirst = true;
                    eventsUserStateObjectId = s;
                    Toast.makeText(_mActivity, "报名成功", Toast.LENGTH_SHORT).show();

                    LatteLogger.d("Detail", "onClickEnter() --- 1 --- state.save"
                            + "  ;  \nmObjectId: " + mObjectId
                            + "  ;  \nmyUserId: " + myUserId
                            + "  ;  \nmState: " + mState
                            + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                            + "  ;  \nisFirst: " + isFirst);

                } else {
                    Toast.makeText(_mActivity, "报名失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //获得 报名 请假 待定 的状态
    private void initState() {
        BmobQuery<EventsUserState> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("scheduleId", mObjectId);
        bmobQuery.addWhereEqualTo("userId", myUserId);
        bmobQuery.findObjects(new FindListener<EventsUserState>() {
            @Override
            public void done(List<EventsUserState> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 1) {
                        LatteLogger.d("ScheduleDetailDelegate", "EventsUserState 里相同事件 相同用户 多余 一条记录");
                    }
                    EventsUserState eventsUserState = list.get(0);
                    eventsUserStateObjectId = eventsUserState.getObjectId();
                    final String state = eventsUserState.getState();
                    if (state.equals(ENTER)) {
                        tvEnter.setBackgroundResource(R.color.indianred);
                        tvLeave.setBackgroundResource(R.color.dimgray);
                        tvPending.setBackgroundResource(R.color.dimgray);
                    }
                    if (state.equals(LEAVE)) {
                        tvEnter.setBackgroundResource(R.color.dimgray);
                        tvLeave.setBackgroundResource(R.color.indianred);
                        tvPending.setBackgroundResource(R.color.dimgray);
                    }
                    if (state.equals(PENDING)) {
                        tvEnter.setBackgroundResource(R.color.dimgray);
                        tvLeave.setBackgroundResource(R.color.dimgray);
                        tvPending.setBackgroundResource(R.color.indianred);
                    }
                    mState = state;

                    LatteLogger.d("Detail", "onClickEnter() --- 1 --- 在saveState(ENTER) 之后 ， initState()之前 --- initState()里"
                            + "  ;  \nmObjectId: " + mObjectId
                            + "  ;  \nmyUserId: " + myUserId
                            + "  ;  \nmState: " + mState
                            + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                            + "  ;  \nisFirst: " + isFirst
                            + "  ;  \nstate: " + state);

                } else {
                    Toast.makeText(_mActivity, "未报名", Toast.LENGTH_SHORT).show();
//                    if (ERRORMESSAGE.equals(e.toString())){
//                        saveState(PENDING);
//                        initState();
//                        Toast.makeText(_mActivity, "未报名,插入待定状态"+e.toString(), Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
    }
}
