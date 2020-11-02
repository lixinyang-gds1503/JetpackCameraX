package cn.lxyhome.jetpackcamerax.activity

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.activity.ui.login.LoginActivity
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo
import cn.lxyhome.jetpackcamerax.util.startActivity
import cn.lxyhome.jetpackcamerax.viewmodel.UserInfoActivityModel
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : BaseActivity() {

    private lateinit var mModel:UserInfoActivityModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_user_info)
        mModel = ViewModelProvider(this).get(UserInfoActivityModel::class.java)
        val observer = Observer<UserInfo> {
            setTitle(it.loginName)

        }
        mModel.userInfoData.observe(this, observer)
        val userinfo = intent.getParcelableExtra<UserInfo>("username")
        userinfo?.let {
            mModel.setValue(it)
          //  observer.onChanged(it)
        }

        more_OnClick = {
            startActivity<PagingTsetActivity> {
                Intent()
            }
        }
        back_upOnClick = {
            startActivity<MainActivity> {
                Intent()
            }
            finish()
           // super.back_upOnClick()
        }
        baseLayout.setOnClicks(back_upOnClick, more_OnClick)
        mL.setTransitionListener(object :MotionLayout.TransitionListener{
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {

            }

        })


    }

    private fun showPopWin() {

    }

}
