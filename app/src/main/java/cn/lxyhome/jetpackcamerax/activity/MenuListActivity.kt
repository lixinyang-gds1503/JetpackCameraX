package cn.lxyhome.jetpackcamerax.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.activity.ui.login.LoginActivity
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.util.startActivity
import cn.lxyhome.jetpackcamerax.viewmodel.MenuListModel
import java.util.ArrayList

/**
 *  功能页面列表
 */
class MenuListActivity : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_menu_list)
        setTitle("功能页面列表")
        initView()
    }

    private fun initView() {
       val list =  findViewById<RecyclerView>(R.id.menulist)
        list.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        list.adapter = mAdapter()
        list.addItemDecoration(DividerItemDecoration(this,LinearLayout.VERTICAL))
        val mMenuListModel = ViewModelProvider(this).get(MenuListModel::class.java)
        mMenuListModel.mLiveData.observe(this){ data->
            list.adapter?.also {
                val mAdapter = it as mAdapter
                mAdapter.up(data)
            }
            list.adapter?.notifyDataSetChanged()
        }
    }
    inner class mAdapter : RecyclerView.Adapter<mVH>() {
        private val listdata = arrayListOf<String>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mVH {
            return mVH(LayoutInflater.from(this@MenuListActivity).inflate(android.R.layout.simple_list_item_1,parent,false),this@MenuListActivity)
        }

        override fun getItemCount(): Int {
            return listdata.size
        }

        override fun onBindViewHolder(holder: mVH, position: Int) {
            holder.onBind(listdata[position])

        }

        fun up(data: ArrayList<String>) {
            listdata.clear()
            listdata.addAll(data)
            notifyDataSetChanged()
        }


    }
    inner class mVH(val view: View,onClickListener:View.OnClickListener) : RecyclerView.ViewHolder(view) {
        private lateinit var mText: TextView

        init {
            mText= view.findViewById(android.R.id.text1)
            view.setOnClickListener(onClickListener)
        }
       fun onBind(text:String){
           val name= text.split("cn.lxyhome.jetpackcamerax.activity.".toRegex())[1]?:""
           mText?.text = name
           mText?.tag = text
       }
    }

    override fun onClick(v: View?) {
        v?.let {
         val className =    it.findViewById<TextView>(android.R.id.text1).tag.toString()
            startActivity(Intent(this, Class.forName(className)))
        }
    }
}