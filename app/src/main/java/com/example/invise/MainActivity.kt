package com.example.invise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.invise.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

lateinit var binding: ActivityMainBinding
lateinit var auth: FirebaseAuth
lateinit var databaseReference: DatabaseReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn.setOnClickListener {
            val et = binding.editTextName.text.toString()
            if(et == "") {
                Toast.makeText(this, "Вы не ввели имя", Toast.LENGTH_SHORT).show()
            }
            else{
                database()
                startActivity(Intent(this, HomeActivity::class.java))
            }
            }
        }

    }
    fun database(){
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }
}