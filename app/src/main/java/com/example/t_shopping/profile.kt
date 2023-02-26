package com.example.t_shopping

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class profile constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        getSupportActionBar()!!.hide()
        var Name: TextView? = null
        var Email: TextView? = null
        var sp: SharedPreferences? = null
        var Logout: AppCompatButton? = null
        var profilepic: CircleImageView? = null
        var auth: FirebaseAuth? = null

        Name = findViewById(R.id.profile_name)
        Email = findViewById(R.id.profile_email)
        auth = FirebaseAuth.getInstance()
        sp = getApplicationContext().getSharedPreferences("Userinfo", MODE_PRIVATE)
        val pName: String? = sp.getString("name", "")
        val pEmail: String? = sp.getString("email", "")
        Email.setText(pEmail)
        Name.setText(pName)
        val googleSignInAccount: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (googleSignInAccount != null) {
            Name.setText(googleSignInAccount.getDisplayName())
            Email.setText(googleSignInAccount.getEmail())
        }
        Logout = findViewById(R.id.profile_lg)
        Logout.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                val mediaPlayer: MediaPlayer =
                    MediaPlayer.create(this@profile, R.raw.mixkitarcadegamejumpcoin)
                mediaPlayer.start()
                val builder: AlertDialog.Builder =
                    AlertDialog.Builder(this@profile, R.style.CustomAlertDialog)
                val viewGroup: ViewGroup = findViewById(android.R.id.content)
                val dialogView: View =
                    LayoutInflater.from(v.getContext()).inflate(R.layout.jasjfs, viewGroup, false)
                val buttonOk: Button = dialogView.findViewById(R.id.buttonyes)
                builder.setView(dialogView)
                val alertDialog: AlertDialog = builder.create()
                buttonOk.setOnClickListener(object : View.OnClickListener {
                    public override fun onClick(v: View) {
                        val mediaPlayer: MediaPlayer =
                            MediaPlayer.create(this@profile, R.raw.mixkitarcadegamejumpcoin)
                        mediaPlayer.start()
                        finishAffinity()
                        auth!!.signOut()
                        Toast.makeText(
                            this@profile,
                            "Logout successfull restart  the app",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                dialogView.findViewById<View>(R.id.buttoncancle)
                    .setOnClickListener(object : View.OnClickListener {
                        public override fun onClick(v: View) {
                            val mediaPlayer: MediaPlayer =
                                MediaPlayer.create(this@profile, R.raw.mixkitarcadegamejumpcoin)
                            mediaPlayer.start()
                            alertDialog.dismiss()
                        }
                    })
                alertDialog.show()
            }
        })
    }
}