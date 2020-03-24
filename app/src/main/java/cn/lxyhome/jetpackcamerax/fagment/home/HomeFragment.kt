package cn.lxyhome.jetpackcamerax.fagment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.activity.TabMainActivity
import cn.lxyhome.jetpackcamerax.adapter.HomeFragmentRecycleViewAdapter
import cn.lxyhome.jetpackcamerax.dao.entity.CardInfo


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val listview = root.findViewById<RecyclerView>(R.id.list_view)
        val o = activity as TabMainActivity

        homeViewModel.Cardlist.observe(o, Observer {
            initListView(listview,it)
        })
        homeViewModel.text.observe(o, Observer {
            textView.text = it
        })
        homeViewModel.text.value = "Card表中数据是酱婶儿的:"
        return root
    }

    private fun initListView(
        list: RecyclerView,
        data: List<CardInfo>
    ) {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredGridLayoutManager
        list.adapter = activity?.let { HomeFragmentRecycleViewAdapter(it,data) }
    }
}