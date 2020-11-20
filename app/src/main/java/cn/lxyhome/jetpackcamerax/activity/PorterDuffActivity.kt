package cn.lxyhome.jetpackcamerax.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.dialog.PorterDuffDialog
import cn.lxyhome.jetpackcamerax.viewmodel.PorterDuffViewModle
import kotlinx.android.synthetic.main.activity_porter_duff.*

class PorterDuffActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_porter_duff)
        setTitle("PorterDuff")
        lv_porter.layoutManager = GridLayoutManager(this,2,RecyclerView.VERTICAL,false)
        ViewModelProvider(this).get(PorterDuffViewModle::class.java).strLiveData.observe(this,
            Observer {
                lv_porter.adapter = PorterAdapter(it)
            })

    }
   inner class PorterAdapter(var list: List<HashMap<String,PorterDuff.Mode>>) : RecyclerView.Adapter<PorterHV>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PorterHV {
            val view = LayoutInflater.from(this@PorterDuffActivity).inflate(R.layout.porter_item,parent,false)
            return PorterHV (view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: PorterHV, position: Int) {
            val text = list.get(position)
            holder.bind(text)
        }
    }

   inner class PorterHV(var item: View) : RecyclerView.ViewHolder(item) {
        private var btn_text:Button
        init {
            btn_text = item.findViewById(R.id.btn_text)
        }

        fun bind(text: HashMap<String, PorterDuff.Mode>) {
            text.forEach {
                btn_text.text = it.key
                btn_text.setOnClickListener {v->
                    ShowDialog(it.key,it.value)
                }
            }
        }
    }

    private fun ShowDialog(text: String, value: PorterDuff.Mode) {
        val porterDialog = PorterDuffDialog(text,value)
        porterDialog.show(supportFragmentManager,"porterdialog")
    }
}