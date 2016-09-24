package com.lindanrong.android.criminalintent;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by 林丹荣 on 2016/9/10.
 * 用来与模型及视图对象交互的控制器
 * 用于显示特定的crime的明细信息，并在用户修改这些信息后立即进行更新。
 */
public class CrimeFragment extends Fragment {
    //跟模型层进行数据的交互
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private static  final int REQUEST_DATE=0;
    private static  final String ARG_CRIME_ID="crime_id";
    private static final String DIALOG_DATE="DialogDate";

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args=new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment=new CrimeFragment();
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //除了getActivity()方法的调用，获取extra数据的实现代码与activity里获取extra数据的代码是一样。
        //getIndent()返回用来启动CrimeActivity的Intent，然后调用Intent的getSerializableExtra（string）方法获取UUID并存入变量中。
        //获取Crime 的ID后，再利用它从CrimeLab单例中调用Crime对象
        UUID crimeId= (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime=CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    /**
     * onCreateView才是真正的创建fragment的方法 类似onCreat是创建Activity方法一样的道理。
     * 该方法实例化fragment视图的布局，然后将实例化的View返回给推官的activity。
     * @param inflater 实例化布局的必要参数
     * @param parent 实例化布局的必要参数
     * @param savedInstanceState 用存储回复数据，可供方法从保存状态下重建视图
     * @return 返回View对象，交托给托管的Activity
     * 此处是托管犯罪标题等的Fragment界面上的所有东西，然后在准备将Fragment托管给Activity
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fargment_crime,parent,false);
        mTitleField= (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            /**
             * CharSequence 代表用户的输入;
             */
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /**
         * 设置按钮的内容；
         * 设置它的属性为crime日期
         * 然后禁用它（意思是让它不响应客户的点击 setEnable(false)）
         */
        mDateButton= (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示DialogFragment的
                android.support.v4.app.FragmentManager manager =getFragmentManager();
               DatePickerFragment dialog=DatePickerFragment.newInstance(mCrime.getDate());
              //建立跟DatePickerFragment的联系
               dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);
            }
        });

        mSolvedCheckBox= (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //设置crime的解决性质
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode!= Activity.RESULT_OK){return;}
        if(requestCode==REQUEST_DATE){
           Date date=(Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
