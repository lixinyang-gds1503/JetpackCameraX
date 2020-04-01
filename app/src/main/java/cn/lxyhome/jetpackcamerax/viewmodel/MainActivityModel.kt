package cn.lxyhome.jetpackcamerax.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.lxyhome.jetpackcamerax.entity.MainEntity

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/3/19 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class MainActivityModel:ViewModel() {
    val buttonText:MutableLiveData<List<MainEntity>> = MutableLiveData<List<MainEntity>>().apply{
        val list = arrayListOf<MainEntity>()
        list.add(MainEntity("http://file02.16sucai.com/d/file/2014/0822/b44cd1310d09026f6dd1f0633a1cc2a5.jpg","主页"))
        list.add(MainEntity("http://pic1.win4000.com/wallpaper/2017-11-10/5a054ff030718.jpg","相机"))
        list.add(MainEntity("http://img4.imgtn.bdimg.com/it/u=3396849968,2501703168&fm=26&gp=0.jpg","卡片"))
        list.add(MainEntity("http://img3.imgtn.bdimg.com/it/u=2221945056,112347075&fm=26&gp=0.jpg","用户"))
        value = list
    }

}