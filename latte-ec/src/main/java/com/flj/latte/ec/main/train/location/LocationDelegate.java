package com.flj.latte.ec.main.train.location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ec.main.index.search.SearchDataConverter;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.util.storage.LattePreference;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LB-john on 2018/3/13.
 */

public class LocationDelegate extends LatteDelegate {

    private LocationAdapter mAdapter;
    private List<MultipleItemEntity> mData;

    public interface ILocationListener{
        void locationChange(String location);
    }

    public  ILocationListener mLocationListener = null;

    @BindView(R2.id.et_location)
    EditText mLocationEdit = null;
    @BindView(R2.id.rv_location)
    RecyclerView mRecycleView = null;

    @OnClick(R2.id.icon_location_back)
    void onClickBack(){
        getSupportDelegate().pop();
    }

    @OnClick(R2.id.tv_location_save)
    void onClickSave(){
        String location = mLocationEdit.getText().toString();
        if (mLocationListener!=null){
            mLocationListener.locationChange(location);
        }
        saveItem(location);
        mLocationEdit.setText("");

        initRecycleView();
    }

    private void saveItem(String item) {
        if (!StringUtils.isEmpty(item) && !StringUtils.isSpace(item)) {
            List<String> history;
            final String historyStr =
                    LattePreference.getCustomAppProfile(LocationDataConverter.TAG_LOCATION_HISTORY);
            if (StringUtils.isEmpty(historyStr)) {
                history = new ArrayList<>();
            } else {
                history = JSON.parseObject(historyStr, ArrayList.class);
            }
            history.add(item);
            final String json = JSON.toJSONString(history);
            LatteLogger.d(LocationDataConverter.TAG_LOCATION_HISTORY,history.toString());

            LattePreference.addCustomAppProfile(LocationDataConverter.TAG_LOCATION_HISTORY, json);
        }
    }

    public void setLocationListener(ILocationListener listener){
        this.mLocationListener = listener;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_location;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecycleView();
        mRecycleView.addOnItemTouchListener(new LocationClickListener(this));
    }

    private void initRecycleView(){
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        mRecycleView.setLayoutManager(manager);

        mData = new LocationDataConverter().convert();
        mAdapter = new LocationAdapter(mData);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.scrollToPosition(mAdapter.getItemCount()-1);
    }

}
