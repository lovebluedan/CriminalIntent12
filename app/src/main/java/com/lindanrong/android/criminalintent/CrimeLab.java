package com.lindanrong.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 林丹荣 on 2016/9/11.
 * 这个Crimelab单例
 * 将crime数组对象将存储在一个单例里
 * 位于模型层上的CrimeLab对象，该对象是一个数据集中存储池，用来存储Crime对象
 *crime列表需要在应用的控制层新增一个activity和一个fragment：CrimeListActivity和CrimeListFragement
 */
public class CrimeLab {
  private static CrimeLab sCrimeLab;
    //在CrimeLab的构造函数里，创建一个空的List用来保存Crime对象
    //此外，再添加两个办法：getCrimes（）和getCrime（UUID）前者返回数组列表，后者返回带有指定ID的Crime对象；
    private List<Crime> mCrimes;

  //传入的Context对象14章节才会用到；
  public static CrimeLab get(Context context) {

      if (sCrimeLab == null) {
          sCrimeLab = new CrimeLab(context);
          }
        return sCrimeLab;
      }
    private CrimeLab(Context context){
        mCrimes=new ArrayList<>();
        for(int i=0;i<100;i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }
    //返回数组列表
    public List<Crime> getCrimes(){
        return mCrimes;
    }
    //返回带有指定ID的Crime对象
    public Crime getCrime(UUID id)
    {
        for (Crime crime : mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
  }


