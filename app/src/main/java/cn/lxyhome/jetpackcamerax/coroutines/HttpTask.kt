package cn.lxyhome.jetpackcamerax.coroutines

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import cn.lxyhome.jetpackcamerax.util.loge
import kotlinx.coroutines.*
import java.io.Closeable
import java.util.concurrent.Executors
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 *  测试协程。
 */


interface  CoroutineSpoceErrorListener{
    fun onError(e:Throwable)
}


private class ExceptionHadler(var errorListener: CoroutineSpoceErrorListener? = null) : CoroutineExceptionHandler {
    public companion object Key : CoroutineContext.Key<ExceptionHadler>
    override val key: CoroutineContext.Key<*>
        get() = ExceptionHadler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        try {
            exception.printStackTrace()
            errorListener?.run {
                onError(exception)
            }
        } catch (e: Exception) {
            errorListener?.run {
                onError(e)
            }
        }
    }
}

fun View.getViewAutoDisposeScopeCancellable(call:suspend CoroutineScope.()->Unit): Job {
  return autoDisposeScope.launch {
      call()
      suspendCancellableCoroutine<Unit> { con ->
          con.invokeOnCancellation {
              if (it is CancellationException) {
                  this.coroutineContext[ExceptionHadler]?.handleException(this.coroutineContext, it)
              }
          }
      }
  }
}

private val UI: CoroutineDispatcher = Dispatchers.Main

private val View.autoDisposeScope: CoroutineScope
    get() = SafeCoroutineSpose(UI + object : ContinuationInterceptor {
        override val key: CoroutineContext.Key<*>
            get() = ContinuationInterceptor

        override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
            continuation.context[Job]?.let {
                this@autoDisposeScope.addOnAttachStateChangeListener(
                    ViewListener(
                        this@autoDisposeScope,
                        it
                    )
                )
            }

            return continuation
        }
    }, object : CoroutineSpoceErrorListener {
        override fun onError(e: Throwable) {
            loge(e.printStackTrace().toString())
        }
    })

private class ViewListener(var view:View, var job:Job) :
    View.OnAttachStateChangeListener,CompletionHandler{
    override fun onViewAttachedToWindow(v: View?) {
    }

    override fun onViewDetachedFromWindow(v: View?) {
        view.removeOnAttachStateChangeListener(this)
        job.cancel()
    }

    override fun invoke(cause: Throwable?) {
        view.removeOnAttachStateChangeListener(this)
        job.cancel()
    }

}

/**
 * 自定义协程空间，可以释放，捕获异常。
 */
private class SafeCoroutineSpose(val context: CoroutineContext,var errorListener: CoroutineSpoceErrorListener? = null) : CoroutineScope, Closeable {
    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + context + ExceptionHadler(errorListener)

    override fun close() {
        coroutineContext.cancelChildren()
    }

}

/**
 *  一般性质的http请求体
 */
fun getHttpTask(
    lifecycleScope: LifecycleCoroutineScope,
    errorListener: CoroutineSpoceErrorListener? = null,
    timeoutCall: Unit,
    suspendCall: suspend CoroutineScope.() -> Unit
) {
    val content = lifecycleScope.coroutineContext + SupervisorJob() + ExceptionHadler(errorListener)
    lifecycleScope.launch(content + Dispatchers.IO) {
        loge("${Thread.currentThread().name}  $content")
        val withTimeoutOrNull = try {
            withTimeoutOrNull(5000L, suspendCall)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (withTimeoutOrNull == null) { //表示超时了
            launch(UI) { //主线程调用超时回调
                loge("${Thread.currentThread().name}  ${content[Job]}")
                timeoutCall
               /* Executors.newSingleThreadExecutor().asCoroutineDispatcher().use {

                }*/
            }
        }

        suspendCancellableCoroutine<Unit> { con ->
            con.invokeOnCancellation {
                if (it is CancellationException) {
                    content[ExceptionHadler]?.handleException(content, it)
                }
            }
        }
    }
}