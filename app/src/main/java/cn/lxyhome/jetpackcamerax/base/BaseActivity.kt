package cn.lxyhome.jetpackcamerax.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import cn.lxyhome.jetpackcamerax.view.BaseLayout

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/19 created.
 *""
 *
 */
 abstract class BaseActivity:AppCompatActivity() {

    protected lateinit var myLocationListener:MyLocationListener
    protected val baseLayout:BaseLayout by lazy {
        BaseLayout(this, back_upOnClick,more_OnClick)
    }
    var mContext:Context? = null

    open var back_upOnClick = {
      val key =   onKeyDown(
            KeyEvent.KEYCODE_BACK,
            KeyEvent(
                KeyEvent.KEYCODE_BACK,
                KeyEvent.ACTION_DOWN
            )
        )
        //Unit
    }
    open var more_OnClick:()->Unit={
        Log.e("base","base more")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)//安全策略
        setContentView(baseLayout)
    }
    fun getLifecycleinfo(): Lifecycle {
        return lifecycle
    }

    protected fun setview(viewid: Int) {
        if (viewid!=0) {
            baseLayout.setLocalView(viewid)
        }
    }

    protected fun setViewVisible(left: Boolean, right: Boolean) {
        baseLayout.setViewVisible(left, right)
    }


    protected fun setTitle(message: String?) {
        baseLayout.setTitle(message)
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