package com.example.t_shopping

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager.widget.PagerAdapter
import com.google.firebase.auth.FirebaseAuth

class Swipeviepageradapter constructor(var context: Context) : PagerAdapter() {
    var firebaseAuth: FirebaseAuth? = null
    public override fun getCount(): Int {
        return 3
    }

    public override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    public override fun instantiateItem(container: ViewGroup, position: Int): Any {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth!!.getCurrentUser() != null) {
            context.startActivity(Intent(context, Login::class.java))
        }
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.swipe_screen, container, false)
        val logo: ImageView = view.findViewById(R.id.into_logo)
        val Sub: TextView = view.findViewById(R.id.subject_txt)
        val dec: TextView = view.findViewById(R.id.subject_desc_txt)
        val getstarted: AppCompatButton = view.findViewById(R.id.get_start)
        val ind1: ImageView = view.findViewById(R.id.s1)
        val ind2: ImageView = view.findViewById(R.id.s2)
        val ind3: ImageView = view.findViewById(R.id.s3)
        val next: ImageView = view.findViewById(R.id.next)
        val back: ImageView = view.findViewById(R.id.back)
        getstarted.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                val intent: Intent = Intent(context, Login::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        })
        next.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                Introduction.Companion.swipe!!.setCurrentItem(position + 1)
            }
        })
        back.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                Introduction.Companion.swipe!!.setCurrentItem(position - 1)
            }
        })
        when (position) {
            0 -> {
                logo.setImageResource(R.drawable.shopping)
                ind1.setImageResource(R.drawable.selected)
                ind2.setImageResource(R.drawable.unselected)
                ind3.setImageResource(R.drawable.unselected)
                Sub.setText("Best Product")
                back.setVisibility(View.GONE)
                dec.setText("You can find best product with multiple brands.where you can select brand which you want")
            }
            1 -> {
                logo.setImageResource(R.drawable.delivery)
                ind1.setImageResource(R.drawable.unselected)
                ind2.setImageResource(R.drawable.selected)
                ind3.setImageResource(R.drawable.unselected)
                back.setVisibility(View.VISIBLE)
                Sub.setText("Fast Delivery")
                dec.setText("Fast Delivery of your product at your place absolutely free")
            }
            2 -> {
                next.setVisibility(View.GONE)
                logo.setImageResource(R.drawable.visa)
                ind1.setImageResource(R.drawable.unselected)
                ind2.setImageResource(R.drawable.unselected)
                ind3.setImageResource(R.drawable.selected)
                Sub.setText("Cash on delivery")
                back.setVisibility(View.VISIBLE)
                next.setVisibility(View.GONE)
                dec.setText("Cash on delivery option available you can pay by online mode also")
            }
        }
        container.addView(view)
        return view
    }

    public override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}