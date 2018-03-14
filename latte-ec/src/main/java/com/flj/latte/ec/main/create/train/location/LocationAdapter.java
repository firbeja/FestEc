package com.flj.latte.ec.main.create.train.location;

import android.support.v7.widget.AppCompatTextView;

import com.diabin.latte.ec.R;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by LB-john on 2018/3/13.
 */

public class LocationAdapter extends MultipleRecyclerAdapter {

    protected LocationAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(LocationItemType.ITEM_LOCATION, R.layout.item_location);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()){
            case LocationItemType.ITEM_LOCATION :
                final AppCompatTextView tvLocationItem = holder.getView(R.id.tv_location_item);
                final String history = entity.getField(MultipleFields.TEXT);
                tvLocationItem.setText(history);
                break;
            default:
                break;
        }
    }
}
