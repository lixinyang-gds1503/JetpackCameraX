package cn.lxyhome.jetpackcamerax.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.DataSource
import androidx.paging.PagedListAdapter
import androidx.paging.PositionalDataSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/7/21 created.
 *
 */
class PagingViewAdapter(var mContext:Context) : PagedListAdapter<UserInfo, PagingVH>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingVH {
        val item:View = LayoutInflater.from(mContext).inflate(R.layout.pgitem,parent,false)
        return PagingVH(item)
    }

    override fun onBindViewHolder(holder: PagingVH, position: Int) {
        val item = getItem(position)
        holder.onbind(position,item)
    }

    companion object {
        val callback: PagingViewCallback = PagingViewCallback()
    }



}

class PagingVH(item:View) : RecyclerView.ViewHolder(item) {


    fun onbind(position: Int, item: UserInfo?) {
        item?.apply {
            val text: CharSequence = this.loginName ?: this.nickName ?: "no data"
            itemView.findViewById<TextView>(R.id.tv_content).text= text
        }
    }

}

class PagingViewCallback : DiffUtil.ItemCallback<UserInfo>() {
    override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem == newItem
    }

}






