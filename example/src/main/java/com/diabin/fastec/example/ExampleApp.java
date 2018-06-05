package com.diabin.fastec.example;

import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;

import com.diabin.fastec.example.event.ShareEvent;
import com.diabin.fastec.example.event.TestEvent;
import com.facebook.stetho.Stetho;
import com.flj.latte.app.Latte;
import com.flj.latte.ec.database.DatabaseManager;
import com.flj.latte.ec.icon.FontEcModule;
import com.flj.latte.net.interceptors.DebugInterceptor;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.flj.latte.util.callback.IGlobalCallback;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;


public class ExampleApp extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withLoaderDelayed(1000)
                .withApiHost("http://192.168.31.80:8080/RestServer/api/")
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withInterceptor(new DebugInterceptor("user_profile.php", R.raw.user))
                .withInterceptor(new DebugInterceptor("index1.php", R.raw.index))
                .withInterceptor(new DebugInterceptor("sort_list.php", R.raw.sort_list))
                .withInterceptor(new DebugInterceptor("shop_cart.php", R.raw.shop_cart))
                .withInterceptor(new DebugInterceptor("sort_content_list.php", R.raw.sort_content_list_1))
                .withInterceptor(new DebugInterceptor("shop_cart_count.php", R.raw.shop_cart))
                .withInterceptor(new DebugInterceptor("order_list.php", R.raw.order_list))
                .withInterceptor(new DebugInterceptor("address.php", R.raw.address))
                .withInterceptor(new DebugInterceptor("about.php",R.raw.about))
                .withInterceptor(new DebugInterceptor("refresh.php",R.raw.index_2_data))
                .withInterceptor(new DebugInterceptor("search.php",R.raw.search))
                .withInterceptor(new DebugInterceptor("goods_detail.php?goods_id",R.raw.goods_detail_data_1))
                .withInterceptor(new DebugInterceptor("goods_detail.php?goods_id=2",R.raw.goods_details_data_2))
                .withInterceptor(new DebugInterceptor("add_shop_cart_count1.php",R.raw.add_shop_cart_count))
                .withWeChatAppId("你的微信AppKey")
                .withWeChatAppSecret("你的微信AppSecret")
                .withJavascriptInterface("latte")
                .withWebEvent("test", new TestEvent())
                .withWebEvent("share", new ShareEvent())
                //添加Cookie同步拦截器
                .withWebHost("https://www.baidu.com/")
//                .withInterceptor(new AddCookieInterceptor())
                .withActivity(new ExampleActivity())
                .configure();
        initStetho();



        //初始化GreenDao
        DatabaseManager.getInstance().init(this);


        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            //开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
