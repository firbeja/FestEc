package com.flj.latte.ec.main.create.train.location;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;

/**
 * Created by LB-john on 2018/3/13.
 */

public class LocationClickListener extends SimpleClickListener {

    private LocationDelegate DELEGATE = null;

    public LocationClickListener(LocationDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MultipleItemEntity data = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        if (DELEGATE.mLocationListener != null){
            String location = data.getField(MultipleFields.TEXT);
            DELEGATE.mLocationListener.locationChange(location);
            DELEGATE.getSupportDelegate().pop();
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
