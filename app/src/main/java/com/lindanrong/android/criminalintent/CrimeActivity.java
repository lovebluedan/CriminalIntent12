package com.lindanrong.android.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * 这个用来控制界面上的变动 控制器类
 * 直接继承SingleFragmentActivity超类
 *
 */
public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID="com.lindanrong.android.criminalintent.crime_id";
            //"com.lindanrong.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageCountext,UUID crimeId){
        Intent intent=new Intent(packageCountext,CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        //intent.putExtra(EXTRA_CRIME_ID,crimeID);
        return intent;
    }
   protected Fragment createFragment(){

       UUID crimeId = (UUID) getIntent()
               .getSerializableExtra(EXTRA_CRIME_ID);
       return CrimeFragment.newInstance(crimeId);
       //修改一：return new CrimeFragment();
   }
}
