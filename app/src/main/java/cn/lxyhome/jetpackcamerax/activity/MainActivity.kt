package cn.lxyhome.jetpackcamerax.activity

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.entity.MainEntity
import cn.lxyhome.jetpackcamerax.util.setImageUrl
import cn.lxyhome.jetpackcamerax.util.startActivity
import cn.lxyhome.jetpackcamerax.viewmodel.MainActivityModel
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : BaseActivity() {

    private lateinit var mBtnModel:MainActivityModel

    val observer = Observer<List<MainEntity>> { list ->
        list[0].let {
            img_main.setImageUrl(it.imgurl,"main.png")
            btn_livedata_test.text = it.btnText
        }
        list[1].let {
            img_camera.setImageUrl(it.imgurl,"camera.png")
            btn_camera.text = it.btnText
        }
        list[2].let {
            img_insert_card.setImageUrl(it.imgurl,"card.png")
            btn_insert.text = it.btnText
        }
        list[3].let {
            img_insert_user.setImageUrl(it.imgurl,"user.png")
            btn_user.text = it.btnText
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_main)
        setTitle("扉页")
        this.requestedOrientation
        setViewVisible(left = false, right = false)
        myLocationListener = openMyLocationListener()
        myLocationListener?.let {
            it.onCreate()
        }

    }

    private fun initView() {
        ll_parent.post {
            val childheighti = (ll_parent.height -ll_parent.paddingTop - ll_parent.paddingBottom)/ 4f;
            ll_main.layoutParams.height = childheighti.toInt()
            ll_camera.layoutParams.height = childheighti.toInt()
            ll_card_insert.layoutParams.height = childheighti.toInt()
            ll_user_insert.layoutParams.height = childheighti.toInt()
            img_main.layoutParams.height = (childheighti*5f/7).toInt()
          //  btn_livedata_test.layoutParams.height  = childheighti *2/7
            img_camera.layoutParams.height =  (childheighti*5f/7).toInt()
           // btn_camera.layoutParams.height = childheighti *2/7
            img_insert_card.layoutParams.height = (childheighti*5f/7).toInt()
         //   btn_insert.layoutParams.height = childheighti *2/7
            img_insert_user.layoutParams.height = (childheighti*5f/7).toInt()
          //  btn_user.layoutParams.height = childheighti *2/7
            ll_parent.requestLayout()
            ll_main.visibility = View.INVISIBLE
            ll_camera.visibility = View.INVISIBLE
            ll_card_insert.visibility = View.INVISIBLE
            ll_user_insert.visibility = View.INVISIBLE
            startAnimator(ll_main)
            val t = Handler()
            t.postDelayed(object : TimerTask() {
                override fun run() {
                    startAnimator(ll_camera)
                }
            },300L)
            t.postDelayed(object : TimerTask() {
                override fun run() {
                    startAnimator(ll_card_insert)
                }
            },500L)
            t.postDelayed(object : TimerTask() {
                override fun run() {
                    startAnimator(ll_user_insert)
                }
            },700L)
        }

        btn_livedata_test.setOnClickListener {
            startActivity(Intent(this@MainActivity, TabMainActivity::class.java))
        }

        btn_insert.setOnClickListener{
            startActivity<EntryDataActivity>{
                  Intent()
              }
        }
        //todo 权限管理
        btn_camera.setOnClickListener {
            val rxp = RxPermissions(this@MainActivity)
            rxp.request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) {
                        Toast.makeText(this@MainActivity,it.toString(),Toast.LENGTH_LONG).show()
                        val intent = Intent(this@MainActivity, PreviewActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@MainActivity,it.toString(),Toast.LENGTH_LONG).show()
                        getAppDetailSettingIntent()
                    }
                }
        }
    }

    private fun startAnimator(view:View) {
        val animator = ObjectAnimator.ofFloat(view,"translationX",(ll_parent.width-ll_parent.paddingStart).toFloat(),ll_parent.paddingStart.toFloat())
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 1000L
       animator.start()
        view.visibility = View.VISIBLE
    }

    private fun openMyLocationListener(): MyLocationListener {
        return object : MyLocationListener(this@MainActivity,getLifecycleinfo()) {
            override fun onCreate() {
                mLifecycle.addObserver(btn_camera)
                mBtnModel = ViewModelProvider(this@MainActivity).get(MainActivityModel::class.java)
                mBtnModel.buttonText.observe(this@MainActivity, observer)
                initView()
            }

            override fun onStart() {

            }

            override fun onResume() {

            }

            override fun onStop() {

            }

            override fun onDestroy() {

            }
        }
    }

    fun getAppDetailSettingIntent(){
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts("package", packageName, null)
        } /*else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName(
                "com.android.settings",
                "com.android.settings.InstalledAppDetails"
            )
            localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName)
        }*/
        startActivity(localIntent)
    }

}
