package com.example.t_shopping

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class otp_2() : AppCompatActivity() {
    var verify: TextView? = null
    var Resend: TextView? = null
    var progressBar: ProgressBar? = null
    var otp1: EditText? = null
    var otp2: EditText? = null
    var otp3: EditText? = null
    var otp4: EditText? = null
    var otp5: EditText? = null
    var otp6: EditText? = null
    private var verificationId: String? = null
    var fstore: FirebaseFirestore? = null
    var userID: String? = null
    var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp2)
        supportActionBar!!.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        verificationId = intent.getStringExtra("verificationId")
        val phonenum = findViewById<TextView>(R.id.phone_num)
        fstore = FirebaseFirestore.getInstance()
        phonenum.text = intent.getStringExtra("mobile")
        init()
        onclicklistener()
        setupOTPInputs()
    }

    fun init() {
        Resend = findViewById(R.id.resend_otp)
        otp1 = findViewById(R.id.inputcode1)
        otp2 = findViewById(R.id.inputcode2)
        otp3 = findViewById(R.id.inputcode3)
        otp4 = findViewById(R.id.inputcode4)
        otp5 = findViewById(R.id.inputcode5)
        otp6 = findViewById(R.id.inputcode6)
        verify = findViewById(R.id.verifyotp)
        progressBar = findViewById(R.id.verifyprogress)
    }

    fun onclicklistener() {
        verify!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val mediaPlayer = MediaPlayer.create(this@otp_2, R.raw.mixkitarcadegamejumpcoin)
                mediaPlayer.start()
                val code = (otp1!!.text.toString() +
                        otp2!!.text.toString() +
                        otp3!!.text.toString() +
                        otp4!!.text.toString() +
                        otp5!!.text.toString() +
                        otp6!!.text.toString())
                if (verificationId != null) {
                    if ((otp1!!.text.toString().trim { it <= ' ' }.isEmpty() ||
                                otp2!!.text.toString().trim { it <= ' ' }.isEmpty() ||
                                otp3!!.text.toString().trim { it <= ' ' }.isEmpty() ||
                                otp4!!.text.toString().trim { it <= ' ' }.isEmpty() ||
                                otp5!!.text.toString().trim { it <= ' ' }.isEmpty() ||
                                otp6!!.text.toString().trim { it <= ' ' }.isEmpty())
                    ) {
                        Toast.makeText(this@otp_2, "Please enter valid code", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        progressBar!!.visibility = View.VISIBLE
                        verify!!.visibility = View.GONE
                        val phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId!!, code
                        )
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
                                override fun onComplete(task: Task<AuthResult?>) {
                                    val phonenum = findViewById<TextView>(R.id.phone_num)
                                    phonenum.text = intent.getStringExtra("mobile")
                                    progressBar!!.visibility = View.GONE
                                    verify!!.visibility = View.VISIBLE
                                    if (task.isSuccessful) {
                                        userID = firebaseAuth!!.currentUser!!.uid
                                        val documentReference =
                                            fstore!!.collection("users").document(
                                                userID!!
                                            )
                                        val user: MutableMap<String, Any> = HashMap()
                                        user["phoneno"] =
                                            phonenum.text.toString().trim { it <= ' ' }
                                        documentReference.set(user).addOnSuccessListener(
                                            OnSuccessListener {
                                                Toast.makeText(
                                                    this@otp_2,
                                                    "complete",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                startActivity(
                                                    Intent(
                                                        this@otp_2,
                                                        Forgot::class.java
                                                    )
                                                )
                                            })
                                    } else {
                                        Toast.makeText(
                                            this@otp_2,
                                            "Please enter valid code",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            })
                    }
                } else {
                    Toast.makeText(this@otp_2, "Verified", Toast.LENGTH_SHORT).show()
                }
            }
        })
        Resend!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                PhoneAuthProvider.getInstance()
                    .verifyPhoneNumber("+91" + intent.getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        this@otp_2,
                        object : OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {}
                            override fun onVerificationFailed(e: FirebaseException) {
                                Toast.makeText(this@otp_2, e.message, Toast.LENGTH_SHORT).show()
                            }

                            override fun onCodeSent(
                                newverificationId: String,
                                forceResendingToken: ForceResendingToken
                            ) {
                                verificationId = newverificationId
                                Toast.makeText(this@otp_2, "OTP Sent", Toast.LENGTH_SHORT).show()
                            }
                        })
            }
        })
    }

    private fun setupOTPInputs() {
        otp1!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim { it <= ' ' }.isEmpty()) {
                    otp2!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        otp2!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim { it <= ' ' }.isEmpty()) {
                    otp3!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        otp3!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim { it <= ' ' }.isEmpty()) {
                    otp4!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        otp4!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim { it <= ' ' }.isEmpty()) {
                    otp5!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        otp5!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!(s.toString().trim { it <= ' ' }.isEmpty())) {
                    otp6!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    companion object {
        private val SHARED_PREF_NAME = "mypref"
        private val KEY_PHONE = "phone"
    }
}