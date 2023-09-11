package com.example.chatingapk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth as FirebaseAuth1

class LogIn : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth = FirebaseAuth1.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.loginbtn)
       btnSignUp = findViewById(R.id.SignUpbtn)


       btnSignUp.setOnClickListener{
           val intent = Intent(this, SignUp::class.java)
           startActivity(intent)
       }

        btnLogin.setOnClickListener {
            val  email =edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email,password)
        }

    }

    private fun login(email:String,password:String){
        // logic for creating user

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {

                    Toast.makeText(this@LogIn,"user dose not exist",Toast.LENGTH_SHORT).show()

                }
            }

    }

}