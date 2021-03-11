package cn.lxyhome.jetpackcamerax.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.coroutines.*
import cn.lxyhome.jetpackcamerax.util.loge
import kotlinx.android.synthetic.main.activity_constraint_layout.*
import kotlinx.coroutines.*

class ConstraintLayoutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_constraint_layout)
        setTitle("ConstraintLayout使用")
        val timeoutCall = {
            loge("timeout")
        }
        getHttpTask(lifecycleScope, object : CoroutineSpoceErrorListener {
            override fun onError(e: Throwable) {
                loge(e.printStackTrace().toString())
            }
        }, timeoutCall){
            repeat(100000){
                loge("ConstraintLayoutActivity")
                delay(1000L)
            }
        }

        tv12.setOnClickListener {
            it.getViewAutoDisposeScopeCancellable {
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
                launch.cancelAndJoin()
            }
        }
    }
}