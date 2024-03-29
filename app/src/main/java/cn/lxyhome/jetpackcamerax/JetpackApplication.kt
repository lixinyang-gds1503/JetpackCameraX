package cn.lxyhome.jetpackcamerax

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.work.WorkManager
import cn.lxyhome.jetpackcamerax.config.*
import cn.lxyhome.jetpackcamerax.dao.CardDao
import cn.lxyhome.jetpackcamerax.dao.UserDao
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase2
import cn.lxyhome.jetpackcamerax.notifi.NotifiManager
import cn.lxyhome.jetpackcamerax.util.toast

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/18 created.
 *""
 *
 */

class JetpackApplication:Application(),CameraXConfig.Provider {


    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    override fun onCreate() {
        super.onCreate()
        self = this
        if(Config.APP_PROCESS_NAME_2 == getCurrentProcessName()){
            AppConfig.setConfigs(AppDBConfig(applicationContext),BuglyConfig(applicationContext),
                WorkManagerConfig(applicationContext))
        } else if (Config.APP_PROCESS_NAME_1 == getCurrentProcessName()) {//多进程的时候 application 也是多个的
            AppConfig.setConfigs(AppDBConfig(applicationContext))
        }
        NotifiManager.createNotificationChannel()
        //监听activity的生命周期
        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(
                activity: Activity,
                savedInstanceState: Bundle?
            ) {//TODOLXY: lixinyang  Not yet implemented
            }

            override fun onActivityStarted(activity: Activity) {//TODOLXY: lixinyang  Not yet implemented
            }

            override fun onActivityResumed(activity: Activity) {//TODOLXY: lixinyang  Not yet implemented
            }

            override fun onActivityPaused(activity: Activity) {//TODOLXY: lixinyang  Not yet implemented
            }

            override fun onActivityStopped(activity: Activity) {//TODOLXY: lixinyang  Not yet implemented
                if (!activity.isFinishing) {
                    toast(activity.javaClass.simpleName)
                }
            }

            override fun onActivitySaveInstanceState(
                activity: Activity,
                outState: Bundle
            ) {//TODOLXY: lixinyang  Not yet implemented
            }

            override fun onActivityDestroyed(activity: Activity) {//TODOLXY: lixinyang  Not yet implemented
            }
        })
    }



    private fun getDB():AppDatabase? {
        return AppConfig.getDB()
    }

    private fun getDB2(): AppDatabase2? {
        return AppConfig.getDB2()
    }

    private fun getWorkManager(context: Context): WorkManager? {
        return AppConfig.getWorkManager(context)
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

        fun getWorkManager(context: Context): WorkManager? {
            return  self?.getWorkManager(context)
        }

        fun getCurrentProcessName():String? {
            val pid = Process.myPid()
            val mActivityManager =
               self?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (appProcess in mActivityManager
                .runningAppProcesses) {
                if (appProcess.pid == pid) {
                    return appProcess.processName
                }
            }
            return   null
        }
    }
}