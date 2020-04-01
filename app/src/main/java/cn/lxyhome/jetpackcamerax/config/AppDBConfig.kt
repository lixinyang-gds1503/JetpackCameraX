package cn.lxyhome.jetpackcamerax.config

import android.content.Context
import androidx.room.Room
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase
import cn.lxyhome.jetpackcamerax.dao.database.AppDatabase2
import cn.lxyhome.jetpackcamerax.dao.tools.DBTools

class AppDBConfig(context:Context):Config {
    private var mTag:Int = Config.APP_DATABASE_CONFIG_TAG
    private  val appDatabase: AppDatabase  =
        Room.databaseBuilder(context,AppDatabase::class.java, DB_NAME)
        .allowMainThreadQueries()
        .addMigrations(*DBTools.getMigrations())
        .build()

    private val appDatabase2:AppDatabase2 =
        Room.databaseBuilder(context,AppDatabase2::class.java, DB_NAME_2)
            .allowMainThreadQueries().build()

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