package cn.lxyhome.jetpackcamerax.work

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.*

/**
 *   <后台计数的Worker>
 *This Class: lixinyang on 2020/4/21 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class MyBackCountCoroutineWorker(val appContext: Context, private val params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    private var count: Long = 0L
    val CEH = CoroutineExceptionHandler { coroutineContext, throwable ->
        Result.failure()
    }

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO + CEH) {
            val job = async {
                var tip = true
                var startime: Long = System.currentTimeMillis()
                do {
                    val endtime = System.currentTimeMillis()
                    val l = endtime - startime
                    if (l > 5000L) {
                        if (l > 10000L) {
                            tip = false
                            val sharedPreferences = appContext.getSharedPreferences(
                                "appBackCountCWorker",
                                Context.MODE_PRIVATE
                            )
                            val edit = sharedPreferences.edit()
                            edit.putString("count", count.toString())
                            edit.commit()
                            Log.e("MBCCW","dowork:${count}")
                        }
                        count++
                    }
                } while (tip)
                1
            }


            // awaitAll will throw an exception if a download fails, which CoroutineWorker will treat as a failure
            val await = job.await()
            Log.e("await","dowork:${await.toInt()}")
            if (await == 1) {
                Result.success(Data.Builder().putLong("count",count).build())
            } else {
                Result.failure()
            }
        }
    }

}