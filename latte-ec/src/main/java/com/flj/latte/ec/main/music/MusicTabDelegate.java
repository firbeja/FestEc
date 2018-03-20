package com.flj.latte.ec.main.music;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ec.main.create.activity.ActivityDelegate;
import com.flj.latte.ec.main.create.formal.FormalDelegate;
import com.flj.latte.ec.main.create.friendly.FriendlyDelegate;
import com.flj.latte.ec.main.create.train.TrainDelegate;
import com.flj.latte.ec.main.music.list.MusicDelegate;
import com.flj.latte.ec.main.music.lyrics.LrcDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LB-john on 2018/3/20.
 */

public class MusicTabDelegate extends LatteDelegate {
    private List<String> mTitle = null;

    private List<LatteDelegate> mDelegate = null;

    @BindView(R2.id.mTabLayout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.mViewPager)
    ViewPager mViewPager = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_music_tab;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        initData();

        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDelegate.get(position);
            }

            @Override
            public int getCount() {
                return mDelegate.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);

    }

    public void initData(){

        mTitle = new ArrayList<>();
        mTitle.add("音乐列表");
        mTitle.add("歌词");

        mDelegate = new ArrayList<>();
        mDelegate.add(new MusicDelegate());
        mDelegate.add(new LrcDelegate());

    }

}
