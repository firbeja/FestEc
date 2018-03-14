package com.flj.latte.ec.sign;

import com.flj.latte.app.AccountManager;
import com.flj.latte.app.MyUser;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class SignHandler {

    public static void onSignIn(MyUser myUser, ISignListener signListener) {
        final long studentId = (long) myUser.getStudentId();
        final String objectId = myUser.getObjectId();
        final String name = myUser.getUsername();
        final String avatar = myUser.getAvatar();
        final String gender = myUser.getGender();
        final String address = myUser.getAddress();

//        final UserProfile profile = new UserProfile(studentId,objectId, name, avatar, gender, address);
//        DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册并登录成功了
        signListener.onSignInSuccess();
    }


    public static void onSignUp(MyUser myUser, ISignListener signListener) {
        final long studentId = (long) myUser.getStudentId();
        final String objectId = myUser.getObjectId();
        final String name = myUser.getUsername();
        final String avatar = myUser.getAvatar();
        final String gender = myUser.getGender();
        final String address = myUser.getAddress();

//        final UserProfile profile = new UserProfile(studentId,objectId, name, avatar, gender, address);
//        DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册并登录成功了
        signListener.onSignUpSuccess();
    }
}
