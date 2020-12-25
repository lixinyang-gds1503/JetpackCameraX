package cn.lxyhome.jetpackcamerax.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.Button
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/19 created.
 *""
 *
 */
class MyButton : androidx.appcompat.widget.AppCompatButton,DefaultLifecycleObserver {

    constructor(context: Context):super(context)
    constructor(context: Context,arts: AttributeSet?):super(context,arts)
    constructor(context: Context,arts: AttributeSet?,defStyleAttr:Int):super(context,arts,defStyleAttr)

    override fun onCreate(owner: LifecycleOwner) {
        Log.i(TAG,"MyButton-onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.i(TAG,"MyButton-onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.i(TAG,"MyButton-onResume")
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.i(TAG,"MyButton-onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.i(TAG,"MyButton-onDestroy")
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.i(TAG,"MyButton-onPause")
    }

    companion object{
        const val TAG = "MyButton"
    }
}