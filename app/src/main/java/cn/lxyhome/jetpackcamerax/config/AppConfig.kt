package cn.lxyhome.jetpackcamerax.config

import android.content.Context
import androidx.work.WorkManager
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase2

object AppConfig {
    private  var appDBConfig: AppDBConfig?=null
    private var appWorkManager:WorkManagerConfig? = null
    fun setConfigs(vararg configs: Config) {
        configs.forEach {
            when {
                it.getConfigTag()==Config.APP_DATABASE_CONFIG_TAG -> {
                    appDBConfig = it as AppDBConfig
                }
                it.getConfigTag() == Config.APP_BUGLY_CONFIG_TAG -> {

                }
                it.getConfigTag() == Config.APP_WORK_MANAGER_CONFIG_TAG -> {
                    appWorkManager = it as WorkManagerConfig
                }
            }
        }
    }

    fun getDB(): AppDatabase? {
       return appDBConfig?.getDB()
    }

    fun getDB2(): AppDatabase2? {
        return appDBConfig?.getDB2()
    }

    fun getWorkManager(context: Context): WorkManager? {
        return appWorkManager?.getWorkManager(context)
    }
}