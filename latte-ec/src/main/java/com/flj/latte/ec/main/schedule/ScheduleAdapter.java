package com.flj.latte.ec.main.schedule;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.diabin.latte.ec.R;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.recycler.MultipleViewHolder;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.flj.latte.util.callback.IGlobalCallback;
import com.flj.latte.ui.dialog.DeleteDialog;
import com.flj.latte.util.log.LatteLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by LB-john on 2018/3/14.
 */

public class ScheduleAdapter extends MultipleRecyclerAdapter {

    private String format3;

    protected ScheduleAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ScheduleItemType.ITEM_SCHEDULE_TRAIN, R.layout.item_schedule_train);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ScheduleItemType.ITEM_SCHEDULE_TRAIN:
                BmobDate dateAndTime = entity.getField(ScheduleItemFields.dateAndTime);
                String date = dateAndTime.getDate();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date format1 = format.parse(date);
                    LatteLogger.d("DateFormat","format1 ===========  "+format1);
                    SimpleDateFormat format2 = new SimpleDateFormat("MM月dd日  HH:mm");
                    format3 = format2.format(format1);
                    LatteLogger.d("DateFormat","format3 -----------===========  "+ format3);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String duration = entity.getField(ScheduleItemFields.duration);
                String headlines = entity.getField(ScheduleItemFields.headlines);
                String category = entity.getField(ScheduleItemFields.category);
                String location = entity.getField(ScheduleItemFields.location);

                TextView tvDateAndTime = holder.getView(R.id.tv_schedule_date_time);
                TextView tvDuration = holder.getView(R.id.tv_schedule_duration);
                TextView tvHeadlines = holder.getView(R.id.tv_schedule_headlines);
                TextView tvCategory = holder.getView(R.id.tv_schedule_category);
                TextView tvLocation = holder.getView(R.id.tv_schedule_location);
                TextView icScheduleDelete = holder.getView(R.id.ic_schedule_delete);
                icScheduleDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DeleteDialog.show();
                        CallbackManager
                                .getInstance()
                                .addCallback(CallbackType.DELETE_ITEM, new IGlobalCallback() {
                                    @Override
                                    public void executeCallback(@Nullable Object args) {
                                        remove(holder.getLayoutPosition());
                                    }
                                });
                    }
                });

                tvDateAndTime.setText(format3);
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
