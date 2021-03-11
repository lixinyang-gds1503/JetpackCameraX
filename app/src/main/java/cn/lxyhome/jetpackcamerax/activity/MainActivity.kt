package cn.lxyhome.jetpackcamerax.activity

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.activity.ui.login.LoginActivity
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.entity.MainEntity
import cn.lxyhome.jetpackcamerax.notifi.NotifiManager
import cn.lxyhome.jetpackcamerax.util.setImageUrl
import cn.lxyhome.jetpackcamerax.util.showBackDialog
import cn.lxyhome.jetpackcamerax.util.startActivity
import cn.lxyhome.jetpackcamerax.util.toast
import cn.lxyhome.jetpackcamerax.viewmodel.MainActivityModel
import cn.lxyhome.jetpackcamerax.work.MyBackCountCoroutineWorker
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : BaseActivity() {

    private lateinit var mBtnModel:MainActivityModel
    private var isfinish = false
    private val build = OneTimeWorkRequestBuilder<MyBackCountCoroutineWorker>()
        .setConstraints(Constraints.NONE)
        .setInputData(Data.Builder().build()).build()

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
        list[4].let {
            img_menulist.setImageUrl(it.imgurl,"menu.png")
            btn_menulist.text = it.btnText
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_main)
        setTitle("扉页")
        this.requestedOrientation
        setViewVisible(left = false, right = false)
        myLocationListener = openMyLocationListener()
        myLocationListener.onCreate()

    }

    override fun onResume() {
        super.onResume()
        /*myLocationListener?.let {
            it.onResume()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        myLocationListener.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            showBackDialog{
                isfinish = it
                if (it) {
                    super.onKeyDown(keyCode, event)
                }
            }
            return isfinish
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initView() {
        ll_parent.post {
        /*    val childheighti = (ll_parent.height -ll_parent.paddingTop - ll_parent.paddingBottom)/ 4f;
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
            ll_parent.requestLayout()*/
            ll_main.visibility = View.INVISIBLE
            ll_camera.visibility = View.INVISIBLE
            ll_card_insert.visibility = View.INVISIBLE
            ll_user_insert.visibility = View.INVISIBLE
            ll_menulist.visibility = View.INVISIBLE
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

            t.postDelayed(object : TimerTask() {
                override fun run() {
                    startAnimator(ll_menulist)
                }
            },900L)
        }

        btn_menulist.setOnClickListener{
            startActivity<MenuListActivity> {
                Intent()
            }
        }

        btn_livedata_test.setOnClickListener {
            startActivity(Intent(this@MainActivity, TabMainActivity::class.java))
          //  startActivity(Intent(this@MainActivity, ESFStoreDetailActivity::class.java))
        }
        ll_user_insert.setOnClickListener {
            startActivity<LoginActivity> {
                Intent()
            }
        }

        btn_insert.setOnClickListener{
            startActivity<EntryDataActivity>{
                  Intent()
              }
        }
        img_main.setOnClickListener {
            NotifiManager.notifi(this@MainActivity)
        }
        // 权限管理
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
        val animator = ObjectAnimator.ofFloat(view,"translationX",(ll_parent.width-ll_parent.paddingStart-ll_parent.paddingEnd).toFloat(),0f)
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
                mBtnModel.getBackCountSP()
                JetpackApplication.getWorkManager(this@MainActivity)?.let {
                    Log.e("WorkManager",it.toString())
                    it.enqueue(build)
                    it.getWorkInfoByIdLiveData(build.id).observe(this@MainActivity,
                        Observer {workInfo->
                            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                                val sharedPreferences =
                                    getSharedPreferences("appBackCountCWorker", Context.MODE_PRIVATE)
                                val count = sharedPreferences.getString("count", "")
                                count?.let { msg ->
                                    toast(msg)
                                }
                                val string = workInfo.outputData.getString("count")
                                Log.e("mainactivity", string?:"")
                            }else {
                                Log.e("mainactivity",workInfo.state.toString())
                            }
                        })
                }

            }

            override fun onStart() {

            }

            override fun onResume() {

            }

            override fun onStop() {

            }

            override fun onDestroy() {
                    JetpackApplication.getWorkManager(this@MainActivity)?.let {
                        it.cancelWorkById(build.id)
                    }
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
