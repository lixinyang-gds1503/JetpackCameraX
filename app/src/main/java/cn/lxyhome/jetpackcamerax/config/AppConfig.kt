package cn.lxyhome.jetpackcamerax.config

import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase2

object AppConfig {
    private  var appDBConfig: AppDBConfig?=null
    fun setConfigs(vararg configs: Config) {
        configs.forEach {
            if (it.getConfigTag()==Config.APP_DATABASE_CONFIG_TAG) {
                appDBConfig = it as AppDBConfig
            }
        }
    }

    fun getDB(): AppDatabase? {
       return appDBConfig?.getDB()
    }

    fun getDB2(): AppDatabase2? {
        return appDBConfig?.getDB2()
    }
}