package com.flj.latte.ec.main.personal;

import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.flj.latte.app.AccountManager;
import com.flj.latte.app.Latte;
import com.flj.latte.app.MyUser;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ec.main.personal.list.ListBean;
import com.flj.latte.ec.main.schedule.detail.Attendance;
import com.flj.latte.util.log.LatteLogger;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;



public class PersonalClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    public PersonalClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                BmobQuery<Attendance> query = new BmobQuery<>();
                query.addWhereEqualTo("userId", AccountManager.getMyUserId());
                query.findObjects(new FindListener<Attendance>() {
                    @Override
                    public void done(List<Attendance> list, BmobException e) {
                        if (e == null){
                            int size = list.size();
                            Toast.makeText(Latte.getApplicationContext(), "出勤次数 ： " + size, Toast.LENGTH_SHORT).show();
                        }else {
                            LatteLogger.d("Attendance","e : " + e);
                        }
                    }
                });
                Toast.makeText(Latte.getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                break;
            case 3:
                MyUser.logOut();
                BmobUser currentUser = MyUser.getCurrentUser();
                if (currentUser == null){
                    Toast.makeText(Latte.getApplicationContext(), "退出成功", Toast.LENGTH_SHORT).show();
                    DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                }
                break;
            default:
                break;
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
