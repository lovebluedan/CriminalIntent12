package com.lindanrong.android.criminalintent;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by 林丹荣 on 2016/9/11.
 * SingleFragmentActivity用来通用的超类
 * 因为每个activity都要用到这个类所以直接调用
 * 将fragment添加到activity中，可直接调用activity的FragmentManager。
 * Fragment fragment=fm.findFragmentById(R.id.fragment_container);
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        //新建FragmentManager来管理fragment托管给activity
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment =fm.findFragmentById(R.id.fragment_container);

        if(fragment ==null){
            fragment=createFragment();
            //R.id.fragment_container是个空的fragment，fragment是带有标题的；
            //也就是说用fragment来填充R.id.fragment_container
            //R.id.fragment_container托管着CrimeFragment
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
}
