package cn.lxyhome.jetpackcamerax.util

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.base.BaseActivity

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/24 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */


fun BaseActivity.toast(message: String) {
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

fun toast(message: String) {
    Toast.makeText(JetpackApplication.self,message,Toast.LENGTH_LONG).show()
}

fun String?.isNotNullorEmpty():Boolean {
    if (this==null) {
        return false
    }
    this.let {
        return !(this==""|| this.trim()=="" || this.isEmpty())
    }
}



inline fun <reified T : Activity> Activity.startActivity(block:()->Intent) {
    val intent = block()
    intent.setClass(this,T::class.java)
    this.startActivity(intent)
}