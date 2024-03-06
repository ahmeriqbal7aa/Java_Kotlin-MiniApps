package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()
        // Initialize FirebaseFirestore
        db = FirebaseFirestore.getInstance()

        btnContinue.setOnClickListener {
            if (checking()) {
                val email = EmailRegister.text.toString()
                var password = PasswordRegister.text.toString()
                val name = Name.text.toString()
                val phone = Phone.text.toString()
                // TODO Map
                val userMap = hashMapOf(
                    "Name" to name,
                    "Phone" to phone,
                    "Email" to email
                )
                // TODO Firebase Collection
                val usersCollection = db.collection("LoginAppUsers")
                val checkUser = usersCollection.whereEqualTo("Email", email).get()
                    .addOnSuccessListener { task ->
                        if (task.isEmpty) {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) {
                                    if (it.isSuccessful) {
                                        usersCollection.document(email).set(userMap)
                                        val intent = Intent(this, LoggedIn::class.java)
                                        intent.putExtra("email", email)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Authentication Failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(this, "User Already Registered", Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
            } else {
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }
        }

    }

    // TODO Checking Method
    private fun checking(): Boolean {
        if (Name.text.toString().trim { it <= ' ' }.isNotEmpty()
            && Phone.text.toString().trim { it <= ' ' }.isNotEmpty()
            && EmailRegister.text.toString().trim { it <= ' ' }.isNotEmpty()
            && PasswordRegister.text.toString().trim { it <= ' ' }.isNotEmpty()
        ) {
            return true
        }
        return false
    }
}