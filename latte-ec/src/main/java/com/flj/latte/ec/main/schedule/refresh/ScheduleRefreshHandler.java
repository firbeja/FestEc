package com.flj.latte.ec.main.schedule.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flj.latte.app.Latte;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ec.main.schedule.Schedule;
import com.flj.latte.ec.main.schedule.ScheduleAdapter;
import com.flj.latte.ec.main.schedule.ScheduleDataConverter;
import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.blankj.utilcode.util.Utils.getContext;


public class ScheduleRefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private ScheduleAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private final LatteDelegate DELEGATE;

    private ScheduleRefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                                   RecyclerView recyclerView,
                                   DataConverter converter, PagingBean bean, LatteDelegate parentDelegate) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
        this.DELEGATE = parentDelegate;
    }

    public static ScheduleRefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                                RecyclerView recyclerView, DataConverter converter, LatteDelegate parentDelegate) {
        return new ScheduleRefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean(), parentDelegate);
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firstPage();
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 1000);
    }

    public void firstPage() {
        BmobQuery<Schedule> query = new BmobQuery<>();
        query.setLimit(15).setSkip(0).order("-dateAndTime");
        query.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(List<Schedule> list, BmobException e) {
                if (e == null) {
                    List<MultipleItemEntity> mData;
                    mData = new ScheduleDataConverter().setListData(list).convert();
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    RECYCLERVIEW.setLayoutManager(manager);

                    mAdapter = new ScheduleAdapter(mData, DELEGATE);
                    mAdapter.setOnLoadMoreListener(ScheduleRefreshHandler.this,RECYCLERVIEW);
                    RECYCLERVIEW.setAdapter(mAdapter);
                    BEAN.addIndex();
                } else {
                    LatteLogger.d("BmobQuery", "ScheduleDelegate-----find----null " + e.toString());
                }
            }
        });
    }

    private void paging() {

        final int index = BEAN.getPageIndex();
        int skip = index*15;
        BmobQuery<Schedule> query = new BmobQuery<>();
        query.setLimit(15).setSkip(skip).order("-dateAndTime");
        query.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(List<Schedule> list, BmobException e) {
                if (e == null) {
                    CONVERTER.clearData();
                    mAdapter.addData(CONVERTER.setListData(list).convert());
                    //累加数量
                    BEAN.setCurrentCount(mAdapter.getData().size());
                    mAdapter.loadMoreComplete();
                    BEAN.addIndex();
                } else {
                    LatteLogger.d("BmobQuery", "ScheduleDelegate-----find----null " + e.toString() +"------");
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        refresh();
    }


    @Override
    public void onLoadMoreRequested() {
        paging();
    }
}
