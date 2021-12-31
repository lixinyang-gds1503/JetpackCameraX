package cn.lxyhome.jetpackcamerax.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.dialog.BackDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/24 created.
 *""
 *
 */

fun loge(message: String?) {
    Log.e("JetPack",message+"")
}


fun dip2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}


fun BaseActivity.toast(message: String) {
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

fun JetpackApplication.toast(message: String) {
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

fun toast(message: String) {
    Toast.makeText(JetpackApplication.self,message,Toast.LENGTH_LONG).show()
}

fun String?.isNotNullorEmpty():Boolean {
    if (this==null) {
        return false
    }
    this.let {
        return !(this==""|| this.trim()=="" || this.isEmpty())
    }
}



inline fun <reified T : Activity> Activity.startActivity(block:(intent:Intent)->Unit) {
    val intent = Intent()
    intent.setClass(this,T::class.java)
    block(intent)
    this.startActivity(intent)
    //overridePendingTransition()
}

fun AppCompatActivity.showBackDialog(function: (Boolean) -> Unit) {
    BackDialog(function)
        .show(supportFragmentManager.beginTransaction(),"backDialog")
}

fun ImageView.setImageUrl(url: String?, name:String) {
    url?.let {
        Glide.with(context).load(url)
            .asBitmap()
            .error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher)
            .listener(object : RequestListener<String, Bitmap> {
                override fun onException(
                    p0: Exception?,
                    p1: String?,
                    p2: Target<Bitmap>?,
                    p3: Boolean
                ): Boolean {
                    val localfile =
                        File(context.filesDir.toString() + File.separator + MAIN_IMAGEURL_DIR, name)
                    Log.i("PubUtils-onException", "local:$name")
                    if (localfile.exists()) {
                        Glide.with(context).load(localfile).into(this@setImageUrl)
                    } else {
                      //  this@setImageUrl.setImageUrl(url, name)
                    }
                    return true;
                }

                override fun onResourceReady(
                    p0: Bitmap?,
                    p1: String?,
                    p2: Target<Bitmap>?,
                    p3: Boolean,
                    p4: Boolean
                ): Boolean {
                    val localfile =
                        File(context.filesDir.toString() + File.separator + MAIN_IMAGEURL_DIR, name)
                    if (localfile.exists()) {
                        Log.i("PubUtils", "local:$name")
                        Glide.with(context).load(localfile).into(this@setImageUrl)
                    } else {
                        Log.i("PubUtils", "network")
                        this@setImageUrl.setImageBitmap(p0)
                    }
                    return saveFileToLocal(p0, name)
                }
            })
            .into(this)
    }
}
private const val MAIN_IMAGEURL_DIR = "mainDir/imageurl"
private fun saveFileToLocal(bitmap: Bitmap?,filename:String): Boolean {
    val filedir = JetpackApplication.self?.filesDir
    val mainDir = File(filedir.toString(), MAIN_IMAGEURL_DIR)

    try {
        if (!mainDir.exists()) {
            mainDir.mkdirs()
        }
        //toast(mainDir.toString())
        val imgfile = File(mainDir.toString() + File.separator, filename)
        if (imgfile.exists()) {
            return true
        }
        val outputStream = FileOutputStream(imgfile)
        bitmap?.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
        outputStream.flush()
        outputStream.close()

    }catch (e:FileNotFoundException){
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return true
}

/**
 * 删除文件
 */
fun File.DeleteAll():Boolean {
    if (this.exists()) {
        if (this.isDirectory) {
            try {
                this.listFiles()?.forEach {
                    if (it.isDirectory) {
                        it.DeleteAll()
                    } else{
                        it.delete()
                    }
                }
               return this.delete()
            } catch (e: Exception) {
                loge(e.message)
            }
        } else{
           return delete()
        }
    }
    return false
}
private val ImagURI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
fun deleteMediaData(file:File) {
    if (file.isDirectory) {
        file.listFiles()?.forEach {
            if (it.isDirectory) {
                deleteMediaData(it)
            } else{
                JetpackApplication.self?.contentResolver?.let { cr->
                    try {
                        val url = MediaStore.Images.Media.DATA + "=?"
                        val delete = cr.delete(ImagURI, url, arrayOf(it.path))
                        delete
                    } catch (e: Exception) {
                        loge(e.message)
                    }
                }
            }
        }
    }

}
