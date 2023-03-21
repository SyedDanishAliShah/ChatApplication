package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class LogIn : AppCompatActivity() {

    private lateinit var edtEmail : EditText //will be later initializing it in the onCreate function
    private lateinit var edtPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp : Button
    private lateinit var mAuth : FirebaseAuth//firebase authentication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()//Means in this activity we will have no action bar

        mAuth = FirebaseAuth.getInstance()//Initializing the firebase authentication

        edtEmail = findViewById(R.id.edt_email)//variable/view has been initialized
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignup)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)//will jump from this activity to the signup activity
            finish()
            startActivity(intent)//function that will make the intent work
        }
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()//getting the email from the user
            val password = edtPassword.text.toString()

            login(email,password)//calling the function
        }
 }
    private fun login(email:String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    startActivity(intent)
                  //code for logging in user
                } else {
                  Toast.makeText(this@LogIn, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }

    }//function made for writing the logic for logging user
}