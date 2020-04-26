package cn.lxyhome.jetpackcamerax.dao.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/20 created.
 *""
 *
 */
annotation class database

@Entity(tableName = "Card")
@database
data class CardInfo(
    @PrimaryKey(autoGenerate = true)
    var _id:Int= 0,
    var title: String?,
    var detail: String?,
    var headimg: String?,
    var datatime:String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    constructor(title: String, detail: String, http: String,datatime: String) : this(0,title,detail,http,datatime)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(_id)
        parcel.writeString(title)
        parcel.writeString(detail)
        parcel.writeString(headimg)
        parcel.writeString(datatime)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "CardInfo(_id=$_id, title=$title, detail=$detail, headimg=$headimg, datatime=$datatime)"
    }


    companion object CREATOR : Parcelable.Creator<CardInfo> {
        override fun createFromParcel(parcel: Parcel): CardInfo {
            return CardInfo(parcel)
        }

        override fun newArray(size: Int): Array<CardInfo?> {
            return arrayOfNulls(size)
        }
    }

}