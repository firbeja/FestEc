package com.flj.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.app.MyUser;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.util.storage.LattePreference;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by LB-john on 2018/4/21.
 */

public class ChangePasswordDelegate extends LatteDelegate {

    @BindView(R2.id.et_change_old_password)
    EditText etOldPassword = null;
    @BindView(R2.id.et_change_new_password)
    EditText etNewPassword = null;
    @BindView(R2.id.et_change_renew_password)
    EditText etReNewPassword = null;
    @OnClick(R2.id.tv_change_onclick)
    void onClickChangePassword(){
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String reNewPassword = etReNewPassword.getText().toString();

        MyUser myUser = new MyUser();
        myUser.setSessionToken(LattePreference.getCustomAppProfile("Token"));
        myUser.updateCurrentUserPassword(oldPassword, reNewPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Toast.makeText(_mActivity, "修改密码成功", Toast.LENGTH_SHORT).show();
                    getSupportDelegate().pop();
                }else {
                    Toast.makeText(_mActivity, "修改密码成功", Toast.LENGTH_SHORT).show();
                    LatteLogger.d("BmobUser","e :" + e);
                }
            }
        });
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_changepassword;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
