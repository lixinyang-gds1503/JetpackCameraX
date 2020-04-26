package cn.lxyhome.jetpackcamerax.dao.tools

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/25 created.
 *""
 *
 */
class MigrationImpl {
    fun getMigrations(): Array<Migration> {
        return arrayOf(M1_2TableCard(1,2))
    }

    private fun M1_2TableCard(oldint: Int, newint: Int):Migration {
        return object :Migration(oldint,newint){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Card ADD COLUMN datatime Text")
            }
        }
    }
}