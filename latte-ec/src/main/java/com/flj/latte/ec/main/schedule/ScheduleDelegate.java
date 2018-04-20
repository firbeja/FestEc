package com.flj.latte.ec.main.schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.delegates.bottom.BottomItemDelegate;
import com.flj.latte.ec.main.schedule.refresh.ScheduleRefreshHandler;
import com.flj.latte.ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by LB-john on 2018/3/13.
 */

public class ScheduleDelegate extends BottomItemDelegate {

    @BindView(R2.id.rl_schedule)
    RecyclerView mRecycleView = null;
    @BindView(R2.id.srl_schedule_index)
    SwipeRefreshLayout mRefreshLayout = null;


    private List<MultipleItemEntity> mData;
    private ScheduleRefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_schedule;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        LatteDelegate parentDelegate = getParentDelegate();
        mRefreshHandler= ScheduleRefreshHandler.create(mRefreshLayout,mRecycleView,new ScheduleDataConverter(),parentDelegate);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage();
    }

    private void initRecyclerView() {
//        BmobQuery<Schedule> query = new BmobQuery<>();
//        query.setLimit(100).setSkip(0).order("-dateAndTime");
//        query.findObjects(new FindListener<Schedule>() {
//            @Override
//            public void done(List<Schedule> list, BmobException e) {
//                if (e == null) {
//                    mData = new ScheduleDataConverter().setListData(list).convert();
//                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
//                    mRecycleView.setLayoutManager(manager);
//                    LatteDelegate parentDelegate = getParentDelegate();
//                    ScheduleAdapter adapter = new ScheduleAdapter(mData,parentDelegate);
//                    mRecycleView.setAdapter(adapter);
//                } else {
//                    LatteLogger.d("BmobQuery", "ScheduleDelegate-----find----null " + e.toString());
//                }
//            }
//        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycleView.setLayoutManager(manager);

    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }
}
