package cn.lxyhome.jetpackcamerax.dialog

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.room.util.StringUtil
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.util.dip2px
import cn.lxyhome.jetpackcamerax.view.LxyHomeImageView

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/11/20 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class PorterDuffDialog(val title:String,val mode:PorterDuff.Mode):DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(DialogFragment.STYLE_NO_TITLE,0)
        isCancelable = true
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater =  activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view:View? = layoutInflater.inflate(R.layout.porterduff_dialog_item,null)
        view?.findViewById<TextView>(R.id.tv_title)?.text = title
        view?.findViewById<LxyHomeImageView>(R.id.lxy_img)?.setMode(mode)
        return view?:super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(dip2px(requireActivity(),300f),dip2px(requireActivity(),300f))
    }

}