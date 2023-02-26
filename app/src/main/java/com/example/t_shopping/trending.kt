package com.example.t_shopping

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.firebase.database.*

class trending constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)
        var refresh: SwipeRefreshLayout? = null
        var databaseReference: DatabaseReference? = null
        var trending_rv: RecyclerView? = null
        var tending_gs: ArrayList<tending_gs?>? = null
        var back: ImageView? = null
        var tending_adapter: tending_Adapter? = null
        back = findViewById(R.id.backtrending)
        back.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                startActivity(Intent(this@trending, Home::class.java))
            }
        })
        refresh = findViewById(R.id.trefresh)
        refresh.setOnRefreshListener(object : OnRefreshListener {
            public override fun onRefresh() {
                val mediaPlayer: MediaPlayer =
                    MediaPlayer.create(this@trending, R.raw.mixkitarcadegamejumpcoin)
                mediaPlayer.start()
                Handler().postDelayed(object : Runnable {
                    public override fun run() {
                        recreate()
                        tending_adapter!!.notifyDataSetChanged()
                        refresh.setRefreshing(false)
                    }
                }, 2000)
            }
        })
        trending_rv = findViewById(R.id.trending_rv)
        val FraglayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        trending_rv.setLayoutManager(FraglayoutManager)
        getSupportActionBar()!!.hide()
        databaseReference = FirebaseDatabase.getInstance().getReference("Fragrance")
        trending_rv.setHasFixedSize(true)
        tending_gs = ArrayList()
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            public override fun onDataChange(snapshot: DataSnapshot) {
                for (values in snapshot.getChildren()) {
                    val Trending = values.getValue(tending_gs::class.java)
                    tending_gs!!.add(Trending)
                }
                tending_adapter = tending_Adapter(this@trending, tending_gs!!)
                trending_rv.setAdapter(tending_adapter)
                tending_adapter!!.notifyDataSetChanged()
            }

            public override fun onCancelled(error: DatabaseError) {}
        })
    }
}