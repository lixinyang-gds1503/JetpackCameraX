package cn.lxyhome.jetpackcamerax.entity

import java.io.Serializable

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/31 created.
 *""
 *
 */
class MainEntity(
    var imgurl:String?,
    var btnText:String?
):Serializable {
    companion object{
     private const val serialVersionUID = 1L
    }
}