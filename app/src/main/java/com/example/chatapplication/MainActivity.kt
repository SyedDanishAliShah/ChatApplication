package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList : ArrayList<User>//to be used in the recycler view
    private lateinit var adapter: UserAdapter//provides data to the recycler view
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()//by initializing we get the reference to the database

        userList = ArrayList()
        adapter = UserAdapter(this, userList)//as this provides the userList to the recycler view
        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)//as we will be arranging the users linearly
        userRecyclerView.adapter = adapter

        mDbRef.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()//before running the for loop we will clear the previous list.
                for (postSnapshot in snapshot.children) {

                    val currentUser = postSnapshot.getValue(User::class.java)//like this we get one user.

                    if (mAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)//adding this current user to the user list.
                    }//uid of the current user does not match with the uid of the current logged in user.This means the user which has been logged in will not show its name in the list.
                }//going inside the child nodes of the data base schema. As snapshot denotes the schema of the database.
                adapter.notifyDataSetChanged()//letting know the adapter about the data change.
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })//this line of code sets up a listener for changes to the user node in firebase data base.
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)//we have inflated the menu layout
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            //write the logic for logout
            mAuth.signOut()
            val intent = Intent(this@MainActivity, LogIn::class.java)
            finish()
            startActivity(intent)
            finish()

            return true
        }//means if the logout is clicked
        return true

    }//function for setting the onclick listener to the menu
}