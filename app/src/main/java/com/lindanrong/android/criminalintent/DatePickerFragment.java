package com.lindanrong.android.criminalintent;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by 林丹荣 on 2016/9/18.
 * 用来设置时间；使用AlerDialog类，属于Dialog子类
 * 使用AlerDialog类封装到DialogFragment
 *
 */
public class DatePickerFragment extends DialogFragment {

        private static final String ARG_DATE="date";
        public static final String EXTRA_DATE="package com.lindanrong.android.criminalintent.date";
        private DatePicker mDatePicker;



    public static DatePickerFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment=new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // getArguments中取出Date对象，然后使用它和Calendar对象完成DatePicker的初始化工作；
        final Date Date= (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(Date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        mDatePicker= (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year,month,day,null);
        //创建对话框
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_pricker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year=mDatePicker.getYear();
                        int month=mDatePicker.getMonth();
                        int day=mDatePicker.getDayOfMonth();
                        Date date =new GregorianCalendar(year,month,day).getTime();
                        sendResult(Activity.RESULT_OK,date);
                    }
                }).create();


    }
//创建该方法将日期数据作为extra附加到intent上去
    private void sendResult(int resultCode,Date date){
        if(getTargetFragment()==null){
            return;
        }
        Intent intent=new Intent();
        intent.putExtra(EXTRA_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
