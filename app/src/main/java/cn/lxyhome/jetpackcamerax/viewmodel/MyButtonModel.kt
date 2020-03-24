package cn.lxyhome.jetpackcamerax.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/19 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class MyButtonModel:ViewModel() {
    val buttonText:MutableLiveData<String> = MutableLiveData<String>().apply{
        value = "主页"
    }

}