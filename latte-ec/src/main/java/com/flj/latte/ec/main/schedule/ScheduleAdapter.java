package com.flj.latte.ec.main.schedule;

import android.widget.TextView;

import com.diabin.latte.ec.R;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.recycler.MultipleViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by LB-john on 2018/3/14.
 */

public class ScheduleAdapter extends MultipleRecyclerAdapter {

    protected ScheduleAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ScheduleItemType.ITEM_SCHEDULE_TRAIN, R.layout.item_schedule_train);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ScheduleItemType.ITEM_SCHEDULE_TRAIN:
                BmobDate dateAndTime = entity.getField(ScheduleItemFields.dateAndTime);
                String date = dateAndTime.getDate();

                String duration = entity.getField(ScheduleItemFields.duration);
                String headlines = entity.getField(ScheduleItemFields.headlines);
                String category = entity.getField(ScheduleItemFields.category);
                String location = entity.getField(ScheduleItemFields.location);

                TextView tvDateAndTime = holder.getView(R.id.tv_schedule_date_time);
                TextView tvDuration = holder.getView(R.id.tv_schedule_duration);
                TextView tvHeadlines = holder.getView(R.id.tv_schedule_headlines);
                TextView tvCategory = holder.getView(R.id.tv_schedule_category);
                TextView tvLocation = holder.getView(R.id.tv_schedule_location);

                tvDateAndTime.setText(date);
                tvDuration.setText(duration);
                tvHeadlines.setText(headlines);
                tvCategory.setText(category);
                tvLocation.setText(location);

                break;
            default:
                break;
        }
    }
}
