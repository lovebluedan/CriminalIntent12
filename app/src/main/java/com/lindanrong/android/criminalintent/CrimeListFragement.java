package com.lindanrong.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Created by 林丹荣 on 2016/9/11.
 * 用来控制List方面的视图
 *显示罪恶的列表
 */
public class CrimeListFragement extends Fragment {
    //list的显示列
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //将数列里每一行要显示的东西都加载到view里面
       View view=inflater.inflate(R.layout.fragment_crime_list,container,false);

        mCrimeRecyclerView= (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }

    private void updateUI(){
        CrimeLab crimelab=CrimeLab.get(getActivity());
        List<Crime> crimes=crimelab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 实现Adapter和ViewHolder
     * 第一步：首先先定义ViewHolder内部类,用来存放View
     * ViewHolder为了itemView而生，它引用我们传给super(view)的整个view视图；
     *
     */
    private class CrimeHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
       private Crime mCrime;

       private TextView mTitleTextView;
       private TextView mDateTextView;
       private CheckBox mSolvedCheckBox;

        public CrimeHolder(View itemView) {
            super(itemView);
            //注意要设置这个 否则itemView是没有反应的
            itemView.setOnClickListener(this);

            mTitleTextView= (TextView) itemView.findViewById(R.id.List_item_crime_title_text_view);
            mDateTextView=(TextView)itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox=(CheckBox)itemView.findViewById(R.id.List_item_crime_solved_check_box);
        }
    //将模型层的数据更新到界面上来
        public void bindCrime(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }
        //设置点击事件
        @Override
        public void onClick(View v) {
            //跳转到创建事件的界面上
          // Intent intent =new Intent(getActivity(),CrimeActivity.class);
          Intent intent =CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
           startActivity(intent);
            //startActivity(intent);

        }

    }


    /**
     * 第二步：创建adapter内部类
     * 1.穿创建必要的ViewHolder
     * 2.绑定ViewHolder至模型层数据
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        //构造函数修改mCrimes的值
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        /**
         * onCreateViewHolder方法
         * 当RecycleView方法需要新的View视图来显示列表项是会调用该方法；
         * 在这个方法内部 我们创建view视图，然后用来封装到ViewHolder中。
         * 此时，RecyclerView并不要求封装视图装载数据；
         */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            //绑定list_item_crime
            View view =layoutInflater.inflate(R.layout.list_item_crime,parent,false);
            //将绑定list_item_crime防盗Holder里面
            return new CrimeHolder(view);
        }
        /**
         * 该方法会把ViewHolder的View视图和模型层数据绑定起来。
         * 收到ViewHolder和列表项在数据集中的索引位置后，我们通过索引位置找到要显示的数据进行绑定。
         * 绑定完毕，刷新显示View视图
         * @param holder
         * @param position 索引位置。所谓的索引位置，市局上就是数组中Crime的位置，
         *                 取出目标数据后，通过发送crime标题给ViewHolder的TextView视图，
         *                 这样就完成了Crime数据和View视图的绑定
         */
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
        Crime crime=mCrimes.get(position);
        holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
}