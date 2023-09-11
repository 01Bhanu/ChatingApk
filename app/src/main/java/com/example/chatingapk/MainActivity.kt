package com.example.chatingapk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mAuth = FirebaseAuth.getInstance()
        mDbRef= FirebaseDatabase.getInstance().getReference()

        // Initialize RecyclerView and adapter
        userRecyclerView = findViewById(R.id.userRecyclerView)
        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        // Set adapter to RecyclerView
        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.adapter = adapter
        userRecyclerView.layoutManager = LinearLayoutManager(this)


        mDbRef.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for(postSnapshot in snapshot.children){
                    val currenUser = postSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid!=currenUser?.uid) {
                        userList.add(currenUser!!)
                    }
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }
        } )


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity,LogIn::class.java)
            finish()
            startActivity(intent)

            return true
        }
        return true
    }
}




