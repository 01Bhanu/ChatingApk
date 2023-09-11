package com.example.chatingapk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var edtName: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtName = findViewById(R.id.edt_name)
        btnSignUp = findViewById(R.id.SignUpbtn)

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signUp( name,email , password)
        }

    }

    private fun signUp(name:String,email:String,password:String){
        // logic for creating user

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    adduserToDatabase(name,email,mAuth.currentUser?.uid!!)
                   val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "some error occurred", Toast.LENGTH_SHORT).show()

                }
            }



    }
    private fun adduserToDatabase (name: String,email:String,uid:String){

            mDbRef = FirebaseDatabase.getInstance().getReference()

            mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}