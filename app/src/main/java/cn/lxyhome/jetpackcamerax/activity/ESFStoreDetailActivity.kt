package cn.lxyhome.jetpackcamerax.activity

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.fagment.esftest.FristFragment
import cn.lxyhome.jetpackcamerax.fagment.esftest.SecondFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_esf_store_detail.*
import kotlin.math.abs


class ESFStoreDetailActivity : AppCompatActivity() {

    var mHeigth:Int = 0
    val fristFragment  = FristFragment()
    val secondFragment = SecondFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_esf_store_detail)
        initStateBar()
        initFragment()
    }

    private fun initFragment() {

        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.fl_container,fristFragment).show(fristFragment)
        beginTransaction.add(R.id.fl_container,secondFragment).hide(secondFragment)
        beginTransaction.commitAllowingStateLoss()
        findViewById<View>(R.id.tv_frist).setOnClickListener{
            swicthFragemnt(1)
        }
        findViewById<View>(R.id.tv_second).setOnClickListener {
            swicthFragemnt(2)
        }
    }

    private fun swicthFragemnt(i: Int) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        when(i){
            1->{
                beginTransaction.show(fristFragment)
                beginTransaction.hide(secondFragment)
                beginTransaction.commitAllowingStateLoss()
            }
            else->{
                beginTransaction.hide(fristFragment)
                beginTransaction.show(secondFragment)
                beginTransaction.commitAllowingStateLoss()
            }
        }
    }

    private fun initStateBar() {
        val mRelativeLayout = findViewById<RelativeLayout>(R.id.rl_bar_title)
        val view  = findViewById<View>(R.id.v_status_bar)
        view.layoutParams.height = getStatusBarHeight(this)
        mRelativeLayout.run {
            post {
                this@ESFStoreDetailActivity.mHeigth = this.height
                this@ESFStoreDetailActivity.findViewById<CollapsingToolbarLayout>(R.id.ctlayout).run {
                    this.minimumHeight = this@ESFStoreDetailActivity.mHeigth
                }
            }
        }

        mApplayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.i("lxy","${verticalOffset.toString()}   total:${appBarLayout.totalScrollRange}")
            when {
                verticalOffset == 0 -> { //打开
                    val background = mRelativeLayout.background as ColorDrawable
                    background.alpha = 0
                    setAndroidNativeLightStatusBar(this,false)
                }
                abs(verticalOffset) >= appBarLayout.totalScrollRange -> { //折叠
                    val background = mRelativeLayout.background as ColorDrawable
                    background.alpha =255
                    setAndroidNativeLightStatusBar(this,true)
                }
                else -> {
                    val num: Float = (abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange)
                    setAndroidNativeLightStatusBar(this,true)
                    val background = mRelativeLayout.background as ColorDrawable
                    background.alpha = (255* num).toInt()
                    //                    中间
                }
            }
        })
    }
    private fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
        val decor = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    fun getStatusBarHeight(context: Context): Int {
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = field[obj].toString().toInt()
            val statusbarHeight =
                context.resources.getDimensionPixelSize(x)
            return statusbarHeight
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }


    private fun AppBarLayout.isAppBarScorll(boolean: Boolean) {
        val view = this.getChildAt(0)
        val layoutParams = view.layoutParams as AppBarLayout.LayoutParams
        if (boolean) {
            layoutParams.scrollFlags = (AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED)
        }
    }

}