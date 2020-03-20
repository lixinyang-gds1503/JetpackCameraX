package cn.lxyhome.jetpackcamerax.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/19 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
 abstract class BaseActivity:AppCompatActivity() {

    protected lateinit var myLocationListener:MyLocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    fun getLifecycleinfo(): Lifecycle {
        return lifecycle
    }

    /**
     * 生命周期回调接口
     */
      abstract class MyLocationListener(val context: Context, val mLifecycle:Lifecycle) {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        abstract  fun onCreate()
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        abstract  fun onStart()
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        abstract  fun onResume()
        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        abstract  fun onStop()
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        abstract  fun onDestroy()
    }
}