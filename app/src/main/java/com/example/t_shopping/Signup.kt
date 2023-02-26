package com.example.t_shopping

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Signup constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        getSupportActionBar()!!.hide()
        var Login: TextView? = null
        var Signup_done: ImageView? = null
        var Name: EditText? = null
        var Email: EditText? = null
        var Password: EditText? = null
        var fAuth: FirebaseAuth? = null
        var fstore: FirebaseFirestore? = null
        var userID: String? = null
        var sp: SharedPreferences? = null
        Login = findViewById(R.id.Login)
        Signup_done = findViewById(R.id.Signup_done)
        sp = getSharedPreferences("Userinfo", MODE_PRIVATE)
        Name = findViewById(R.id.signup_name)
        Email = findViewById(R.id.signup_email)
        Password = findViewById(R.id.signup_pass)
        fstore = FirebaseFirestore.getInstance()
        fAuth = FirebaseAuth.getInstance()
        Login.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(view: View) {
                val mediaPlayer: MediaPlayer =
                    MediaPlayer.create(this@Signup, R.raw.mixkitarcadegamejumpcoin)
                mediaPlayer.start()
                startActivity(Intent(this@Signup, com.example.t_shopping.Login::class.java))
            }
        })
        Signup_done.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(view: View) {
                val mediaPlayer: MediaPlayer =
                    MediaPlayer.create(this@Signup, R.raw.mixkitarcadegamejumpcoin)
                mediaPlayer.start()
                val fname: String = Name.getText().toString().trim({ it <= ' ' })
                val semail: String = Email.getText().toString().trim({ it <= ' ' })
                val spass: String = Password.getText().toString().trim({ it <= ' ' })
                if (Name.getText().toString().trim({ it <= ' ' }).isEmpty()) {
                    Name.setError("This field is require")
                } else if (Email.getText().toString().trim({ it <= ' ' }).isEmpty()) {
                    Email.setError("This field is require")
                } else if (!Patterns.EMAIL_ADDRESS.matcher(
                        Email.getText().toString().trim({ it <= ' ' })
                    ).matches()
                ) {
                    Email.setError("Invalid Email")
                } else if (Password.getText().toString().trim({ it <= ' ' }).isEmpty()) {
                    Password.setError("This field is require")
                } else {
                    val editor: SharedPreferences.Editor = sp.edit()
                    editor.putString("name", Name.getText().toString().trim({ it <= ' ' })).apply()
                    val progressDialog: ProgressDialog = ProgressDialog(this@Signup)
                    progressDialog.setTitle("Sign Up")
                    progressDialog.setMessage("Please wait loading")
                    progressDialog.show()
                    fAuth!!.createUserWithEmailAndPassword(
                        Email.getText().toString().trim({ it <= ' ' }),
                        Password.getText().toString().trim({ it <= ' ' })
                    ).addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
                        public override fun onComplete(task: Task<AuthResult?>) {
                            if (task.isSuccessful()) {
                                Toast.makeText(
                                    this@Signup,
                                    "Sign UP successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                userID = fAuth!!.getCurrentUser()!!.getUid()
                                val documentReference: DocumentReference =
                                    fstore!!.collection("users").document(
                                        userID!!
                                    )
                                val user: MutableMap<String, Any> = HashMap()
                                user.put("email", semail)
                                user.put("fname", fname)
                                user.put("password", spass)
                                documentReference.set(user)
                                    .addOnSuccessListener(object : OnSuccessListener<Void?> {
                                        public override fun onSuccess(aVoid: Void?) {
                                            progressDialog.dismiss()
                                            startActivity(
                                                Intent(
                                                    this@Signup,
                                                    com.example.t_shopping.Login::class.java
                                                )
                                            )
                                        }
                                    })
                            } else {
                                Toast.makeText(
                                    this@Signup,
                                    "Error!" + task.getException()!!.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }
        })
    }
}