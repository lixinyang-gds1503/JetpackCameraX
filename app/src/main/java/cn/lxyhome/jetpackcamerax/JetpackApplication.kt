package cn.lxyhome.jetpackcamerax

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import cn.lxyhome.jetpackcamerax.config.AppConfig
import cn.lxyhome.jetpackcamerax.config.AppDBConfig
import cn.lxyhome.jetpackcamerax.dao.CardDao
import cn.lxyhome.jetpackcamerax.dao.UserDao
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase2

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/18 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class JetpackApplication:Application(),CameraXConfig.Provider {


    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    override fun onCreate() {
        super.onCreate()
        self = this
        if("cn.lxyhome.jetpackcamerax" == getCurrentProcessName()){
            AppConfig.setConfigs(AppDBConfig(applicationContext))
        }
    }

    private fun getCurrentProcessName():String? {
        val pid = Process.myPid()
        val mActivityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in mActivityManager
            .runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return   null
    }

    private fun getDB():AppDatabase? {
        return AppConfig.getDB()
    }

    private fun getDB2(): AppDatabase2? {
        return AppConfig.getDB2()
    }


    companion object{
        @SuppressLint("StaticFieldLeak")
        var self:JetpackApplication?= null
            private set

        fun getCardDao():CardDao?{
           return self?.getDB()?.cardDao()
        }
        fun getUserDao(): UserDao? {
            return  self?.getDB2()?.userDao()
        }
    }
}