package com.flj.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.flj.latte.app.Latte;
import com.flj.latte.app.MyUser;
import com.flj.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.util.storage.LattePreference;
import com.flj.latte.wechat.LatteWeChat;
import com.flj.latte.wechat.callbacks.IWeChatSignInCallback;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;



public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
//        if (checkForm()) {
//            RestClient.builder()
//                    .url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")
//                    .params("email", mEmail.getText().toString())
//                    .params("password", mPassword.getText().toString())
//                    .success(new ISuccess() {
//                        @Override
//                        public void onSuccess(String response) {
//                            LatteLogger.json("USER_PROFILE", response);
//                            SignHandler.onSignIn(response, mISignListener);
//                        }
//                    })
//                    .build()
//                    .post();
//        }

        if (checkForm()){
            MyUser myUser = new MyUser();
            myUser.setUsername(mName.getText().toString());
            myUser.setPassword(mPassword.getText().toString());
            myUser.login(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    if (e == null){
                        SignHandler.onSignIn(myUser, mISignListener);
                        String sessionToken = myUser.getSessionToken();
                        LattePreference.addCustomAppProfile("Token",sessionToken);
                        Toast.makeText(Latte.getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    }else {
                        LatteLogger.d("signin","登录失败"+ e.toString());
                        Toast.makeText(Latte.getApplicationContext(), "登录失败" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat() {
        LatteWeChat
                .getInstance()
                .onSignSuccess(new IWeChatSignInCallback() {
                    @Override
                    public void onSignInSuccess(String userInfo) {
                        Toast.makeText(getContext(), userInfo, Toast.LENGTH_LONG).show();
                    }
                })
                .signIn();
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()){
            mName.setError("用户名不能为空");
            isPass = false;
        }else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
