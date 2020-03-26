package cn.lxyhome.jetpackcamerax.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.util.startActivity
import cn.lxyhome.jetpackcamerax.viewmodel.MyButtonModel
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var mBtnModel:MyButtonModel

    val observer = Observer<String> { s ->
        btn_livedata_test.run {
            text = s
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_main)
        setTitle("扉页")
        setViewVisible(left = false, right = false)
        myLocationListener = openMyLocationListener()
        myLocationListener?.let {
            it.onCreate()
        }

    }

    private fun initView() {
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

    private fun openMyLocationListener(): MyLocationListener {
        return object : MyLocationListener(this@MainActivity,getLifecycleinfo()) {
            override fun onCreate() {
                mLifecycle.addObserver(btn_camera)
                mBtnModel = ViewModelProvider(this@MainActivity).get(MyButtonModel::class.java)

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
