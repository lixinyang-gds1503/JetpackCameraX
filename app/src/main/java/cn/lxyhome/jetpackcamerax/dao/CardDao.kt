package cn.lxyhome.jetpackcamerax.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cn.lxyhome.jetpackcamerax.dao.entity.CardInfo

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/23 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(cardinfo: CardInfo): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCard(vararg cardinfos: CardInfo): Int

    @Delete
    fun deleteCard(vararg cardinfos: CardInfo)

    @Query("SELECT * FROM Card")
    fun queryCardAllLiveData(): LiveData<List<CardInfo>>

    @Query("SELECT * FROM Card")
    fun queryCardAllList(): List<CardInfo>

    @Query("SELECT * FROM Card WHERE _id= :id")
    fun queryWhereForCard(id: Int): LiveData<CardInfo>

    @Query("SELECT * FROM Card WHERE title= :title")
    fun queryWhereForCard(title: String): List<CardInfo>

}