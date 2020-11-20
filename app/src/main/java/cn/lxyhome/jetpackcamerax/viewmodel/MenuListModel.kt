package cn.lxyhome.jetpackcamerax.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/10/28 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class MenuListModel:ViewModel() {
    private var innerLiveData = MutableLiveData<ArrayList<String>>().also {
        it.value = arrayListOf("cn.lxyhome.jetpackcamerax.activity.DataStoreActivity"
            ,"cn.lxyhome.jetpackcamerax.activity.PagingTsetActivity"
            ,"cn.lxyhome.jetpackcamerax.activity.ESFStoreDetailActivity","cn.lxyhome.jetpackcamerax.activity.PorterDuffActivity")
    }
    val mLiveData:LiveData<ArrayList<String>> = innerLiveData
}