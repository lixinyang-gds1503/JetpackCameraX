package cn.lxyhome.jetpackcamerax.coroutines

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/7/1 created.
 * 协程工具类
 */
object CoroutinesUtils {
    suspend fun bitmapSuspendble(context: Context,url: String):Bitmap {
       return suspendCoroutine<Bitmap> {
            thread {
                try {
                    it.resume(context.download(url))
                } catch (e: Exception) {
                    it.resumeWithException(e)
                }
            }
        }
    }

    private fun Context.download(url: String): Bitmap {
      return  Glide.with(this)
            .load(url)
            .asBitmap()
            .into(200,200)
            .get()
    }

    fun createCoroutions():Continuation<Unit> =
      suspend {}.createCoroutine(object : Continuation<Any> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Any>) {

            }
        })
}