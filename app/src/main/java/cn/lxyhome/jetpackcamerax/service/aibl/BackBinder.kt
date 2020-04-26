package cn.lxyhome.jetpackcamerax.service.aibl

import android.util.Log
import cn.lxyhome.jetpackcamerax.IServiceToViewInterface
import cn.lxyhome.jetpackcamerax.IUserAidlInterface
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/4/2 created.
 *""
 *
 */
class BackBinder: IUserAidlInterface.Stub() {
    override fun insertUserInfo(username: String?,callback: IServiceToViewInterface) {
        Log.i("BackBinder",username+"")
        username?.run {
            CoroutineScope(Dispatchers.IO+ CoroutineExceptionHandler{_,e->
                Log.e("CEHandler",e.toString())
            }).launch(Dispatchers.IO) {
                try {
                    val userinfos = UserInfo(0, this@run)
                    val insertUser = JetpackApplication.getUserDao()?.insertUser(userinfos)
                    if (insertUser != null) {
                        callback.insertState(true, arrayListOf(userinfos))
                    }else{
                        Log.i("BackBinder","isnertuser is null")
                        callback.insertState(false, arrayListOf(userinfos))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun queryWhereUserName(username: String?,callback:IServiceToViewInterface) {

    }

    override fun deleteUserInfo(username: String?,callback:IServiceToViewInterface) {

    }

}