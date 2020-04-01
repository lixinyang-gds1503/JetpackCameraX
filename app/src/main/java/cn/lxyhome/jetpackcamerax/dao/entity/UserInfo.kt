package cn.lxyhome.jetpackcamerax.dao.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/31 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */

@Entity(tableName = "User",indices = [Index(name = "index_name",value = ["username"],unique = true)])
@database
class UserInfo(
    @PrimaryKey(autoGenerate = true)
    var _id:Int,
    @ColumnInfo(name = "username")
    @NotNull
    val loginName: String?,
    var realName:String?,
    var nickName:String?,
    var createDate:Long,
    var upDataDate:Long,
    var portrait:String?,
    var NumPhone:String?,
    var Com:String?,
    var noteText:String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(_id)
        parcel.writeString(loginName)
        parcel.writeString(realName)
        parcel.writeString(nickName)
        parcel.writeLong(createDate)
        parcel.writeLong(upDataDate)
        parcel.writeString(portrait)
        parcel.writeString(NumPhone)
        parcel.writeString(Com)
        parcel.writeString(noteText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }
}