package cn.lxyhome.jetpackcamerax.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.dao.entity.CardInfo
import cn.lxyhome.jetpackcamerax.util.toast
import com.bumptech.glide.Glide

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/24 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class HomeFragmentRecycleViewAdapter(private val context:Context, private val list:List<CardInfo>):RecyclerView.Adapter<HomeFragementRecycleViewHV>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragementRecycleViewHV {
        val item = LayoutInflater.from(context)
            .inflate(R.layout.fragment_home_recycleview_adapter_item, null)

       return HomeFragementRecycleViewHV(context,item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeFragementRecycleViewHV, position: Int) {
            val info = list[position]
            holder.onBind(info)
    }
}


class HomeFragementRecycleViewHV(private val context: Context, item: View) : RecyclerView.ViewHolder(item) {
    private lateinit var currentItem:CardInfo
    init {
        item.setOnClickListener {
            toast(currentItem.toString())
        }
    }

    fun onBind(info:CardInfo) {
        currentItem = info
        Glide.with(context).load(info.headimg)
            .error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher)
            .into(itemView.findViewById<ImageView>(R.id.img_head))
        itemView.findViewById<TextView>(R.id.tv_title).text = info.title
        itemView.findViewById<TextView>(R.id.tv_detail).text = info.detail

    }
}