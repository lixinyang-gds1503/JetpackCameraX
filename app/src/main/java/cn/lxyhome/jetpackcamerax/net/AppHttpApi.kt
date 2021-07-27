package cn.lxyhome.jetpackcamerax.net

import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 *   <网络请求>
 *This Class: lixinyang on 2021/3/12 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
object AppHttpApi:BaseHttpApi(),NetConfig {

    @JvmStatic
    fun executeJsonFromNet(map: Map<String, String?>?, netCallback: (Any?) -> Unit) {
        try {
            val url = getUrl(map)
            val request:Request = Request.Builder().url(url).build()
            val execute = mOkHttpClient.newCall(request).execute()
            netCallback(execute.body?.string())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getUrl(map: Map<String, String?>?): String {
        val url = StringBuilder()
        url.append(NetConfig.HTTP).append(NetConfig.WWW).append(NetConfig.HOST).append(NetConfig.COM)
        map?.forEach {
            url.append("&").append(it.value)
        }
        return url.toString()
    }

    override fun addHeaders(headermap: Map<String, String?>?) :Interceptor{
      return  object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val builder = chain.request().newBuilder()
                builder.addHeader("header1","header1")
                builder.addHeader("header2","header2")
                builder.addHeader("header3","header3")
                return chain.proceed(builder.build())
            }
        }
    }

    @JvmStatic
    fun enqueueJsonFromNet(url:String,netCallback: (Any?) -> Unit){
        val request:Request = Request.Builder().url(url).build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                netCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val string = response.body?.string()
                netCallback(string)
            }
        })
    }

    fun postJsonFromNet(map: Map<String, String?>,bodyString: String,netCallback: (Any?) -> Unit) {
        val body = bodyString.toRequestBody()
        val request:Request = Request.Builder().url(getUrl(map)).post(body).build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
               netCallback(response.body?.string())
            }
        })
    }
}

/**
 * 基类
 */
abstract class BaseHttpApi {
    val mOkHttpClient: OkHttpClient by lazy {
        OkHttpClient().newBuilder().connectTimeout(5000,TimeUnit.MILLISECONDS)
            .readTimeout(5000,TimeUnit.MILLISECONDS)
            .writeTimeout(5000,TimeUnit.MILLISECONDS)
            .addInterceptor(this.addHeaders())
            .addInterceptor(ToKenInerceptor())
            .build()
    }

    fun getClient():OkHttpClient {
        return mOkHttpClient
    }

    abstract fun getUrl(map: Map<String, String?>?): String

    abstract fun addHeaders(headermap: Map<String, String?>? = null):Interceptor

}

class ToKenInerceptor : Interceptor {

    companion object{
        val UTF8: Charset = Charset.forName("UTF-8")
        var TOKEN = "test_token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return request.run {
            url.newBuilder()
                .scheme(url.scheme)
                .host(url.host)
                .addQueryParameter("access_token", TOKEN)
        }.let {
            val newrequest =
                request.newBuilder().addHeader("access_token", TOKEN)
                    .method(request.method, request.body).url(it.build()).build()
            newrequest
        }.let {
            val proceed = chain.proceed(it)
            proceed
        }
    }

}