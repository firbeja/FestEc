package com.flj.latte.ui.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.flj.latte.app.Latte;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.flj.latte.util.callback.IGlobalCallback;

/**
 * Created by LB-john on 2018/3/14.
 */

public final class DeleteDialog {

    public static void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Latte.getActivityContext())
                .setTitle("删除对话框")
                .setMessage("确认删除吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final IGlobalCallback callback = CallbackManager
                                .getInstance()
                                .getCallback(CallbackType.DELETE_ITEM);
                        if (callback != null) {
                            callback.executeCallback("delete");
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

}
