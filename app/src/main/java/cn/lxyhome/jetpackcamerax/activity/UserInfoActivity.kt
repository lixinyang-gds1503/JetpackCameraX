package cn.lxyhome.jetpackcamerax.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo
import cn.lxyhome.jetpackcamerax.viewmodel.UserInfoActivityModel

class UserInfoActivity : BaseActivity() {

    private lateinit var mModel:UserInfoActivityModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_user_info)
        mModel = ViewModelProvider(this).get(UserInfoActivityModel::class.java)
        mModel.userInfoData.observe(this, Observer {
            setTitle(it.loginName)
        })
        val userinfo = intent.getParcelableExtra<UserInfo>("username")
        userinfo?.let {
            mModel.setValue(it)
        }
        more_OnClick = {
            showPopWin()
        }
    }

    private fun showPopWin() {

    }

}
