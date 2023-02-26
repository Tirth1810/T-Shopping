package com.example.t_shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Logout constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        getSupportActionBar()!!.hide()
    }
}