package cn.lxyhome.jetpackcamerax.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
            ,"cn.lxyhome.jetpackcamerax.activity.ESFStoreDetailActivity","cn.lxyhome.jetpackcamerax.activity.PorterDuffActivity",
            "cn.lxyhome.jetpackcamerax.activity.RecycleViewActivity","cn.lxyhome.jetpackcamerax.activity.ConstraintLayoutActivity",
        "cn.lxyhome.jetpackcamerax.activity.VectorAnimatorActivity","cn.lxyhome.jetpackcamerax.activity.MotionLayoutActivity")
    }
    val mLiveData:LiveData<ArrayList<String>> = innerLiveData
    fun test(): Unit {
        viewModelScope.launch {
            suspendTest()
        }
    }

    private suspend fun CoroutineScope.suspendTest() {
        Log.e("jetpackApp",this.toString())
    }
}