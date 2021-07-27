package cn.lxyhome.jetpackcamerax.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.coroutines.*
import cn.lxyhome.jetpackcamerax.net.getJsonFromNet
import cn.lxyhome.jetpackcamerax.util.loge
import cn.lxyhome.jetpackcamerax.util.toast
import kotlinx.android.synthetic.main.activity_constraint_layout.*
import kotlinx.coroutines.*

class ConstraintLayoutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_constraint_layout)
        setTitle("ConstraintLayout使用")

        getHttpTask(lifecycleScope, object : CoroutineSpoceErrorListener {
            override fun onError(e: Throwable) {
                loge(e.printStackTrace().toString())
            }
        }, timeoutCall(1)) {
            repeat(100000) {
                loge("ConstraintLayoutActivity")
                delay(1000L)
            }
        }

        tv12.setOnClickListener {
            /*it.getViewAutoDisposeScopeCancellable {
                //todo
                val launch = launch(Dispatchers.IO) {
                    try {
                        // throw RuntimeException("抛异常被捕获")
                        delay(10000L)
                        this.cancel()
                        ""
                    } catch (e: Exception) {

                    }
                }
            }*/

            getHttpTask(
                lifecycleScope,
                object : CoroutineSpoceErrorListener {
                    override fun onError(e: Throwable) {
                        loge(e.printStackTrace().toString())
                    }
                }, timeoutCall(2)
            ) {
                getJsonFromNet(mapOf()) {
                    runOnUiThread {
                        // TODO: 2021/3/12 UI
                        toast(it.toString())
                    }
                }
            }
        }
    }

/*    var timeoutCall = object :(Int) -> Unit {
        override fun invoke(p1: Int) {

        }
    }*/

    val timeoutCall = { i: Int ->
        when (i) {
            1 -> {
            }
            2 -> {
            }
            else -> {
            }

        }
    }
}