package cn.lxyhome.jetpackcamerax.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.adapter.PagingViewAdapter
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo
import cn.lxyhome.jetpackcamerax.viewmodel.PagingDataSourceFactory
import cn.lxyhome.jetpackcamerax.viewmodel.PagingViewModel
import kotlinx.android.synthetic.main.activity_paging_tset.*

class PagingTsetActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_paging_tset)
        setTitle("Paging")
        val mPGModle = ViewModelProvider(this).get(PagingViewModel::class.java)
        val pagingViewAdapter = PagingViewAdapter(this)
        mPGModle.liveData.observe(this, Observer {
            pagingViewAdapter.submitList(it)
        })
        rc.adapter = pagingViewAdapter
        rc.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
    }
}