package cn.lxyhome.jetpackcamerax.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import cn.lxyhome.jetpackcamerax.IUserAidlInterface
import cn.lxyhome.jetpackcamerax.service.aibl.BackBinder

class BackService : Service() {
    private  val mBinder:IUserAidlInterface.Stub = BackBinder()
    override fun onCreate() {
        super.onCreate()
        Log.e("backSevice","onCreate")
    }
    override fun onDestroy() {
        Log.e("backSevice","onDestroy")
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        Log.e("backSevice","onBind")
        return mBinder
    }

}
