package cn.lxyhome.jetpackcamerax.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.dao.entity.CardInfo
import cn.lxyhome.jetpackcamerax.util.isNotNullorEmpty
import cn.lxyhome.jetpackcamerax.util.toast
import cn.lxyhome.jetpackcamerax.viewmodel.EntryDataActivityModel
import kotlinx.android.synthetic.main.activity_entry.*
import kotlinx.android.synthetic.main.activity_main.btn_insert
import kotlinx.coroutines.flow.Flow

class EntryDataActivity : BaseActivity() {

    private lateinit var priliveModel:EntryDataActivityModel
    private lateinit var mUpdataCardInfo: CardInfo
    private lateinit var mQuereWhereForCard:LiveData<CardInfo>
    private lateinit var  mObserve:Observer<CardInfo>
    private val mhandler = Handler(Looper.getMainLooper(),Handler.Callback {
        if (it.what ==1) {
            mObserve?.let {o->
                mQuereWhereForCard.removeObserver(o)
                val updataInt = JetpackApplication.getCardDao()?.updateCard(mUpdataCardInfo)
                if (updataInt == mUpdataCardInfo._id) {
                    this@EntryDataActivity.toast("updata succes")
                } else {
                    this@EntryDataActivity.toast("updata fail")
                }
            }
        }
                     false
    })
    private val mObserver = Observer<CardInfo>{ newCardinfo->
        if (newCardinfo._id!=0) {
            val queryWhereForCard = JetpackApplication.getCardDao()?.queryWhereForCard(newCardinfo._id)
            if (queryWhereForCard != null) {
                mQuereWhereForCard = queryWhereForCard
                mObserve = Observer<CardInfo> {
                    it?.run {
                        val a = if (newCardinfo.title.isNotNullorEmpty()) {
                            this.title = newCardinfo.title
                            true
                        } else {
                            false
                        }
                        val b = if (newCardinfo.detail.isNotNullorEmpty()) {
                            this.detail = newCardinfo.detail
                            true
                        } else {
                            false
                        }
                        val c = if (newCardinfo.headimg.isNotNullorEmpty()) {
                            this.headimg = newCardinfo.headimg
                            true
                        } else false
                        if (a && b && c) {
                            this.datatime = System.currentTimeMillis().toString()
                            mUpdataCardInfo = this
                            Log.i("CardDao","1")
                            mhandler.sendEmptyMessage(1)
                        }else{
                            this@EntryDataActivity.toast("updata fail")
                        }

                    }
                }
                mQuereWhereForCard.observe(this@EntryDataActivity, mObserve)
            }

        }else{
            val l = JetpackApplication.getCardDao()?.insertCard(newCardinfo)
            //Flow使用
            val queryWhereForCard:Flow<List<CardInfo>>? = JetpackApplication.getCardDao()?.queryWhereForCard("")
            queryWhereForCard?.asLiveData()
            if (l!=null && l>0L) {
                Toast.makeText(this@EntryDataActivity,"insert succes",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@EntryDataActivity,"insert fail",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_entry)
        setTitle("录入/修改")
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
