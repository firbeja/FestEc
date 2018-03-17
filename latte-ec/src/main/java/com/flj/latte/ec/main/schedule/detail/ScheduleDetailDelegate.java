package com.flj.latte.ec.main.schedule.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.flj.latte.ec.main.schedule.ScheduleItemFields;
import com.flj.latte.ec.main.schedule.ScheduleType;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by LB-john on 2018/3/16.
 */

public class ScheduleDetailDelegate extends LatteDelegate implements IDetailState {

    @BindView(R2.id.rv_schedule_detail_enter_recycle_view)
    RecyclerView mEnterRecycleView = null;
    @BindView(R2.id.rv_schedule_detail_leave_recycle_view)
    RecyclerView mLeaveRecycleView = null;
    @BindView(R2.id.rv_schedule_detail_pending_recycle_view)
    RecyclerView mPendingRecycleView = null;
    @BindView(R2.id.tv_schedule_detail_enter)
    TextView tvEnter = null;
    @BindView(R2.id.tv_schedule_detail_leave)
    TextView tvLeave = null;
    @BindView(R2.id.tv_schedule_detail_pending)
    TextView tvPending = null;
    @BindView(R2.id.tv_schedule_detail_opponent)
    TextView tvOppoent = null;
    @BindView(R2.id.tv_schedule_detail_dateAndTime)
    TextView tvDateAndTime = null;
    @BindView(R2.id.tv_schedule_detail_category)
    TextView tvCategory = null;
    @BindView(R2.id.tv_schedule_detail_location)
    TextView tvLocation = null;
    @BindView(R2.id.tv_schedule_detail_summary)
    TextView tvSummary = null;
    @BindView(R2.id.detail_answered)
    TextView tvAnswerCount = null;
    @BindView(R2.id.detail_enter)
    TextView tvEnterCount = null;
    @BindView(R2.id.detail_leave)
    TextView tvLeaveCount = null;
    @BindView(R2.id.detail_pending)
    TextView tvPendingCount = null;

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
        }
    }

    @OnClick(R2.id.tv_schedule_detail_leave)
    void onCLickLeave() {

        if (!isFirst) {
            saveState(LEAVE);
        }

        if (!mState.equals(LEAVE)) {
            updateState(LEAVE);
        }
    }

    @OnClick(R2.id.tv_schedule_detail_pending)
    void onClickPending() {

        if (!isFirst) {
            saveState(PENDING);
        }

        if (!mState.equals(PENDING)) {
            updateState(PENDING);
        }
    }

    private static final String ENTER = "enter";
    private static final String LEAVE = "leave";
    private static final String PENDING = "pending";
    private static final String ARG_SCHEDULE_OBJECT_ID = "ARG_SCHEDULE_OBJECT_ID";
    private static final String ERRORMESSAGE = "errorCode:9015,errorMsg:java.lang.IndexOutOfBoundsException: Index: 0, Size: 0";

    private IDetailState DETAILSTATE;
    //事件的objectId
    private String mObjectId = "";
    private String myUserId = AccountManager.getMyUserId();
    private static String mState;
    private static String eventsUserStateObjectId;
    private static boolean isFirst;
    //回应人数
    private static int answerPeopleCount = 0;
    private int enterCount = 0;
    private int leaveCount = 0;
    private int pendingCount = 0;

    private List<EventsUserState> enterList = null;
    private List<EventsUserState> leaveList = null;
    private List<EventsUserState> pendingList = null;

    public void setListener(IDetailState iDetailState) {
        this.DETAILSTATE = iDetailState;
    }

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
        setListener(this);

        LatteLogger.d("Detail", "onBindView  ()  --- 运行了 initState() 之后"
                + "  ;  \nmObjectId: " + mObjectId
                + "  ;  \nmyUserId: " + myUserId
                + "  ;  \nmState: " + mState
                + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                + "  ;  \nisFirst: " + isFirst);

        //更新页面数据
        initDetail();

        //展示 报名 请假 待定 RecycleView
        initThreeRecycleView();

        tvAnswerCount.setText(answerPeopleCount+"");
        tvEnterCount.setText(enterCount+"");
        tvLeaveCount.setText(leaveCount+"");
        tvPendingCount.setText(pendingCount+"");

    }


    private void initThreeRecycleView() {
        BmobQuery<EventsUserState> query = new BmobQuery<>();
        query.addWhereEqualTo("scheduleId", mObjectId);
        query.findObjects(new FindListener<EventsUserState>() {
            @Override
            public void done(List<EventsUserState> list, BmobException e) {

                if (e == null) {
                    enterList = new ArrayList<EventsUserState>();
                    leaveList = new ArrayList<EventsUserState>();
                    pendingList = new ArrayList<EventsUserState>();

                    int size = list.size();
                    answerPeopleCount = size;
                    for (int i = 0; i < size; i++) {
                        EventsUserState eventsUserState = list.get(i);
                        String state = eventsUserState.getState();
                        if (state.equals(ENTER)) {
                            enterList.add(eventsUserState);
                            enterCount++;
                        } else if (state.equals(LEAVE)) {
                            leaveList.add(eventsUserState);
                            leaveCount++;
                        } else if (state.equals(PENDING)) {
                            pendingList.add(eventsUserState);
                            pendingCount++;
                        }
                    }

                    tvAnswerCount.setText(answerPeopleCount+"");
                    tvEnterCount.setText(enterCount+"");
                    tvLeaveCount.setText(leaveCount+"");
                    tvPendingCount.setText(pendingCount+"");

                    initEnterRecycle(enterList);
                    initLeaveRecycle(leaveList);
                    initPendingRecycle(pendingList);

                }
            }
        });
    }

    private void initEnterRecycle(List<EventsUserState> list) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mEnterRecycleView.setLayoutManager(manager);
        ArrayList<MultipleItemEntity> convert = new ScheduleDetailDataConverter().setListData(list).convert();
        ScheduleDetailAdapter adapter = new ScheduleDetailAdapter(convert);
        mEnterRecycleView.setAdapter(adapter);
    }

    private void initLeaveRecycle(List<EventsUserState> list) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mLeaveRecycleView.setLayoutManager(manager);
        ArrayList<MultipleItemEntity> convert = new ScheduleDetailDataConverter().setListData(list).convert();
        ScheduleDetailAdapter adapter = new ScheduleDetailAdapter(convert);
        mLeaveRecycleView.setAdapter(adapter);
    }

    private void initPendingRecycle(List<EventsUserState> list) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setAutoMeasureEnabled(true);
        mPendingRecycleView.setLayoutManager(manager);
        ArrayList<MultipleItemEntity> convert = new ScheduleDetailDataConverter().setListData(list).convert();
        ScheduleDetailAdapter adapter = new ScheduleDetailAdapter(convert);
        mPendingRecycleView.setAdapter(adapter);
    }

    private void initDetail() {

        BmobQuery<Schedule> query = new BmobQuery<>();
        query.getObject(mObjectId, new QueryListener<Schedule>() {
            @Override
            public void done(Schedule schedule, BmobException e) {
                if (e == null) {
                    String objectId = schedule.getObjectId();
                    String type = schedule.getType();
                    String category = schedule.getCategory();

                    String opponent = null;
                    if (type.equals(ScheduleType.FRIENDLY_MATCH)) {
                        opponent = schedule.getOpponent();
                    }

                    String competition = null;
                    if (type.equals(ScheduleType.FORMAL_MATCH)) {
                        opponent = schedule.getOpponent();
                        competition = schedule.getCompetition();
                    }

                    String theme = null;
                    if (type.equals(ScheduleType.ACTIVITY)) {
                        theme = schedule.getTheme();
                    }

                    String headlines = schedule.getHeadlines();

                    String duration = schedule.getDuration();
                    String location = schedule.getLocation();
                    String summary = schedule.getSummary();

                    BmobDate dateAndTime = schedule.getDateAndTime();
                    String date = dateAndTime.getDate();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    try {
                        Date format1 = format.parse(date);
                        SimpleDateFormat format2 = new SimpleDateFormat("MM月dd日  HH:mm");
                        String format3 = format2.format(format1);
                        tvDateAndTime.setText(format3);
                    } catch (ParseException ee) {
                        ee.printStackTrace();
                    }

                    tvOppoent.setText(opponent);
                    tvCategory.setText(category);
                    tvLocation.setText(location);
                    tvSummary.setText(summary);
                } else {
                    Toast.makeText(_mActivity, "获取 事件 信息失败" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //更新状态 报名 请假 待定
    private void updateState(final String userState) {
        EventsUserState eventsUserState = new EventsUserState();
        eventsUserState.setState(userState);
        eventsUserState.update(eventsUserStateObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    mState = userState;
                    Toast.makeText(_mActivity, "更新成功", Toast.LENGTH_SHORT).show();
                    initState();
                } else {
                    LatteLogger.d("aaaaaa", "e ======= " + e.toString());
                    Toast.makeText(_mActivity, "更新失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //初始化 EventUserState 表，插入一条记录 事件-用户-待定
    private void saveState(final String stateUser) {
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
                    Toast.makeText(_mActivity, "保存" + stateUser + "成功", Toast.LENGTH_SHORT).show();
                    initState();
                    LatteLogger.d("Detail", "onClickEnter() --- 1 --- state.save"
                            + "  ;  \nmObjectId: " + mObjectId
                            + "  ;  \nmyUserId: " + myUserId
                            + "  ;  \nmState: " + mState
                            + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
                            + "  ;  \nisFirst: " + isFirst);

                } else {
                    Toast.makeText(_mActivity, "保存" + stateUser + "失败", Toast.LENGTH_SHORT).show();
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
                    mState = state;
                    LatteLogger.d("onStateChange", "initState(); state: " + state
                            + "\nDETAILSTATE:" + DETAILSTATE);
                    if (DETAILSTATE != null) {
                        DETAILSTATE.onStateChange(state);
                    }
                } else {
                    isFirst = false;
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

    @Override
    public void onStateChange(String state) {

        LatteLogger.d("onStateChange", "state: " + state
                + "\nmState: " + mState);

        if (mState.equals(ENTER)) {
            tvEnter.setBackgroundResource(R.color.indianred);
            tvLeave.setBackgroundResource(R.color.dimgray);
            tvPending.setBackgroundResource(R.color.dimgray);
        }
        if (mState.equals(LEAVE)) {
            tvEnter.setBackgroundResource(R.color.dimgray);
            tvLeave.setBackgroundResource(R.color.indianred);
            tvPending.setBackgroundResource(R.color.dimgray);
        }
        if (mState.equals(PENDING)) {
            tvEnter.setBackgroundResource(R.color.dimgray);
            tvLeave.setBackgroundResource(R.color.dimgray);
            tvPending.setBackgroundResource(R.color.indianred);
        }

//            LatteLogger.d("Detail", "onClickEnter() --- 1 --- 在saveState(ENTER) 之后 ， initState()之前 --- initState()里"
//                    + "  ;  \nmObjectId: " + mObjectId
//                    + "  ;  \nmyUserId: " + myUserId
//                    + "  ;  \nmState: " + mState
//                    + "  ;  \neventsUserStateObjectId: " + eventsUserStateObjectId
//                    + "  ;  \nisFirst: " + isFirst
//                    + "  ;  \nstate: " + state);

    }

}
