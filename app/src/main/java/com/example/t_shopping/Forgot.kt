package com.example.t_shopping

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Forgot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        var Done: ImageView = findViewById(R.id.Forgot_done)
        var Password: EditText? = null
        var ConfirmPass: EditText? = null
        Password = findViewById(R.id.forgot_password)
        supportActionBar!!.hide()
        ConfirmPass = findViewById(R.id.forgot_confirmpassword)
        Done.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (Password.getText().toString().isEmpty()) {
                    Password.setError("This field is empty")
                } else if (ConfirmPass.getText().toString().isEmpty()) {
                    ConfirmPass.setError("This field is empty")
                } else if (!((Password.getText().toString() == ConfirmPass.getText().toString()))) {
                    ConfirmPass.error = "Password does't match"
                } else {
                    val intent: Intent = Intent(this@Forgot, Login::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}