package com.example.t_shopping

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class Home : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var Hair: TextView? = null
        var Body: TextView? = null
        var Face: TextView? = null
        var Fragrance: TextView? = null
        var ALL: TextView? = null
        var Trending: AppCompatButton? = null
        var product_adapter: Product_adapter? = null
        var body_adapter: Body_adapter? = null
        var face_adapter: Face_adapter? = null
        var fragadapter: Fragadapter? = null
        var hair_adapter: Hair_adapter? = null
        var databaseReference: DatabaseReference? = null
        var products: ArrayList<Product_gs?>? = null
        var Nodata: LottieAnimationView? = null
        var Frag_gs: ArrayList<Frag_Gs?>? = null
        var Body_gs: ArrayList<Body_Gs?>? = null
        var Hair_Gs: ArrayList<Hair_Gs?>? = null
        var Face_gs: ArrayList<Face_Gs?>? = null
        var filter: ImageView? = null
        var Search_main: ImageView? = null
        var Logout: LottieAnimationView? = null
        var Logout_txt: TextView? = null
        var Search: EditText? = null
        var Uname: TextView? = null
        var Load: ProgressBar? = null
        var auth: FirebaseAuth? = null
        var Search_btn: AppCompatButton? = null
        var Cart_btn: AppCompatButton? = null
        var Productcategory: RecyclerView? = null
        var Product_home: RecyclerView? = null
        var Frag_rv: RecyclerView? = null
        var Body_rv: RecyclerView? = null
        var Face_rv: RecyclerView? = null
        var Hair_rv: RecyclerView? = null
        var refresh: SwipeRefreshLayout? = null
        var sp: SharedPreferences? = null

        val connectivityManager =
            applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected || !networkInfo.isAvailable) {
            val builder = AlertDialog.Builder(this@Home, R.style.CustomAlertDialog)
            val viewGroup = findViewById<ViewGroup>(android.R.id.content)
            builder.setCancelable(false)
            val dialogView =
                LayoutInflater.from(this@Home).inflate(R.layout.no_internet, viewGroup, false)
            builder.setView(dialogView)
            val alertDialog = builder.create()
            dialogView.findViewById<View>(R.id.try_again).setOnClickListener {
                val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
                mediaPlayer.start()
                recreate()
            }
            alertDialog.show()
        }
        Nodata = findViewById(R.id.nodata)
        Load = findViewById(R.id.home_load)
        auth = FirebaseAuth.getInstance()
        sp = applicationContext.getSharedPreferences("Userinfo", MODE_PRIVATE)
        val Name = sp.getString("name", "")
        Logout_txt = findViewById(R.id.Logout)
        Logout = findViewById(R.id.Logout_img)
        refresh = findViewById(R.id.s_refresh)
        Search_main = findViewById(R.id.search_btn_home_main)
        Search = findViewById(R.id.search_product)
        Cart_btn = findViewById(R.id.cart_btn)
        Face_rv = findViewById(R.id.face_home_rv)
        Frag_rv = findViewById(R.id.frag_home_rv)
        Body_rv = findViewById(R.id.body_home_rv)
        Hair_rv = findViewById(R.id.hair_home_rv)
        Hair = findViewById(R.id.hair)
        Body = findViewById(R.id.body)
        Face = findViewById(R.id.face)
        Trending = findViewById(R.id.mp_btn)
        Trending.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@Home,
                    trending::class.java
                )
            )
        })
        Logout.setOnClickListener(View.OnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            startActivity(Intent(this@Home, profile::class.java))
        })
        Logout_txt.setOnClickListener(View.OnClickListener { v ->
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            val builder = AlertDialog.Builder(this@Home, R.style.CustomAlertDialog)
            val viewGroup = findViewById<ViewGroup>(android.R.id.content)
            val dialogView =
                LayoutInflater.from(v.context).inflate(R.layout.jasjfs, viewGroup, false)
            val buttonOk = dialogView.findViewById<Button>(R.id.buttonyes)
            builder.setView(dialogView)
            val alertDialog = builder.create()
            buttonOk.setOnClickListener {
                val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
                mediaPlayer.start()
                finishAffinity()
                auth!!.signOut()
                Toast.makeText(this@Home, "Logout successfull restart  the app", Toast.LENGTH_SHORT)
                    .show()
            }
            dialogView.findViewById<View>(R.id.buttoncancle).setOnClickListener {
                val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
                mediaPlayer.start()
                alertDialog.dismiss()
            }
            alertDialog.show()
        })
        Handler().postDelayed({ Load.setVisibility(View.GONE) }, 2000)
        Fragrance = findViewById(R.id.fragrance)
        Search_main.setOnClickListener(View.OnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            ALL!!.setTypeface(Typeface.DEFAULT_BOLD)
            Body.setTypeface(Typeface.DEFAULT)
            Hair.setTypeface(Typeface.DEFAULT)
            Fragrance.setTypeface(Typeface.DEFAULT)
            Face.setTypeface(Typeface.DEFAULT)
            Frag_rv.setVisibility(View.GONE)
            Face_rv.setVisibility(View.GONE)
            Body_rv.setVisibility(View.GONE)
            Hair_rv.setVisibility(View.GONE)
            Product_home!!.visibility = View.VISIBLE
            Search.setVisibility(View.VISIBLE)
            Hair.setTextColor(Color.parseColor("#FF000000"))
            ALL!!.setTextColor(Color.parseColor("#FBC02D"))
            Body.setTextColor(Color.parseColor("#FF000000"))
            Fragrance.setTextColor(Color.parseColor("#FF000000"))
            Face.setTextColor(Color.parseColor("#FF000000"))
        })
        Search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                ALL!!.setTypeface(Typeface.DEFAULT_BOLD)
                Body.setTypeface(Typeface.DEFAULT)
                Hair.setTypeface(Typeface.DEFAULT)
                Fragrance.setTypeface(Typeface.DEFAULT)
                Face.setTypeface(Typeface.DEFAULT)
                Frag_rv.setVisibility(View.GONE)
                Face_rv.setVisibility(View.GONE)
                Body_rv.setVisibility(View.GONE)
                Hair_rv.setVisibility(View.GONE)
                Product_home!!.visibility = View.VISIBLE
                Hair.setTextColor(Color.parseColor("#FF000000"))
                ALL!!.setTextColor(Color.parseColor("#FBC02D"))
                Body.setTextColor(Color.parseColor("#FF000000"))
                Fragrance.setTextColor(Color.parseColor("#FF000000"))
                Face.setTextColor(Color.parseColor("#FF000000"))
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                if (product_adapter == null) {
                    Nodata.setVisibility(View.VISIBLE)
                } else {
                    Nodata.setVisibility(View.GONE)
                }
            }
        })
        refresh.setOnRefreshListener(OnRefreshListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            Handler().postDelayed({
                recreate()
                product_adapter!!.notifyDataSetChanged()
                refresh.setRefreshing(false)
                Load.setVisibility(View.GONE)
            }, 2000)
        })
        Product_home = findViewById(R.id.all_home_rv)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        Product_home.setLayoutManager(layoutManager)
        supportActionBar!!.hide()
        databaseReference = FirebaseDatabase.getInstance().getReference("Fragrance")
        Product_home.setHasFixedSize(true)
        products = ArrayList()
        ALL = findViewById(R.id.All_tv)
        ALL.setTextColor(Color.parseColor("#FBC02D"))
        product_adapter = Product_adapter(this, products!!)
        Product_home.setAdapter(product_adapter)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val product_gs = dataSnapshot.getValue(Product_gs::class.java)
                    products!!.add(product_gs)
                }
                product_adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        val FraglayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        Frag_rv.setLayoutManager(FraglayoutManager)
        supportActionBar!!.hide()
        databaseReference = FirebaseDatabase.getInstance().getReference("Fra")
        Frag_rv.setHasFixedSize(true)
        Frag_gs = ArrayList()
        fragadapter = Fragadapter(this, Frag_gs!!)
        Frag_rv.setAdapter(fragadapter)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val frag_gs = dataSnapshot.getValue(Frag_Gs::class.java)
                    Frag_gs!!.add(frag_gs)
                }
                body_adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        val BodylayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        Body_rv.setLayoutManager(BodylayoutManager)
        supportActionBar!!.hide()
        databaseReference = FirebaseDatabase.getInstance().getReference("Body")
        Body_rv.setHasFixedSize(true)
        Body_gs = ArrayList()
        body_adapter = Body_adapter(this, Body_gs!!)
        Body_rv.setAdapter(body_adapter)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val body_gs = dataSnapshot.getValue(Body_Gs::class.java)
                    Body_gs!!.add(body_gs)
                }
                body_adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        val FacelayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        Face_rv.setLayoutManager(FacelayoutManager)
        supportActionBar!!.hide()
        databaseReference = FirebaseDatabase.getInstance().getReference("Face")
        Face_rv.setHasFixedSize(true)
        Face_gs = ArrayList()
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val face_gs = dataSnapshot.getValue(Face_Gs::class.java)
                    Face_gs!!.add(face_gs)
                }
                face_adapter = Face_adapter(this@Home, Face_gs!!)
                Face_rv.setAdapter(face_adapter)

                face_adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        val hairlayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        Hair_rv.setLayoutManager(hairlayoutManager)
        supportActionBar!!.hide()
        databaseReference = FirebaseDatabase.getInstance().getReference("Hair")
        Hair_rv.setHasFixedSize(true)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    if(dataSnapshot.exists()) {

                        val hair_gs = dataSnapshot.getValue(Hair_Gs!!::class.java)
                        Hair_Gs!!.add(hair_gs)
                    }
                }
                hair_adapter = Hair_adapter(this@Home, Hair_Gs!!)
                Hair_rv.setAdapter(hair_adapter)

                hair_adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        ALL.setOnClickListener(View.OnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            ALL.setTypeface(Typeface.DEFAULT_BOLD)
            Body.setTypeface(Typeface.DEFAULT)
            Hair.setTypeface(Typeface.DEFAULT)
            Fragrance.setTypeface(Typeface.DEFAULT)
            Face.setTypeface(Typeface.DEFAULT)
            Frag_rv.setVisibility(View.GONE)
            Face_rv.setVisibility(View.GONE)
            Body_rv.setVisibility(View.GONE)
            Hair_rv.setVisibility(View.GONE)
            Product_home.setVisibility(View.VISIBLE)
            Hair.setTextColor(Color.parseColor("#FF000000"))
            ALL.setTextColor(Color.parseColor("#FBC02D"))
            Body.setTextColor(Color.parseColor("#FF000000"))
            Fragrance.setTextColor(Color.parseColor("#FF000000"))
            Face.setTextColor(Color.parseColor("#FF000000"))
        })
        Hair.setOnClickListener(View.OnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            ALL.setTypeface(Typeface.DEFAULT)
            Body.setTypeface(Typeface.DEFAULT)
            Hair.setTypeface(Typeface.DEFAULT_BOLD)
            Fragrance.setTypeface(Typeface.DEFAULT)
            Face.setTypeface(Typeface.DEFAULT)
            Hair.setTextColor(Color.parseColor("#FBC02D"))
            ALL.setTextColor(Color.parseColor("#FF000000"))
            Body.setTextColor(Color.parseColor("#FF000000"))
            Fragrance.setTextColor(Color.parseColor("#FF000000"))
            Face.setTextColor(Color.parseColor("#FF000000"))
            Frag_rv.setVisibility(View.GONE)
            Body_rv.setVisibility(View.GONE)
            Hair_rv.setVisibility(View.VISIBLE)
            Product_home.setVisibility(View.GONE)
            Face_rv.setVisibility(View.GONE)
        })
        Body.setOnClickListener(View.OnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            ALL.setTypeface(Typeface.DEFAULT)
            Body.setTypeface(Typeface.DEFAULT_BOLD)
            Hair.setTypeface(Typeface.DEFAULT)
            Fragrance.setTypeface(Typeface.DEFAULT)
            Face.setTypeface(Typeface.DEFAULT)
            ALL.setTextColor(Color.parseColor("#FF000000"))
            Body.setTextColor(Color.parseColor("#FBC02D"))
            Hair.setTextColor(Color.parseColor("#FF000000"))
            Fragrance.setTextColor(Color.parseColor("#FF000000"))
            Face.setTextColor(Color.parseColor("#FF000000"))
            Frag_rv.setVisibility(View.GONE)
            Body_rv.setVisibility(View.VISIBLE)
            Hair_rv.setVisibility(View.GONE)
            Product_home.setVisibility(View.GONE)
            Face_rv.setVisibility(View.GONE)
        })
        Face.setOnClickListener(View.OnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            ALL.setTypeface(Typeface.DEFAULT)
            Body.setTypeface(Typeface.DEFAULT)
            Hair.setTypeface(Typeface.DEFAULT)
            Fragrance.setTypeface(Typeface.DEFAULT)
            Face.setTypeface(Typeface.DEFAULT_BOLD)
            Face.setTextColor(Color.parseColor("#FBC02D"))
            Body.setTextColor(Color.parseColor("#FF000000"))
            Fragrance.setTextColor(Color.parseColor("#FF000000"))
            Hair.setTextColor(Color.parseColor("#FF000000"))
            Face_rv.setVisibility(View.VISIBLE)
            Frag_rv.setVisibility(View.GONE)
            Body_rv.setVisibility(View.GONE)
            Hair_rv.setVisibility(View.GONE)
            Product_home.setVisibility(View.GONE)
        })
        Fragrance.setOnClickListener(View.OnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            ALL.setTypeface(Typeface.DEFAULT)
            Body.setTypeface(Typeface.DEFAULT)
            Hair.setTypeface(Typeface.DEFAULT)
            Fragrance.setTypeface(Typeface.DEFAULT_BOLD)
            Face.setTypeface(Typeface.DEFAULT)
            Frag_rv.setVisibility(View.VISIBLE)
            Product_home.setVisibility(View.GONE)
            Body_rv.setVisibility(View.GONE)
            Hair_rv.setVisibility(View.GONE)
            Face_rv.setVisibility(View.GONE)
            ALL.setTextColor(Color.parseColor("#FF000000"))
            Fragrance.setTextColor(Color.parseColor("#FBC02D"))
            Body.setTextColor(Color.parseColor("#FF000000"))
            Hair.setTextColor(Color.parseColor("#FF000000"))
            Face.setTextColor(Color.parseColor("#FF000000"))
        })
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this@Home, R.style.CustomAlertDialog)
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView =
            LayoutInflater.from(this@Home).inflate(R.layout.exit_dailog, viewGroup, false)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        dialogView.findViewById<View>(R.id.exit_no).setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            alertDialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.exit_yes).setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Home, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            finishAffinity()
        }
        alertDialog.show()
    }

    private fun filter(text: String) {
        val filterlist = ArrayList<Product_gs?>()
        var product_adapter: Product_adapter? = null

        var products: ArrayList<Product_gs?>? = null

        for (item in products!!) {
            if (item?.name?.toLowerCase(Locale.ROOT)!!.contains(text.toLowerCase(Locale.ROOT))) {
                filterlist.add(item)
            }
            product_adapter!!.filterlist(filterlist)
        }
    }

    override fun onPause() {
        super.onPause()
    }
}