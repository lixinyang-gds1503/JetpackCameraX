package cn.lxyhome.jetpackcamerax.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import cn.lxyhome.jetpackcamerax.IUserAidlInterface

/**
 *
 */
class BackServiceConnection: ServiceConnection {
   var mbb:IUserAidlInterface? = null
    override fun onServiceDisconnected(name: ComponentName?) {

    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mbb = IUserAidlInterface.Stub.asInterface(service)
    }
}