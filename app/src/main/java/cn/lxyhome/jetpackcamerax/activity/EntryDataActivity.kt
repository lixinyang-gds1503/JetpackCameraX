package cn.lxyhome.jetpackcamerax.activity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.dao.entity.CardInfo
import cn.lxyhome.jetpackcamerax.util.isNotNullorEmpty
import cn.lxyhome.jetpackcamerax.util.toast
import cn.lxyhome.jetpackcamerax.viewmodel.EntryDataActivityModel
import kotlinx.android.synthetic.main.activity_entry.*
import kotlinx.android.synthetic.main.activity_main.btn_insert

class EntryDataActivity : BaseActivity() {

    private lateinit var priliveModel:EntryDataActivityModel

    private val mObserver = Observer<CardInfo>{ newCardinfo->
        if (newCardinfo._id!=0) {
            val queryWhereForCard = JetpackApplication.getCardDao()?.queryWhereForCard(newCardinfo._id)
            queryWhereForCard?.let { queryCardinfo ->
                val oldCardInfo = queryCardinfo.value
                oldCardInfo?.run {
                    if (newCardinfo.title.isNotNullorEmpty()){
                        this.title = newCardinfo.title
                    }
                    if (newCardinfo.detail.isNotNullorEmpty()) {
                        this.detail = newCardinfo.detail
                    }
                    if (newCardinfo.headimg.isNotNullorEmpty()) {
                        this.headimg = newCardinfo.headimg
                    }
                    this.datatime = System.currentTimeMillis().toString()
                    val updataInt = JetpackApplication.getCardDao()?.updateCard(this)
                    if (updataInt == this._id) {
                        this@EntryDataActivity.toast("updata succes")
                    }else{
                        this@EntryDataActivity.toast("updata fail")
                    }
                }
            }
        }else{
            val l = JetpackApplication.getCardDao()?.insertCard(newCardinfo)
            if (l!=null && l>0L) {
                Toast.makeText(this@EntryDataActivity,"insert succes",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@EntryDataActivity,"insert fail",Toast.LENGTH_LONG).show()
            }
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
            val times = System.currentTimeMillis().toString()
            val CardInfo = CardInfo(title,detail,http,times)
            val _id = et_id.text.toString()
            if (!_id.isNotNullorEmpty()&&  title.isNotNullorEmpty() && detail.isNotNullorEmpty()) {
                priliveModel.currentData.value = CardInfo
            }else{
                if (!_id.isNotNullorEmpty()) {
                    this.toast("数据错误")
                }else {
                    priliveModel.currentData.value = CardInfo(_id.toInt(),title,detail,http,times)
                }
            }
        }
    }
}
