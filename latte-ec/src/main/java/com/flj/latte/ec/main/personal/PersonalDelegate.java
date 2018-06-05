package com.flj.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.delegates.bottom.BottomItemDelegate;
import com.flj.latte.ec.launcher.LauncherDelegate;
import com.flj.latte.ec.main.personal.list.ListAdapter;
import com.flj.latte.ec.main.personal.list.ListBean;
import com.flj.latte.ec.main.personal.list.ListItemType;
import com.flj.latte.ec.main.personal.profile.UserProfileDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;



public class PersonalDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvSettings = null;

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @OnClick(R2.id.img_user_avatar)
    void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final ListBean attendance = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setText("出勤次数")
                .build();

        final ListBean changePassword = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new ChangePasswordDelegate())
                .setText("修改密码")
                .build();

        final ListBean signOut = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setDelegate(new LauncherDelegate())
                .setText("退出登录")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(attendance);
        data.add(changePassword);
        data.add(signOut);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }
}
