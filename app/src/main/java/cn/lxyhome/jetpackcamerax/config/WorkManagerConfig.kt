package cn.lxyhome.jetpackcamerax.config

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import java.util.concurrent.Executors

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/4/21 created.
 *""
 *
 */
class WorkManagerConfig(val context: Context) : Config {
    private var mTag:Int = Config.APP_WORK_MANAGER_CONFIG_TAG
    init {
        val build = Configuration.Builder()
            .setExecutor(Executors.newFixedThreadPool(8))
            .build()
        WorkManager.initialize(context, build)// 初始化 workmanager
    }
    override fun getConfigTag(): Int {
        return mTag
    }
    /*fun enqueue(work: WorkRequest) {
        getWorkManager(context).enqueue(work)
    }*/
    fun getWorkManager(context: Context):WorkManager {
        return WorkManager.getInstance(context)
    }
}