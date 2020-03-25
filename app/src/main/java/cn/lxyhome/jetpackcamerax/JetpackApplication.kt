package cn.lxyhome.jetpackcamerax

import android.annotation.SuppressLint
import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.room.Room
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

    private lateinit var appDatabase: AppDatabase
    private val db_name = "jetpack_db1.db"

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    override fun onCreate() {
        super.onCreate()
        self = this
        appDatabase = Room.databaseBuilder(applicationContext,AppDatabase::class.java,db_name)
            .allowMainThreadQueries()
            .addMigrations(*DBTools.getMigrations())
            .build()
    }

    public fun getDB():AppDatabase? {
        return appDatabase
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