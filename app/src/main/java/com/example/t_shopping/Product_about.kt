package com.example.t_shopping

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class Product_about constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_about)
        getSupportActionBar()!!.hide()
        var Dec: AppCompatButton? = null
        var Htouse: AppCompatButton? = null
        var Revl: AppCompatButton? = null
        var back: ImageView? = null
        Dec = findViewById(R.id.p_desc)
        Htouse = findViewById(R.id.p_htouse)
        Revl = findViewById(R.id.p_rev)
        back = findViewById(R.id.product_back)
        back.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                startActivity(Intent(this@Product_about, Home::class.java))
            }
        })
        Dec.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                Dec.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg3))
                Dec.setTextColor(Color.parseColor("#FFFFFFFF"))
                Htouse.setTextColor(Color.parseColor("#FF030303"))
                Revl.setTextColor(Color.parseColor("#FF030303"))
                Htouse.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn2_bg))
                Revl.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn2_bg))
            }
        })
        Htouse.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                Htouse.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg3))
                Dec.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn2_bg))
                Revl.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn2_bg))
                Htouse.setTextColor(Color.parseColor("#FFFFFFFF"))
                Dec.setTextColor(Color.parseColor("#FF030303"))
                Revl.setTextColor(Color.parseColor("#FF030303"))
            }
        })
        Revl.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                Revl.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg3))
                Htouse.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn2_bg))
                Dec.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn2_bg))
                Revl.setTextColor(Color.parseColor("#FFFFFFFF"))
                Htouse.setTextColor(Color.parseColor("#FF030303"))
                Dec.setTextColor(Color.parseColor("#FF030303"))
            }
        })
    }
}