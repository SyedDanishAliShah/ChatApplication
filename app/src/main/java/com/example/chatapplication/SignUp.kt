package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chatapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edtName : EditText
    private lateinit var edtEmail : EditText //will be later initializing it in the onCreate function
    private lateinit var edtPassword : EditText
    private lateinit var btnSignUp : Button
    private lateinit var mAuth : FirebaseAuth//firebase authentication
    private lateinit var mDbRef: DatabaseReference//to add users to the firebase real time database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()//Means in this activity we will have no action bar
        mAuth = FirebaseAuth.getInstance()
        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)//variable/view has been initialized
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignup)

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signUp(name,email,password)//calling the function
        }
    }
    private fun signUp(name : String,email:String, password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                      //code for jumping to the home activity
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()//this will finish the previous running activity
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()

                }
            }

    }//function made to write the logic for creating user.
    private fun addUserToDatabase(name : String, email:String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()//reference of the database
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))//child function will add a node to the database.First we will pass user which is the parent node inside which we can have multiple users.and by passing uid it will create unique uid for every user.like this it will add a user to the database.

    }//function made to add user to the database as we create it.
}