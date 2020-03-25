package cn.lxyhome.jetpackcamerax.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cn.lxyhome.jetpackcamerax.dao.CardDao
import cn.lxyhome.jetpackcamerax.dao.entity.CardInfo

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/23 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */


@Database(entities = [CardInfo::class] ,version = 2,exportSchema = false)
 abstract class AppDatabase: RoomDatabase() {

    abstract fun cardDao():CardDao
}