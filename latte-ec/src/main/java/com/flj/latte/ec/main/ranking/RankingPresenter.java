package com.flj.latte.ec.main.ranking;

import com.flj.latte.ec.main.schedule.detail.EventsUserState;
import com.flj.latte.util.log.LatteLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by LB-john on 2018/4/15.
 */

public class RankingPresenter {

    public static void queryEnterRanking(final IRanking iRanking) {

        final ArrayList<JSONArray> list = new ArrayList<>();

        BmobQuery<EventsUserState> query = new BmobQuery<>();
        query.addWhereEqualTo("state", "enter");
        query.groupby(new String[]{"userId"});
        query.setHasGroupCount(true);
        query.findStatistics(EventsUserState.class, new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    if (jsonArray != null) {
                        iRanking.onEnterJsonArray(jsonArray,"enter");
                    }
                } else {
                    LatteLogger.d("queryEnterRanking-enter", "e : " + e);
                }
            }
        });

        BmobQuery<EventsUserState> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("state", "leave");
        query1.groupby(new String[]{"userId"});
        query1.setHasGroupCount(true);
        query1.findStatistics(EventsUserState.class, new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    if (jsonArray != null) {
                        iRanking.onEnterJsonArray(jsonArray,"leave");
                    }
                } else {
                    LatteLogger.d("queryEnterRanking-leave", "e : " + e);
                }
            }
        });

        BmobQuery<EventsUserState> query2 = new BmobQuery<>();
        query2.addWhereEqualTo("state", "pending");
        query2.groupby(new String[]{"userId"});
        query2.setHasGroupCount(true);
        query2.findStatistics(EventsUserState.class, new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    if (jsonArray != null) {
                        iRanking.onEnterJsonArray(jsonArray,"pending");
                    }
                } else {
                    LatteLogger.d("queryEnterRanking-pending", "e : " + e);
                }
            }
        });
    }

}
