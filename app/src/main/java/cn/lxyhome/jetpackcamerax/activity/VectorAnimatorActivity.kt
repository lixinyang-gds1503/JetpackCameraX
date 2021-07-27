package cn.lxyhome.jetpackcamerax.activity

import android.animation.ObjectAnimator
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import cn.lxyhome.jetpackcamerax.R

class VectorAnimatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_animator)
        val img = findViewById<AppCompatImageView>(R.id.img)
        val playAnima:AnimatedVectorDrawable = ContextCompat.getDrawable(this,R.drawable.playtobg) as AnimatedVectorDrawable
        playAnima.registerAnimationCallback(object : Animatable2.AnimationCallback() {

            override fun onAnimationStart(drawable: Drawable?) {

            }

            override fun onAnimationEnd(drawable: Drawable?) {

            }
        })
        img.setOnClickListener {
            val drawable = img.drawable as AnimatedVectorDrawable
            if (drawable.isRunning) {
                drawable.stop()
            }else{
                drawable.start()
            }
        }
    }
}