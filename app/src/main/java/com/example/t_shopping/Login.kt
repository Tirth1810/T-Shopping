package com.example.t_shopping

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    val fAuth = FirebaseAuth.getInstance()
    private val mGoogleSignInClient: GoogleSignInClient? = null
    override fun onStart() {
        super.onStart()
        val user = fAuth!!.currentUser
        if (user != null) {
            startActivity(Intent(this@Login, Home::class.java))
        }
    }

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val connectivityManager =
            applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected || !networkInfo.isAvailable) {
            val builder = AlertDialog.Builder(this@Login, R.style.CustomAlertDialog)
            val viewGroup = findViewById<ViewGroup>(android.R.id.content)
            builder.setCancelable(false)
            val dialogView =
                LayoutInflater.from(this@Login).inflate(R.layout.no_internet, viewGroup, false)
            builder.setView(dialogView)
            val alertDialog = builder.create()
            dialogView.findViewById<View>(R.id.try_again).setOnClickListener {
                val mediaPlayer = MediaPlayer.create(this@Login, R.raw.mixkitarcadegamejumpcoin)
                mediaPlayer.start()
                recreate()
            }
            alertDialog.show()
        }
        supportActionBar!!.hide()
        val Name: TextView = findViewById(R.id.login_name)
        val fstore = FirebaseFirestore.getInstance()
        val sp: SharedPreferences = getSharedPreferences("Userinfo", MODE_PRIVATE)
        val Signup: TextView = findViewById(R.id.signup)
        val Email: EditText = findViewById(R.id.login_email)
        val Password: EditText = findViewById(R.id.login_pass)
        if (fAuth!!.currentUser != null) {
            startActivity(Intent(this@Login, Home::class.java))
        }
        val Login_done = findViewById<Button>(R.id.Login_done)
        Signup.setOnClickListener(View.OnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Login, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            startActivity(Intent(this@Login, Signup::class.java))
        })
        Login_done.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Login, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            val lemail = Email.getText().toString().trim { it <= ' ' }
            if (Name.getText().toString().isEmpty()) {
                Name.setError("This filed is required")
            } else if (Email.getText().toString().trim { it <= ' ' }.isEmpty()) {
                Email.setError("This field is require")
            } else if (!Patterns.EMAIL_ADDRESS.matcher(
                    Email.getText().toString().trim { it <= ' ' }).matches()
            ) {
                Email.setError("Invalid Email")
            } else if (Password.getText().toString().trim { it <= ' ' }.isEmpty()) {
                Password.setError("This field is require")
            } else {
                val editor = sp.edit()
                editor.putString("name", Name.getText().toString().trim { it <= ' ' }).apply()
                editor.putString("email", Email.getText().toString().trim { it <= ' ' }).apply()
                val progressDialog = ProgressDialog(this@Login)
                progressDialog.setTitle("Sign Up")
                progressDialog.setMessage("Please wait loading")
                progressDialog.show()
                fAuth!!.signInWithEmailAndPassword(
                    Email.getText().toString().trim { it <= ' ' },
                    Password.getText().toString().trim { it <= ' ' })
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            progressDialog.dismiss()
                            Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT)
                                .show()
                            val userID = fAuth!!.currentUser!!.uid
                            val documentReference = fstore!!.collection("users").document(
                                userID!!
                            )
                            val user: MutableMap<String, Any> = HashMap()
                            user["email"] = lemail
                            documentReference.set(user).addOnSuccessListener {
                                Toast.makeText(
                                    this@Login,
                                    "complete",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            startActivity(Intent(this@Login, Home::class.java))
                        } else {
                            Toast.makeText(
                                this@Login,
                                "Error!" + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this@Login, R.style.CustomAlertDialog)
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView =
            LayoutInflater.from(this@Login).inflate(R.layout.exit_dailog, viewGroup, false)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        dialogView.findViewById<View>(R.id.exit_no).setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Login, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            alertDialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.exit_yes).setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this@Login, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            finishAffinity()
        }
        alertDialog.show()
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }
}