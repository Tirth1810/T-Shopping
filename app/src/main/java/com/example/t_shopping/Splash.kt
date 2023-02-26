package com.example.t_shopping

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class Splash constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getSupportActionBar()!!.hide()
        Handler().postDelayed(object : Runnable {
            public override fun run() {
                startActivity(Intent(this@Splash, Introduction::class.java))
            }
        }, 2000)
    }
}