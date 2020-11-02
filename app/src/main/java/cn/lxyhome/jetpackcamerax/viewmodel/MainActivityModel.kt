package cn.lxyhome.jetpackcamerax.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.entity.MainEntity
import cn.lxyhome.jetpackcamerax.util.toast

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/19 created.
 *""
 *
 */
class MainActivityModel:ViewModel() {
    val buttonText:MutableLiveData<List<MainEntity>> = MutableLiveData<List<MainEntity>>().apply{
        val list = arrayListOf<MainEntity>()
        list.add(MainEntity("http://file02.16sucai.com/d/file/2014/0822/b44cd1310d09026f6dd1f0633a1cc2a5.jpg","主页"))
        list.add(MainEntity("http://pic1.win4000.com/wallpaper/2017-11-10/5a054ff030718.jpg","相机"))
        list.add(MainEntity("http://img4.imgtn.bdimg.com/it/u=3396849968,2501703168&fm=26&gp=0.jpg","卡片"))
        list.add(MainEntity("http://img3.imgtn.bdimg.com/it/u=2221945056,112347075&fm=26&gp=0.jpg","用户"))
        list.add(MainEntity("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603881186750&di=e076bc58a6e9efb40a3d88529ee36f5d&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180608%2F6a3aa55f29134f6c9747007b3ec42215.jpeg","列表"))
        value = list
    }

    fun getBackCountSP() {
        JetpackApplication.self?.let {
            val sharedPreferences =
                it.getSharedPreferences("appBackCountCWorker", Context.MODE_PRIVATE)
            val count = sharedPreferences.getString("count", "")
            count?.let { msg ->
                toast(msg)
            }
        }
    }

    fun test(): String {
        return ""
    }

}