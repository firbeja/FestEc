package com.flj.latte.ec.main.schedule;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ec.main.EcBottomDelegate;
import com.flj.latte.ec.main.schedule.detail.ScheduleDetailDelegate;
import com.flj.latte.ec.main.sort.SortDelegate;
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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by LB-john on 2018/3/14.
 */

public class ScheduleAdapter extends MultipleRecyclerAdapter {

    private String format3;

    private final LatteDelegate DELEGATE;

    protected ScheduleAdapter(List<MultipleItemEntity> data,LatteDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        addItemType(ScheduleItemType.ITEM_SCHEDULE_TRAIN, R.layout.item_schedule_train);
        addItemType(ScheduleItemType.ITEM_SCHEDULE_FRIENDLY_MATCH,R.layout.item_schedule_friendly);
        addItemType(ScheduleItemType.ITEM_SCHEDULE_FORMAL_MATCH,R.layout.item_schedule_friendly);
        addItemType(ScheduleItemType.ITEM_SCHEDULE_ACTIVITY,R.layout.item_schedule_activity);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ScheduleItemType.ITEM_SCHEDULE_TRAIN:
                init(holder,entity);
                String headlines = entity.getField(ScheduleItemFields.headlines);
                TextView tvHeadlines = holder.getView(R.id.tv_schedule_headlines);
                tvHeadlines.setText(headlines);
                break;
            case ScheduleItemType.ITEM_SCHEDULE_FORMAL_MATCH:
                init(holder,entity);
                String opponent = entity.getField(ScheduleItemFields.opponent);
                String competition = entity.getField(ScheduleItemFields.competition);
                TextView tvAwayName = holder.getView(R.id.tv_away_name);
                TextView tvScheduleType = holder.getView(R.id.tv_schedule_type);
                tvAwayName.setText(opponent);
                tvScheduleType.setText(competition);
                break;
            case ScheduleItemType.ITEM_SCHEDULE_FRIENDLY_MATCH:
                init(holder,entity);
                String opponent1 = entity.getField(ScheduleItemFields.opponent);
                TextView tvAwayName1 = holder.getView(R.id.tv_away_name);
                tvAwayName1.setText(opponent1);
                break;
            case ScheduleItemType.ITEM_SCHEDULE_ACTIVITY:
                init(holder,entity);
                String theme = entity.getField(ScheduleItemFields.theme);
                TextView tvScheduleTheme = holder.getView(R.id.tv_schedule_theme);
                tvScheduleTheme.setText(theme);
                break;
            default:
                break;
        }
    }

    private void init(final MultipleViewHolder holder, MultipleItemEntity entity){
        BmobDate dateAndTime = entity.getField(ScheduleItemFields.dateAndTime);
        String date = dateAndTime.getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date format1 = format.parse(date);
            SimpleDateFormat format2 = new SimpleDateFormat("MM月dd日  HH:mm");
            format3 = format2.format(format1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String duration = entity.getField(ScheduleItemFields.duration);
        String category = entity.getField(ScheduleItemFields.category);
        String location = entity.getField(ScheduleItemFields.location);

        TextView tvDateAndTime = holder.getView(R.id.tv_schedule_date_time);
        TextView tvDuration = holder.getView(R.id.tv_schedule_duration);
        TextView tvCategory = holder.getView(R.id.tv_schedule_category);
        TextView tvLocation = holder.getView(R.id.tv_schedule_location);

        tvDateAndTime.setText(format3);
        tvDuration.setText(duration);
        tvCategory.setText(category);
        tvLocation.setText(location);



        final String objectId = entity.getField(ScheduleItemFields.objectId);

        LinearLayout eventDetail = holder.getView(R.id.ll_schedule_item);
        eventDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDetailDelegate delegate = ScheduleDetailDelegate.create(objectId);
                DELEGATE.getSupportDelegate().start(delegate);
            }
        });

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
//                                Schedule schedule = new Schedule();
//                                schedule.setObjectId(objectId);
//                                schedule.delete(new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if (e==null){
//                                            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
//                                        }else {
//                                            Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
                            }
                        });
            }
        });

    }

}
