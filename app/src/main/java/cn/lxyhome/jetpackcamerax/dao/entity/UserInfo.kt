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
 *""
 *
 */

@Entity(tableName = "User",indices = [Index(name = "index_name",value = ["username"],unique = true)])
@database
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    var _id:Int,
    @ColumnInfo(name = "username")
    @NotNull
    val loginName: String?,
    var realName:String?="",
    var nickName:String?="",
    var createDate:Long =System.currentTimeMillis(),
    var upDataDate:Long  =System.currentTimeMillis(),
    var portrait:String?="",
    var NumPhone:String?="",
    var Com:String?="",
    var noteText:String?=""
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInfo

        if (_id != other._id) return false
        if (loginName != other.loginName) return false
        if (realName != other.realName) return false
        if (nickName != other.nickName) return false
        if (createDate != other.createDate) return false
        if (upDataDate != other.upDataDate) return false
        if (portrait != other.portrait) return false
        if (NumPhone != other.NumPhone) return false
        if (Com != other.Com) return false
        if (noteText != other.noteText) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id
        result = 31 * result + (loginName?.hashCode() ?: 0)
        result = 31 * result + (realName?.hashCode() ?: 0)
        result = 31 * result + (nickName?.hashCode() ?: 0)
        result = 31 * result + createDate.hashCode()
        result = 31 * result + upDataDate.hashCode()
        result = 31 * result + (portrait?.hashCode() ?: 0)
        result = 31 * result + (NumPhone?.hashCode() ?: 0)
        result = 31 * result + (Com?.hashCode() ?: 0)
        result = 31 * result + (noteText?.hashCode() ?: 0)
        return result
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