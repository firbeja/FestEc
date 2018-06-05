package com.flj.latte.app;

import com.flj.latte.util.log.LatteLogger;

import cn.bmob.v3.BmobUser;



public class AccountManager {


    public static MyUser getMyUser(){
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        return myUser;
    }

    public static String getMyUserId(){
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        return myUser.getObjectId();
    }

    /**
    dd
     */
    private static boolean isSignIn() {
        MyUser currentUser = BmobUser.getCurrentUser(MyUser.class);
        if (currentUser!= null){
            LatteLogger.d("currentUser",currentUser.getPhone() + "-------");
            return true;
        }else {
            LatteLogger.d("currentUser","null-------");
            return false;
        }
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }

    public static boolean checkIsPermission(){
        String username = getMyUser().getUsername();
        Boolean flag = false;
        if (username.equals("LiuBin1")){
            flag = true;
        }
        return flag;
    }
}
