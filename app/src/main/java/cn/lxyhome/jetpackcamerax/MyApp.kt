package cn.lxyhome.jetpackcamerax

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/18 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class MyApp:Application(),CameraXConfig.Provider {
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    override fun onCreate() {
        super.onCreate()

    }
}