package com.example.t_shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

class Introduction : AppCompatActivity() {
    var swipeviepageradapter: Swipeviepageradapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_introduction)
        val swipe: ViewPager = findViewById(R.id.swipe_vp)
        swipeviepageradapter = Swipeviepageradapter(this)
        swipe.adapter = swipeviepageradapter
    }

    companion object {
        var swipe: ViewPager? = null
    }
}