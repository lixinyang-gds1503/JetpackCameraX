package cn.lxyhome.jetpackcamerax.dao.tools

import androidx.room.migration.Migration

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/25 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
object DBTools {
    fun getMigrations(): Array<Migration> {
        return MigrationImpl().getMigrations()
    }
}