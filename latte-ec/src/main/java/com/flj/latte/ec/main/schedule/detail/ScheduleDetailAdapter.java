package com.flj.latte.ec.main.schedule.detail;

import android.widget.TextView;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.flj.latte.app.Latte;
import com.flj.latte.app.MyUser;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.recycler.MultipleViewHolder;
import com.flj.latte.util.log.LatteLogger;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LB-john on 2018/3/17.
 */

public class ScheduleDetailAdapter extends MultipleRecyclerAdapter {
    protected ScheduleDetailAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ScheduleDetailItemType.ITEM_SCHEDULEDETAIL, R.layout.item_schedule_detail);
        LatteLogger.d("ScheduleDetailAdapter","username : " + "----");
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ScheduleDetailItemType.ITEM_SCHEDULEDETAIL:
                final String userId = entity.getField(MultipleFields.ID);
                final TextView tvUserName = holder.getView(R.id.tv_schedule_detail_item_user_name);

                BmobQuery<MyUser> query = new BmobQuery<>();
                query.addWhereEqualTo("objectId",userId);
                query.findObjects(new FindListener<MyUser>() {
                    @Override
                    public void done(List<MyUser> list, BmobException e) {
                        if (e == null){
                            for (int j = 0; j < list.size(); j++) {
                                MyUser myUser = list.get(j);
                                String username = myUser.getUsername();

                                tvUserName.setText(username);
                                LatteLogger.d("ScheduleDetailDataConverter","username : "+ username
                                        +"\nENTITIES.get(j).getField(MultipleFields.NAME : " + entity.getField(MultipleFields.NAME));
                            }
                        }else {
                            Toast.makeText(Latte.getApplicationContext(), "获取报名人数失败"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                break;
            default:
                break;
        }
    }
}
