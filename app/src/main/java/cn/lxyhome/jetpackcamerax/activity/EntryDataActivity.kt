package cn.lxyhome.jetpackcamerax.activity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.dao.entity.CardInfo
import cn.lxyhome.jetpackcamerax.viewmodel.EntryDataActivityModel
import kotlinx.android.synthetic.main.activity_entry.*
import kotlinx.android.synthetic.main.activity_main.btn_insert

class EntryDataActivity : BaseActivity() {

    private lateinit var priliveModel:EntryDataActivityModel

    private val mObserver = Observer<CardInfo>{
            val l = JetpackApplication.getCardDao()?.insertCard(it)
        if (l!=null && l>0L) {
            Toast.makeText(this@EntryDataActivity,"insert succes",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this@EntryDataActivity,"insert fail",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        priliveModel = ViewModelProvider(this@EntryDataActivity).get(EntryDataActivityModel::class.java)
        resisterObserver()
        registerLisetner()
    }

    private fun resisterObserver() {
        priliveModel.currentData.observe(this@EntryDataActivity,mObserver)
    }

    private fun registerLisetner() {
        btn_insert.setOnClickListener {
            val title = et_title.text.toString().trim()
            val detail = et_detail.text.toString().trim()
            val http = et_headimg.text.toString()
            val CardInfo = CardInfo(title,detail,http)
            priliveModel.currentData.value = CardInfo
        }
    }
}
