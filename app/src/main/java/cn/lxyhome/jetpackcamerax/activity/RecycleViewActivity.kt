package cn.lxyhome.jetpackcamerax.activity

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import kotlinx.android.synthetic.main.activity_recycleview.*

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/12/3 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class RecycleViewActivity :BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_recycleview)
        lv_list.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        val mAdapter = mAdapter(1)
        lv_list.adapter = mAdapter
        refesh.setOnClickListener {
            mAdapter.clear()
            Handler().postDelayed({ mAdapter.updata(3) },1500L)
        }
    }

    inner class mAdapter(var k:Int) : RecyclerView.Adapter<VH>() {
        private val list = arrayListOf("1")
        init {
            for (i in 1..100) {
                list.add((k*i).toString())
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val view:TextView = LayoutInflater.from(this@RecycleViewActivity).inflate(android.R.layout.simple_list_item_1,null) as TextView
            return VH (view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.item.text = list.get(position)

        }

        fun updata(i: Int) {
            k = i
            for (i in 1..100) {
                list.add((k*i).toString())
            }
            notifyDataSetChanged()
        }

        fun clear() {
            list.clear()
        }
    }

    class VH(var item: TextView) : RecyclerView.ViewHolder(item) {
    }
}