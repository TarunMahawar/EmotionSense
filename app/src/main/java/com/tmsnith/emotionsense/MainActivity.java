package com.tmsnith.emotionsense;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tmsnith.emotionsense.Fragments.imageFragment;
import com.tmsnith.emotionsense.Fragments.textFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab=(TabLayout)findViewById(R.id.profile_tab);
        pager=(ViewPager)findViewById(R.id.profile_viewpager);
        pager.setOffscreenPageLimit(3);



        MyPagerAdapter adapter=new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

//        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        tab.setupWithViewPager(pager);

    }


    class MyPagerAdapter extends FragmentStatePagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            String str="Tab";
            switch (position){
                case 0: str="Text";
                    break;
                case 1: str="Image";
                    break;
            }

            return str;
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;

            switch (position){
                case 0: fragment= textFragment.get();
//                        ProfileFragment.getInstance(ProfileActivity.this,1);
                    break;
                case 1: fragment= imageFragment.get();
                    break;
                default:fragment= textFragment.get();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
