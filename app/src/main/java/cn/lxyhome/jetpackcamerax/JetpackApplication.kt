package cn.lxyhome.jetpackcamerax

import android.annotation.SuppressLint
import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.room.Room
import cn.lxyhome.jetpackcamerax.config.AppConfig
import cn.lxyhome.jetpackcamerax.config.AppDBConfig
import cn.lxyhome.jetpackcamerax.config.Config
import cn.lxyhome.jetpackcamerax.dao.CardDao
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase
import cn.lxyhome.jetpackcamerax.dao.tools.DBTools

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
        AppConfig.setConfigs(AppDBConfig(applicationContext))
    }

     private fun getDB():AppDatabase? {
        return AppConfig.getDB()
    }


    companion object{
        @SuppressLint("StaticFieldLeak")
        var self:JetpackApplication?= null
            private set

        fun getCardDao():CardDao?{
           return self?.getDB()?.cardDao()
        }
    }
}