package cn.lxyhome.jetpackcamerax.config

import android.content.Context
import com.tencent.bugly.crashreport.CrashReport

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/4/1 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class BuglyConfig(val context: Context):Config {
    init {
        CrashReport.initCrashReport(context, Config.buglyid, true)
    }
    override fun getConfigTag(): Int {
        return Config.APP_BUGLY_CONFIG_TAG
    }
}