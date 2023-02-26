package com.example.t_shopping

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class otp_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp1)
        supportActionBar!!.hide()
        var getotp: TextView? = null
        var enterotp: EditText? = null

        val permissions = arrayOf("android.permission.RECEIVE_SMS")
        getotp = findViewById(R.id.sentotp)
        enterotp = findViewById(R.id.otp_enter)
        val otpprogress = findViewById<ProgressBar>(R.id.progressotp)
        val sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        getotp.setOnClickListener(View.OnClickListener {
            val mediaPlayer = MediaPlayer.create(this@otp_1, R.raw.mixkitarcadegamejumpcoin)
            mediaPlayer.start()
            if (enterotp.getText().toString().trim { it <= ' ' }.isEmpty()) {
                enterotp.setFocusable(true)
                enterotp.setError("Enter  Phone number")
            } else if (enterotp.getText().toString().trim { it <= ' ' }.length > 10) {
                enterotp.setFocusable(true)
                enterotp.setError("Enter Valid Phone number")
            } else if (enterotp.getText().toString().trim { it <= ' ' }.length < 10) {
                enterotp.setFocusable(true)
                enterotp.setError("Enter Valid Phone number")
            } else {
                otpprogress.visibility = View.VISIBLE
                getotp.setVisibility(View.INVISIBLE)
                PhoneAuthProvider.getInstance()
                    .verifyPhoneNumber("+91" + enterotp.getText().toString().trim { it <= ' ' },
                        60,
                        TimeUnit.SECONDS,
                        this@otp_1,
                        object : OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                                otpprogress.visibility = View.GONE
                                getotp.setVisibility(View.VISIBLE)
                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                otpprogress.visibility = View.GONE
                                getotp.setVisibility(View.VISIBLE)
                                Toast.makeText(this@otp_1, e.message, Toast.LENGTH_SHORT).show()
                            }

                            override fun onCodeSent(
                                VerificationId: String,
                                forceResendingToken: ForceResendingToken
                            ) {
                                otpprogress.visibility = View.GONE
                                getotp.setVisibility(View.VISIBLE)
                                val editor = sharedPreferences.edit()
                                editor.putString(
                                    KEY_PHONE,
                                    enterotp.getText().toString().trim { it <= ' ' })
                                editor.apply()
                                val intent = Intent(applicationContext, otp_2::class.java)
                                intent.putExtra("mobile", enterotp.getText().toString())
                                intent.putExtra("verificationId", VerificationId)
                                startActivity(intent)
                            }
                        })
            }
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 80)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 80) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this@otp_1, "Allow Permission from settings", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        private const val SHARED_PREF_NAME = "mypref"
        private const val KEY_PHONE = "phone"
    }
}