package cn.lxyhome.jetpackcamerax.activity

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.lxyhome.jetpackcamerax.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_esf_store_detail.*
import kotlin.math.abs


class ESFStoreDetailActivity : AppCompatActivity() {
    val arraylist  = arrayOf("123","fafdsf","sdfdsf","sfddsf","fdgfdg",
        "strhh","sgjsjg","gjdhj","stjsf","hsgfhgfs","ewtrg","23423","fsghgfs","fdgfdg","gfdgdg",
        "sdfhtrsh","fdgdnbrtj","afdg3wg","agabadfh","sdeahre","dfbfdah","3rqf","hjtrjha","ar3adfb",
        "sdfsahaeh","bzberh","dsgeaa","agdfbdaf")
    var mHeigth:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_esf_store_detail)
        initRecycleView()
        initStateBar()
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

    private fun initRecycleView() {
        val findViewById = findViewById<RecyclerView>(R.id.fl_container)
        findViewById.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)
        findViewById.adapter = object : RecyclerView.Adapter<VH>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): VH {
                return VH(LayoutInflater.from(this@ESFStoreDetailActivity).inflate(android.R.layout.activity_list_item,parent,false))
            }

            override fun getItemCount(): Int {
                return arraylist.size
            }

            override fun onBindViewHolder(holder: VH, position: Int) {
                holder.onBind(position)
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findViewById.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            }
        }
    }

    inner class VH(val view:View):RecyclerView.ViewHolder(view) {
        fun onBind(position: Int) {
            view.findViewById<TextView>(android.R.id.text1).text = arraylist[position]
        }

    }
}