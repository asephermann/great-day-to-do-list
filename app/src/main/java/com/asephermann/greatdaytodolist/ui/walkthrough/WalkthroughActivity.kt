package com.asephermann.greatdaytodolist.ui.walkthrough

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.asephermann.greatdaytodolist.R
import com.asephermann.greatdaytodolist.ui.MainActivity
import kotlinx.android.synthetic.main.activity_walkthrough.*


class WalkthroughActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_walkthrough)

        imageButton.setOnClickListener(this)

        firstTime()
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.imageButton -> {
                val intent = Intent (this@WalkthroughActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun firstTime() {
        val sharedTime = getSharedPreferences("isFirstTime", 0)
        if (sharedTime.getBoolean("firstTime", true)) {
            sharedTime.edit().putBoolean("firstTime", false).apply()
        } else {
            val intent = Intent (this@WalkthroughActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}