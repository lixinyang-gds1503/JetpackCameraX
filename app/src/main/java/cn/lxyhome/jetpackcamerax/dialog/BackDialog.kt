package cn.lxyhome.jetpackcamerax.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.dialog.model.BackDialogModel
import cn.lxyhome.jetpackcamerax.util.setImageUrl

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/4/1 created.
 *""
 *
 */
class BackDialog(val function:(Boolean)->Unit):DialogFragment() {
    private lateinit var model:BackDialogModel
    private lateinit var img_back_src:ImageView
    private lateinit var tv_enter:TextView
    private lateinit var tv_close:TextView
    private lateinit var tv_content:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(DialogFragment.STYLE_NO_TITLE,0)
        isCancelable = false
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.run {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            if (this.window != null) {
                this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
        activity?.run {
           val layoutInflater =  this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(R.layout.back_dialog_view,null)
            init(view)
            model = ViewModelProvider(this).get(BackDialogModel::class.java)
            model.DataModel.observe(activity!!, Observer {
                img_back_src.setImageUrl(it.Imagurl,"backimg.png")
                tv_close.text = it.btnClose
                tv_enter.text = it.btnEnter
                tv_content.text = it.text
            })
            return  view
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun init(view: View) {
        img_back_src = view.findViewById(R.id.img_back_src)
        tv_close = view.findViewById(R.id.tv_close)
        tv_enter = view.findViewById(R.id.tv_enter)
        tv_content = view.findViewById(R.id.tv_content)
        tv_enter.setOnClickListener {
            function(false)
            dismiss()
        }
        tv_close.setOnClickListener {
            function(true)
            dismiss()
        }
    }
}