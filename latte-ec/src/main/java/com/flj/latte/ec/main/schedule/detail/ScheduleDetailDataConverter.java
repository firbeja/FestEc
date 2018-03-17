package com.flj.latte.ec.main.schedule.detail;

import android.widget.Toast;

import com.flj.latte.app.Latte;
import com.flj.latte.app.MyUser;
import com.flj.latte.ec.main.schedule.ScheduleItemType;
import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LB-john on 2018/3/17.
 */

public class ScheduleDetailDataConverter extends DataConverter {

    private String username;

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        List<EventsUserState> list = getListData();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            final EventsUserState eventsUserState = list.get(i);
            final String userId = eventsUserState.getUserId();



            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(ScheduleDetailItemType.ITEM_SCHEDULEDETAIL)
                    .setField(MultipleFields.ID,userId)
                    .build();
            ENTITIES.add(entity);


        }

        return ENTITIES;
    }
}
