package com.lindanrong.android.criminalintent;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by 林丹荣 on 2016/9/18.
 * 创建左右滑动的界面，CrimePagerActivity
 * 学会用ViewPager
 * ViewPager类似于RecyclerView都是要协同Adapter才可以工作
 */
public class CrimePagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    private static final String EXTRA_CRIME_ID="com.lindanrong.android.criminalintent.crime_id";



    public static Intent newIntent(Context packaageContext,UUID crimeId){
        Intent intent=new Intent(packaageContext,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        UUID crimeId= (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        /**
         * 在activity_crime_pager视图中获取activity_crime_pager_view_pager后从CrimeLab中（crime的List）获取
         * 数据集，然后获取activity的FragmentManager实例；
         */
        mViewPager= (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        mCrimes=CrimeLab.get(this).getCrimes();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime=mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        //设置初始分页显示项目
        //循环检查Crime的ID，找到所选crime在数组中的索引位置。如果Crime实例中的mId与intent extrade 的crimeId相匹配
        //设置指定的列表项。
        for(int i=0;i<mCrimes.size();i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
