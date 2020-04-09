package cn.lxyhome.jetpackcamerax.service.aibl

import android.util.Log
import cn.lxyhome.jetpackcamerax.IServiceToViewInterface
import cn.lxyhome.jetpackcamerax.IUserAidlInterface
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/4/2 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class BackBinder: IUserAidlInterface.Stub() {
    override fun insertUserInfo(username: String?,callback: IServiceToViewInterface) {
        Log.i("BackBinder",username+"")
        username?.run {
            GlobalScope.launch(Dispatchers.IO) {
                val userinfos = UserInfo(0, this@run)
                val insertUser = JetpackApplication.getUserDao()?.insertUser(userinfos)
                if (insertUser != null) {
                    callback.insertState(true, arrayListOf(userinfos))
                }else{
                    Log.i("BackBinder","isnertuser is null")
                    callback.insertState(false, arrayListOf(userinfos))
                }
            }
        }
    }

    override fun queryWhereUserName(username: String?,callback:IServiceToViewInterface) {

    }

    override fun deleteUserInfo(username: String?,callback:IServiceToViewInterface) {

    }

}