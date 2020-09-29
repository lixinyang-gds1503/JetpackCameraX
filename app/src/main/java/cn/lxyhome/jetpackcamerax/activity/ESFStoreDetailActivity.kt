package cn.lxyhome.jetpackcamerax.activity

import android.graphics.Color
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
import kotlinx.android.synthetic.main.activity_esf_store_detail.*


class ESFStoreDetailActivity : AppCompatActivity() {
    val arraylist  = arrayOf("123","fafdsf","sdfdsf","sfddsf","fdgfdg",
        "strhh","sgjsjg","gjdhj","stjsf","hsgfhgfs","ewtrg","23423","fsghgfs","fdgfdg","gfdgdg",
        "sdfhtrsh","fdgdnbrtj","afdg3wg","agabadfh","sdeahre","dfbfdah","3rqf","hjtrjha","ar3adfb",
        "sdfsahaeh","bzberh","dsgeaa","agdfbdaf")
    var height:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_esf_store_detail)
        initRecycleView()
        initStateBar()
    }

    private fun initStateBar() {
        findViewById<RelativeLayout>(R.id.rl_bar_title).run {
            post {
                this@ESFStoreDetailActivity.height = this.height
            }
        }
        mApplayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.i("lxy",verticalOffset.toString())

          /*  if (height-verticalOffset >= appBarLayout.height) {
                if (img.layoutParams is AppBarLayout.LayoutParams) {
                    (img.layoutParams as AppBarLayout.LayoutParams).scrollFlags =  0
                }
            }else{
                (img.layoutParams as AppBarLayout.LayoutParams).scrollFlags =  AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
            }*/
        })
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