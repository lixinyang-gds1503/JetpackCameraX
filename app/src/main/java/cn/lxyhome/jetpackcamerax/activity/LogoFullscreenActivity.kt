package cn.lxyhome.jetpackcamerax.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.util.startActivity
import kotlinx.android.synthetic.main.activity_logo_fullscreen.*
import java.util.*

/**
 * 开屏页面
 */
class LogoFullscreenActivity : AppCompatActivity() {
    private val mHideHandler = Handler(Handler.Callback {
        if (it.what==1) {
            tv_timer.text = (4-cont).toString()
            if (cont==4) {
                mTimer.cancel()
                startActivity<MainActivity> {
                    Intent()
                }
                finish()
            }
        }
        false
    })
    private val mHidePart2Runnable = Runnable {
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private val mTimer = Timer()
    private var cont = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo_fullscreen)
        mHideHandler.post(mHidePart2Runnable)
        tv_timer.text = "4"
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        mTimer.schedule(object : TimerTask() {
            override fun run() {
                cont++
                mHideHandler.sendEmptyMessage(1)
            }
        },100L,1000L)

    }


    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }
}
