package cn.lxyhome.jetpackcamerax.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.net.toUri
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.util.DeleteAll
import cn.lxyhome.jetpackcamerax.util.deleteMediaData
import cn.lxyhome.jetpackcamerax.util.toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.gallery_adapter_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class GalleryActivity : BaseActivity() {
    private  val fileList:MutableList<File> = mutableListOf()
    private lateinit var gallery:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setview(R.layout.activity_gallery)
        setTitle("相册画廊")
        this.externalMediaDirs.first()?.let {
            addFile(it)
        }
        gallery = findViewById(R.id.gallery)
        PagerSnapHelper().attachToRecyclerView(gallery)
        gallery.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        gallery.adapter = GalleryAdapter()
    }


    override var more_OnClick: () -> Unit = {
        if (fileList.isEmpty()) {
            cn.lxyhome.jetpackcamerax.util.toast("暂无数据")
        } else{
            val deleteDialog = AlertDialog.Builder(this)
                .setItems(arrayOf("删除")) { dialog, index ->
                    dialog.dismiss()
                    if (index==0) {
                        this.externalMediaDirs.first()?.let {
                            if (it.exists()) {
                                deleteMediaData(it)
                                it.DeleteAll()
                            }
                        }
                        fileList.clear()
                        gallery.adapter?.notifyDataSetChanged()
                    }
                }
                .create()
            deleteDialog.show()
        }
    }

    private fun addFile(tempFile: File) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (tempFile.exists()) {
                if (tempFile.isDirectory) {
                    tempFile.listFiles()?.forEach {
                        if (it.isDirectory) {
                            addFile(it)
                        } else {
                            fileList.add(it)
                        }
                    }
                } else {
                    fileList.add(tempFile)
                }
            }
        }
    }

    inner class GalleryAdapter() : RecyclerView.Adapter<GalleryHV>() {
        @SuppressLint("InflateParams")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHV {
            val root = LayoutInflater.from(this@GalleryActivity)
                .inflate(R.layout.gallery_adapter_item, parent,false)
            return GalleryHV(root)
        }

        override fun getItemCount(): Int {
            return fileList.size
        }

        override fun onBindViewHolder(holder: GalleryHV, position: Int) {
            holder.bind(fileList[position])
        }
    }

   inner class GalleryHV(var item: View) : RecyclerView.ViewHolder(item) {
        private var preview: ImageView = item.findViewById(R.id.preview)

        fun bind(file: File) {
            preview.apply {
                post {
                   setPadding(10)
                    Glide.with(this@GalleryActivity)
                        .load(file)
                        .into(this)
                }
            }
        }
    }
}