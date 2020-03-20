package cn.lxyhome.jetpackcamerax.dao.entity

import android.os.Parcel
import android.os.Parcelable

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/20 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
annotation class database

@database
data class CardInfo(
    var id:Int,
    var title: String?,
    var detail: String?,
    var headimg: String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(detail)
        parcel.writeString(headimg)
    }

    override fun describeContents(): Int {
        return 0
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