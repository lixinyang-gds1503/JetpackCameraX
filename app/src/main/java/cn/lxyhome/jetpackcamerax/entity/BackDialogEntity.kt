package cn.lxyhome.jetpackcamerax.entity

import java.io.Serializable

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/4/1 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class BackDialogEntity(
    val text:String,
    val btnEnter:String,
    val btnClose:String,
    val Imagurl:String?
):Serializable {
    companion object{
        private const val serialVersionUID = 1L
    }
}