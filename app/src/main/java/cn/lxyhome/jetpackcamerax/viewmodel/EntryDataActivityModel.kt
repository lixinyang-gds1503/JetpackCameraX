package cn.lxyhome.jetpackcamerax.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.lxyhome.jetpackcamerax.dao.entity.CardInfo

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/24 created.
 *""
 *
 */
class EntryDataActivityModel:ViewModel() {
    val currentData:MutableLiveData<CardInfo> = MutableLiveData()
}