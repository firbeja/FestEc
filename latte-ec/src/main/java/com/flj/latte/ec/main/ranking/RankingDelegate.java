package com.flj.latte.ec.main.ranking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.app.MyUser;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.delegates.bottom.BottomItemDelegate;
import com.flj.latte.ec.main.schedule.detail.EventsUserState;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by LB-john on 2018/4/12.
 */

public class RankingDelegate extends BottomItemDelegate {

    @BindView(R2.id.rl_ranking)
    RecyclerView mRecyclerView = null;

    Map<String,JSONArray> mMap;

    @Override
    public Object setLayout() {
        return R.layout.delegate_ranking;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initData();
    }

    private void initData() {
        mMap = new HashMap<>();
        RankingPresenter.queryEnterRanking(new IRanking() {
            @Override
            public void onEnterJsonArray(JSONArray jsonArray, String state) {
                mMap.put(state,jsonArray);
                if (mMap.size()==3){
                    initRecycleView(mMap);
                }
            }
        });
    }

    private void initRecycleView(Map<String,JSONArray> map) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        List<MultipleItemEntity> data = new RankingDataConverter().setMapData(map).convert();
        RankingAdapter adapter = new RankingAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }
}
