package cn.lxyhome.jetpackcamerax.config

import android.content.Context
import androidx.room.Room
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase2
import cn.lxyhome.jetpackcamerax.dao.tools.DBTools

class AppDBConfig(context:Context):Config {
    private var mTag:Int = Config.APP_DATABASE_CONFIG_TAG
    private lateinit var  appDatabase: AppDatabase
    private lateinit var appDatabase2:AppDatabase2

    init {
        val currentProcessName = JetpackApplication.getCurrentProcessName()
        if (Config.APP_PROCESS_NAME_1== currentProcessName) {
             appDatabase2 = Room.databaseBuilder(context,AppDatabase2::class.java, DB_NAME_2)
                .allowMainThreadQueries().build()
        }else if (Config.APP_PROCESS_NAME_2 ==currentProcessName) {
            appDatabase = Room.databaseBuilder(context,AppDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .addMigrations(*DBTools.getMigrations())
                .build()
            appDatabase2 = Room.databaseBuilder(context,AppDatabase2::class.java, DB_NAME_2)
                .allowMainThreadQueries().build()
        }
    }


    fun getDB():AppDatabase {
        return appDatabase
    }
    fun getDB2():AppDatabase2{
        return appDatabase2
    }

    override fun getConfigTag(): Int {
         return mTag
    }

    companion object{
        const val DB_NAME="jetpack_db1.db"
        const val DB_NAME_2="jetpack_db2.db"
    }
}