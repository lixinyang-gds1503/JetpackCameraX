package cn.lxyhome.jetpackcamerax.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.lxyhome.jetpackcamerax.R

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/26 created.
 *""
 *
 */
@SuppressLint("ViewConstructor")
class BaseLayout(context: Context?, vararg onclicks: (() -> Unit)?) : LinearLayout(context),View.OnClickListener {

    lateinit var img_back: ImageView
    lateinit var img_more: ImageView
    lateinit var tv_title: TextView
    var ll_header_left: LinearLayout

    private lateinit var onclicks:Array<out (()->Unit)?>
    init {
        this.orientation = VERTICAL
        this.onclicks = onclicks
        val child:LinearLayout =
            LayoutInflater.from(context).inflate(R.layout.baselayout_head_view,null) as LinearLayout
        img_back = child.findViewById(R.id.btn_back)
        img_more = child.findViewById(R.id.iv_add)
        tv_title = child.findViewById(R.id.tv_header_title)
        ll_header_left = child.findViewById(R.id.ll_header_left)

        img_back.setOnClickListener(this)
        img_more.setOnClickListener(this)
        ll_header_left.setOnClickListener(this)
        this.addView(child,0)
    }

    fun setViewVisible(left: Boolean, right: Boolean) {
        if (left) {
            img_back.visibility  =View.VISIBLE
        }else {
            img_back.visibility = View.GONE
        }

        if (right) {
            img_more.visibility  =View.VISIBLE
        }else {
            img_more.visibility = View.GONE
        }
    }

    fun setOnClicks(vararg onclicks: (() -> Unit)?){
        this.onclicks = onclicks
    }


    fun setTitle(title: String?) {
        title?.run {
            tv_title.text = this
        }
    }
    fun setLocalView(localviewid: Int) {
        if (localviewid==0) {
            return
        }
        val localview = LayoutInflater.from(context).inflate(localviewid, null)
        localview.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        this.addView(localview)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_add->{
                if (onclicks.isNotEmpty()) {
                    onclicks[1]?.let { it() }
                }
            }
            R.id.btn_back,R.id.ll_header_left->{
                if(onclicks.isNotEmpty()){
                    onclicks[0]?.let { it() }
                }
            }
            else ->{}
        }
    }

}