package cn.lxyhome.jetpackcamerax.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/31 created.
 *""
 *
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg userinfos: UserInfo): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(vararg userinfos: UserInfo): Int

    @Delete
    fun deleteUser(vararg userinfos: UserInfo):Int

    @Query("SELECT * FROM User")
    fun queryUserAllLiveData(): LiveData<List<UserInfo>>

    @Query("SELECT * FROM User")
    fun queryUserAllList(): List<UserInfo>

    @Query("SELECT * FROM User WHERE _id= :id")
    fun queryWhereForUser(id: Int): LiveData<UserInfo>

    @Query("SELECT * FROM User WHERE username = :loginName")
    fun queryWhereForUser(loginName: String): List<UserInfo>

}