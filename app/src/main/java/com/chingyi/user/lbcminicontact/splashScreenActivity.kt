package com.chingyi.user.lbcminicontact

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.miguelcatalan.materialsearchview.utils.AnimationUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash_screen.*


class splashScreenActivity : AppCompatActivity() {
val  SPLASH_TIME_OUT :Long = 3300
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTrasparent()
        setContentView(R.layout.activity_splash_screen)
        val animImage:Animation = AnimationUtils.loadAnimation(this,R.anim.from_top_image)
        val animText:Animation =AnimationUtils.loadAnimation(this,R.anim.from_top_text_text)
        val animCpyRight:Animation=AnimationUtils.loadAnimation(this,R.anim.from_bottom_cpy)
         titleTextView.startAnimation(animText)
        splash_imgView.startAnimation(animImage)
        copyRight.startAnimation(animCpyRight)


        Handler().postDelayed({
            val intent = Intent(this@splashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)



    }

    fun setStatusBarTrasparent():Unit{
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

    }
}
