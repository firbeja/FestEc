package com.flj.latte.ec.main.schedule;

import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by LB-john on 2018/3/13.
 */

public class ScheduleDataConverter extends DataConverter {




    @Override
    public ArrayList<MultipleItemEntity> convert() {
        List<Schedule> schedules = getListData();
        int size = schedules.size();
        for (int i = 0; i < size; i++) {
            Schedule schedule = schedules.get(i);

            String objectId = schedule.getObjectId();
            String type = schedule.getType();
            String category = schedule.getCategory();

            String opponent = null;
            if (type.equals(ScheduleType.FRIENDLY_MATCH)){
                opponent = schedule.getOpponent();
            }

            String competition = null;
            if (type.equals(ScheduleType.FORMAL_MATCH)){
                opponent = schedule.getOpponent();
                competition = schedule.getCompetition();
            }

            String theme = null;
            if (type.equals(ScheduleType.ACTIVITY)){
                theme = schedule.getTheme();
            }

            String headlines = schedule.getHeadlines();
            BmobDate dateAndTime = schedule.getDateAndTime();
            String duration = schedule.getDuration();
            String location = schedule.getLocation();
            String summary = schedule.getSummary();

            int itemType = -1;
            switch (type) {
                case ScheduleType.FRIENDLY_MATCH:
                    itemType = ScheduleItemType.ITEM_SCHEDULE_FRIENDLY_MATCH;
                    break;
                case ScheduleType.FORMAL_MATCH:
                    itemType = ScheduleItemType.ITEM_SCHEDULE_FORMAL_MATCH;
                    break;
                case ScheduleType.TRAIN:
                    itemType = ScheduleItemType.ITEM_SCHEDULE_TRAIN;
                    break;
                case ScheduleType.ACTIVITY:
                    itemType = ScheduleItemType.ITEM_SCHEDULE_ACTIVITY;
                    break;
                default:
                    break;
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(itemType)
                    .setField(ScheduleItemFields.objectId,objectId)
                    .setField(ScheduleItemFields.category, category)
                    .setField(ScheduleItemFields.opponent, opponent)
                    .setField(ScheduleItemFields.competition, competition)
                    .setField(ScheduleItemFields.theme, theme)
                    .setField(ScheduleItemFields.headlines, headlines)
                    .setField(ScheduleItemFields.dateAndTime, dateAndTime)
                    .setField(ScheduleItemFields.duration, duration)
                    .setField(ScheduleItemFields.location, location)
                    .setField(ScheduleItemFields.summary, summary)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
