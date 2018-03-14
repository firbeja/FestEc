package com.flj.latte.ec.main.schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.delegates.bottom.BottomItemDelegate;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LB-john on 2018/3/13.
 */

public class ScheduleDelegate extends BottomItemDelegate {

    @BindView(R2.id.rl_schedule)
    RecyclerView mRecycleView = null;
    private List<MultipleItemEntity> mData;

    @Override
    public Object setLayout() {
        return R.layout.delegate_schedule;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        BmobQuery<Schedule> query = new BmobQuery<>();
        query.setLimit(100).setSkip(1).order("-dateAndTime");
        query.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(List<Schedule> list, BmobException e) {
                if (e == null) {
                    mData = new ScheduleDataConverter().setListData(list).convert();
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    mRecycleView.setLayoutManager(manager);
                    ScheduleAdapter adapter = new ScheduleAdapter(mData);
                    mRecycleView.setAdapter(adapter);
                } else {
                    LatteLogger.d("BmobQuery", "ScheduleDelegate-----find----null " + e.toString());
                }
            }
        });

    }
}
