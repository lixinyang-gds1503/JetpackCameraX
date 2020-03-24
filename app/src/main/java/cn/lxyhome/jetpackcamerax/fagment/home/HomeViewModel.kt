package cn.lxyhome.jetpackcamerax.fagment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.dao.entity.CardInfo

class HomeViewModel : ViewModel() {

    val text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val Cardlist = MutableLiveData<List<CardInfo>>().apply {
       value =  JetpackApplication.getCardDao()?.queryCardAllList()
    }

}