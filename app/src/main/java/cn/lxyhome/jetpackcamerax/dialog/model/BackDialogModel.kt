package cn.lxyhome.jetpackcamerax.dialog.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.lxyhome.jetpackcamerax.entity.BackDialogEntity

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/4/1 created.
 *""
 *
 */
class BackDialogModel: ViewModel() {
    val DataModel = MutableLiveData<BackDialogEntity>().also {

        it.value = BackDialogEntity("你真的要离开么？", "再想一想", "不想了，走","http://inews.gtimg.com/newsapp_ls/0/11520531043_240180/0.jpg")
    }
}