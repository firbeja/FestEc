package com.flj.latte.ec.main.create.train.location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ec.detail.GoodsDetailDelegate;
import com.flj.latte.ec.main.create.HistoryType;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.util.storage.LattePreference;

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


    private static final String ARG_TAG_HISTORY = "ARG_TAG_HISTORY";
    private int mTagHistory = -1;

    public static LocationDelegate create(int tagHistory) {
        final Bundle args = new Bundle();
        args.putInt(ARG_TAG_HISTORY, tagHistory);
        final LocationDelegate delegate = new LocationDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mTagHistory = args.getInt(ARG_TAG_HISTORY);
        }
    }

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
            String historyStr = null;

            if (mTagHistory == HistoryType.OPPONENT){
                historyStr = LattePreference.getCustomAppProfile(LocationDataConverter.TAG_OPPONENT_HISTORY);
            }else if(mTagHistory == HistoryType.LOCATION){
                historyStr = LattePreference.getCustomAppProfile(LocationDataConverter.TAG_LOCATION_HISTORY);
            }else if (mTagHistory == HistoryType.COMPETITION){
                historyStr = LattePreference.getCustomAppProfile(LocationDataConverter.TAG_COMPETITION_HISTORY);
            }else if (mTagHistory == HistoryType.THEME){
                historyStr = LattePreference.getCustomAppProfile(LocationDataConverter.TAG_THEME_HISTORY);
            }


            if (StringUtils.isEmpty(historyStr)) {
                history = new ArrayList<>();
            } else {
                history = JSON.parseObject(historyStr, ArrayList.class);
            }
            history.add(item);
            final String json = JSON.toJSONString(history);

            if (mTagHistory == HistoryType.OPPONENT){
                LattePreference.addCustomAppProfile(LocationDataConverter.TAG_OPPONENT_HISTORY,json);
            }else if(mTagHistory == HistoryType.LOCATION){
                LattePreference.addCustomAppProfile(LocationDataConverter.TAG_LOCATION_HISTORY,json);
            }else if (mTagHistory == HistoryType.COMPETITION){
                LattePreference.addCustomAppProfile(LocationDataConverter.TAG_COMPETITION_HISTORY,json);
            }else if (mTagHistory == HistoryType.THEME){
                LattePreference.addCustomAppProfile(LocationDataConverter.TAG_THEME_HISTORY,json);
            }

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

        SpannableString string;
        if (mTagHistory == HistoryType.OPPONENT){
            string = new SpannableString("输入对手");
            mLocationEdit.setHint(string);
        }else if(mTagHistory == HistoryType.LOCATION){
            string = new SpannableString("输入地址");
            mLocationEdit.setHint(string);
        }else if (mTagHistory == HistoryType.COMPETITION){
            string = new SpannableString("输入比赛");
            mLocationEdit.setHint(string);
        }else if (mTagHistory == HistoryType.THEME){
            string = new SpannableString("输入主题");
            mLocationEdit.setHint(string);
        }
    }

    private void initRecycleView(){
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycleView.setLayoutManager(manager);

        mData = new LocationDataConverter(mTagHistory).convert();
        Collections.reverse(mData);
        mAdapter = new LocationAdapter(mData);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.scrollToPosition(mAdapter.getItemCount()-1);
    }

}
