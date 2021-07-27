package cn.lxyhome.jetpackcamerax.activity

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.clazz.CustomMap
import cn.lxyhome.jetpackcamerax.util.toast
import kotlinx.android.synthetic.main.activity_motion_layout.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


class MotionLayoutActivity : BaseActivity() {

    var music = 1 shl 0
    var flag:Int = 0;
    fun disable(permission: Int) {
        flag = flag and permission.inv()
    }

    fun enable(permission: Int) {
        flag = flag or permission
    }

    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_motion_layout)
        imageView.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.rotation1,
                theme
            )
        )
        job = lifecycleScope.launch(Dispatchers.IO) {
            try {
                withTimeout(5000L){
                    repeat(1000) {
                        if (isActive) {
                            runOnUiThread {
                                tv_test.text = it.toString()
                            }
                        }
                        delay(1000L)
                    }
                }
            } finally {
                //不能取消的代码块
                withContext(NonCancellable){
                    runOnUiThread {
                        toast("NonCancellable")
                    }
                }
            }
        }
        root.post {
            root.setTransition(R.id.start, R.id.end)
        }
        root.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                p0?.isInteractionEnabled = false
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                p0?.progress = 0F
                p0?.transitionToStart()
                p0?.isInteractionEnabled = true
                root.setTransition(R.id.start, R.id.end)
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }

        })
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
           // try {
                job?.cancelAndJoin()
//            } catch (e: Exception) {
//                toast("exception ${e.message} ${e.javaClass.simpleName}")
//            }
//            toast("job cancel !")
        }

    }
}