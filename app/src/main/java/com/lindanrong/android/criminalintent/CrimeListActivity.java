package com.lindanrong.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by 林丹荣 on 2016/9/11.
 *
 * 这个用来控制界面上的变动 控制器类
 * 直接继承SingleFragmentActivity超类
 *
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragement();
    }
}
