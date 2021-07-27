package cn.lxyhome.jetpackcamerax.palette

import android.graphics.Bitmap
import android.graphics.Color
import androidx.palette.graphics.Palette

/**
 *   <调色板练习>
 *This Class: lixinyang on 2020/5/13 created.
 *
 */
object PaletteManager {

    fun getMainColors(bitmap: Bitmap):List<Int?> {
        val list = arrayListOf<Int?>()
        Palette.from(bitmap).generate { palette ->
            // 获取到柔和的深色的颜色（可传默认值）
            list.add(palette?.getDarkMutedColor(Color.BLUE))
            // 获取到活跃的深色的颜色（可传默认值）
            list.add(palette?.getDarkVibrantColor(Color.BLUE))
            // 获取到柔和的明亮的颜色（可传默认值）
            list.add(palette?.getLightMutedColor(Color.BLUE))
            // 获取到活跃的明亮的颜色（可传默认值）
            list.add(palette?.getLightVibrantColor(Color.BLUE))
            // 获取图片中最活跃的颜色（也可以说整个图片出现最多的颜色）（可传默认值）
            list.add(palette?.getVibrantColor(Color.BLUE))
            // 获取图片中一个最柔和的颜色（可传默认值）
            list.add(palette?.getMutedColor(Color.BLUE))
        }
        return list
    }
}