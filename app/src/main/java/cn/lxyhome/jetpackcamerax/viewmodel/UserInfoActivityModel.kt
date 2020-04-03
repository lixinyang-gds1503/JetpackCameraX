package cn.lxyhome.jetpackcamerax.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/4/3 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class UserInfoActivityModel:ViewModel() {
   private  val _userInfo = MutableLiveData<UserInfo>().also {
        it.value = UserInfo(-1,"no")
    }
    val userInfoData:LiveData<UserInfo> = _userInfo

    fun setValue(userinfo: UserInfo) {
        _userInfo.value = userinfo
    }
}