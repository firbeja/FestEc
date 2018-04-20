package com.flj.latte.ec.main.ranking;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diabin.latte.ec.R;
import com.flj.latte.app.Latte;
import com.flj.latte.app.MyUser;
import com.flj.latte.ui.recycler.ItemType;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.recycler.MultipleViewHolder;
import com.flj.latte.util.log.LatteLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.socketio.callback.StringCallback;

/**
 * Created by LB-john on 2018/4/12.
 */

public class RankingAdapter extends MultipleRecyclerAdapter {

    protected RankingAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(RankingItemType.Ranking, R.layout.item_ranking);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case RankingItemType.Ranking:
                TextView userName1 = holder.getView(R.id.tv_ranking_user_name_1);
                TextView userName2 = holder.getView(R.id.tv_ranking_user_name_2);
                TextView userName3 = holder.getView(R.id.tv_ranking_user_name_3);
                TextView userName4 = holder.getView(R.id.tv_ranking_user_name_4);
                TextView userName5 = holder.getView(R.id.tv_ranking_user_name_5);
                TextView userName6 = holder.getView(R.id.tv_ranking_user_name_6);
                TextView userName7 = holder.getView(R.id.tv_ranking_user_name_7);
                TextView userName8 = holder.getView(R.id.tv_ranking_user_name_8);
                TextView userName9 = holder.getView(R.id.tv_ranking_user_name_9);
                TextView userName10 = holder.getView(R.id.tv_ranking_user_name_10);

                JSONArray jsonArray = entity.getField(RankingItemFields.ENTERLIST);

                try {
                    JSONObject data1 = jsonArray.getJSONObject(0);
                    String userId1 = data1.getString("userId");
                    userName1.setText(userId1);

                    JSONObject data2 = jsonArray.getJSONObject(1);
                    String userId2 = data2.getString("userId");
                    userName2.setText(userId2);

                    JSONObject data3 = jsonArray.getJSONObject(2);
                    String userId3 = data3.getString("userId");
                    userName3.setText(userId3);

                    JSONObject data4 = jsonArray.getJSONObject(3);
                    String userId4 = data4.getString("userId");
                    userName4.setText(userId4);

                    JSONObject data5 = jsonArray.getJSONObject(4);
                    String userId5 = data5.getString("userId");
                    userName5.setText(userId5);

                    JSONObject data6 = jsonArray.getJSONObject(5);
                    String userId6 = data6.getString("userId");
                    userName6.setText(userId6);

                    JSONObject data7 = jsonArray.getJSONObject(6);
                    String userId7 = data7.getString("userId");
                    userName7.setText(userId7);

                    JSONObject data8 = jsonArray.getJSONObject(7);
                    String userId8 = data8.getString("userId");
                    userName8.setText(userId8);

                    JSONObject data9 = jsonArray.getJSONObject(8);
                    String userId9 = data9.getString("userId");
                    userName9.setText(userId9);

                    JSONObject data10 = jsonArray.getJSONObject(9);
                    String userId10 = data10.getString("userId");
                    userName10.setText(userId10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }



}
