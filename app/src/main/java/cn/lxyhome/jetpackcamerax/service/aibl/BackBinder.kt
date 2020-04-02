package cn.lxyhome.jetpackcamerax.service.aibl

import android.util.Log
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
    override fun callFuction(aString: String?) {
        Log.i("BackBinder",aString+"")
        aString?.run {
            GlobalScope.launch(Dispatchers.IO) {
                val insertUser = JetpackApplication.getUserDao()?.insertUser(UserInfo(0, this@run))
                if (insertUser != null) {
                    for (  k in insertUser){
                        Log.i("BackBinder",k.toString())
                    }

                }else{
                    Log.i("BackBinder","isnertuser is null")
                }
            }
        }
    }

}