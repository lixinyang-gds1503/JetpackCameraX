package cn.lxyhome.jetpackcamerax.fagment.esftest

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.lxyhome.jetpackcamerax.R


class SecondFragment : Fragment() {
    companion object {
        fun newInstance() = FristFragment()
    }

    private var findViewById: RecyclerView?=null
    private lateinit var viewModel: FristViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_second, container, false)
        findViewById = inflate.findViewById<RecyclerView>(R.id.list_view)
        return inflate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FristViewModel::class.java)
        viewModel.listData.observe(requireActivity(), Observer{
            initRecycleView(it)
        })

    }
    private fun initRecycleView(it: Array<String>) {

        findViewById?.layoutManager = LinearLayoutManager(activity,
            RecyclerView.VERTICAL,false)
        findViewById?.adapter = object : RecyclerView.Adapter<VH>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): VH {
                return VH(LayoutInflater.from(activity).inflate(android.R.layout.activity_list_item,parent,false))
            }

            override fun getItemCount(): Int {
                return it.size
            }

            override fun onBindViewHolder(holder: VH, position: Int) {
                holder.onBind(position,it)
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findViewById?.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    //todo
            }
        }
    }

    inner class VH(val view:View): RecyclerView.ViewHolder(view) {
        fun onBind(position: Int, it: Array<String>) {
            view.findViewById<TextView>(android.R.id.text1).text = it[position]
        }

    }
}