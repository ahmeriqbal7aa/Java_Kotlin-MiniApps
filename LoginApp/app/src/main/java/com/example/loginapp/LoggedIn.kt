package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_logged_in.*

class LoggedIn : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        // if SharedPref exist then return "Email" otherwise return "1"
        val isAnyLogin = sharedPref.getString("Email", "1")

        if (isAnyLogin == "1") {
            val email = intent.getStringExtra("email")
            if (email != null) {
                setText(email)
                // set preferences
                with(sharedPref.edit()) {
                    putString("Email", email)
//                 commit() // synchronous method which block the Main UI Thread
                    apply() // asynchronous method and
                }
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            setText(isAnyLogin)
        }

        logout.setOnClickListener {
            sharedPref.edit().remove("Email").apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setText(email: String?) {
        db = FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("LoginAppUsers").document(email).get()
                .addOnSuccessListener {
                    name.text = it.get("Name").toString()
                    phone.text = it.get("Phone").toString()
                    emailLog.text = it.get("Email").toString()
                }
        }
    }
}