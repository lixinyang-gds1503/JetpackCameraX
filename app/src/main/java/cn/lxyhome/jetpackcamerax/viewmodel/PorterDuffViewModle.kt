package cn.lxyhome.jetpackcamerax.viewmodel

import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/11/20 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class PorterDuffViewModle : ViewModel() {
    val strLiveData:LiveData<List<HashMap<String,PorterDuff.Mode>>> = MutableLiveData<List<HashMap<String,PorterDuff.Mode>>>().also {
        val list = mutableListOf<HashMap<String,PorterDuff.Mode>>()
        //所绘制不会提交到画布上
        val CLEAR = HashMap<String, PorterDuff.Mode>()
        CLEAR.put("PorterDuff.Mode.CLEAR",PorterDuff.Mode.CLEAR)
        list.add(CLEAR)
        //显示上层绘制的图像
      //  new PorterDuffXfermode(PorterDuff.Mode.SRC),
        val SRC = HashMap<String, PorterDuff.Mode>()
        SRC.put("PorterDuff.Mode.SRC",PorterDuff.Mode.SRC)
        list.add(SRC)
        //显示下层绘制图像
      //  new PorterDuffXfermode(PorterDuff.Mode.DST),
        val DST = HashMap<String, PorterDuff.Mode>()
        DST.put("PorterDuff.Mode.DST",PorterDuff.Mode.DST)
        list.add(DST)
        //正常绘制显示，上下层绘制叠盖
       // new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
        val SRC_OVER = HashMap<String, PorterDuff.Mode>()
        SRC_OVER.put("PorterDuff.Mode.SRC_OVER",PorterDuff.Mode.SRC_OVER)
        list.add(SRC_OVER)

        //上下层都显示，下层居上显示
       // new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
        val DST_OVER = HashMap<String, PorterDuff.Mode>()
        DST_OVER.put("PorterDuff.Mode.DST_OVER",PorterDuff.Mode.DST_OVER)
        list.add(DST_OVER)
        //取两层绘制交集，显示上层
      //  new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
        val SRC_IN = HashMap<String, PorterDuff.Mode>()
        SRC_IN.put("PorterDuff.Mode.SRC_IN",PorterDuff.Mode.SRC_IN)
        list.add(SRC_IN)
        //取两层绘制交集，显示下层
      //  new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
        val DST_IN = HashMap<String, PorterDuff.Mode>()
        DST_IN.put("PorterDuff.Mode.DST_IN",PorterDuff.Mode.DST_IN)
        list.add(DST_IN)
        //取上层绘制非交集部分，交集部分变成透明
      //  new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
        val SRC_OUT = HashMap<String, PorterDuff.Mode>()
        SRC_OUT.put("PorterDuff.Mode.SRC_OUT",PorterDuff.Mode.SRC_OUT)
        list.add(SRC_OUT)

        //取下层绘制非交集部分，交集部分变成透明
       // new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
        val DST_OUT = HashMap<String, PorterDuff.Mode>()
        DST_OUT.put("PorterDuff.Mode.DST_OUT",PorterDuff.Mode.DST_OUT)
        list.add(DST_OUT)
        //取上层交集部分与下层非交集部分
      //  new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
        val SRC_ATOP = HashMap<String, PorterDuff.Mode>()
        SRC_ATOP.put("PorterDuff.Mode.SRC_ATOP",PorterDuff.Mode.SRC_ATOP)
        list.add(SRC_ATOP)
        //取下层交集部分与上层非交集部分
      //  new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
        val DST_ATOP = HashMap<String, PorterDuff.Mode>()
        DST_ATOP.put("PorterDuff.Mode.DST_ATOP",PorterDuff.Mode.DST_ATOP)
        list.add(DST_ATOP)
        //去除两图层交集部分
       // new PorterDuffXfermode(PorterDuff.Mode.XOR),
        val XOR = HashMap<String, PorterDuff.Mode>()
        XOR.put("PorterDuff.Mode.XOR",PorterDuff.Mode.XOR)
        list.add(XOR)
        //取两图层全部区域，交集部分颜色加深
      //  new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
        val DARKEN = HashMap<String, PorterDuff.Mode>()
        DARKEN.put("PorterDuff.Mode.DARKEN",PorterDuff.Mode.DARKEN)
        list.add(DARKEN)
       // //取两图层全部区域，交集部分颜色点亮
      //  new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
        val LIGHTEN = HashMap<String, PorterDuff.Mode>()
        LIGHTEN.put("PorterDuff.Mode.LIGHTEN",PorterDuff.Mode.LIGHTEN)
        list.add(LIGHTEN)
        //取两图层交集部分，颜色叠加
       // new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
        val MULTIPLY = HashMap<String, PorterDuff.Mode>()
        MULTIPLY.put("PorterDuff.Mode.MULTIPLY",PorterDuff.Mode.MULTIPLY)
        list.add(MULTIPLY)
        //取两图层全部区域，交集部分滤色
        //new PorterDuffXfermode(PorterDuff.Mode.SCREEN),
        val SCREEN = HashMap<String, PorterDuff.Mode>()
        SCREEN.put("PorterDuff.Mode.SCREEN",PorterDuff.Mode.SCREEN)
        list.add(SCREEN)
        //取两图层全部区域，交集部分饱和度相加
        //new PorterDuffXfermode(PorterDuff.Mode.ADD)
        val ADD = HashMap<String, PorterDuff.Mode>()
        ADD.put("PorterDuff.Mode.ADD",PorterDuff.Mode.ADD)
        list.add(ADD)
        //取两图层全部区域，交集部分叠加
       // new PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
        val OVERLAY = HashMap<String, PorterDuff.Mode>()
        OVERLAY.put("PorterDuff.Mode.OVERLAY",PorterDuff.Mode.OVERLAY)
        list.add(OVERLAY)
        it.value = list
    }
}