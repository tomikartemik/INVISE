package com.example.invise

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.invise.databinding.ActivityMainBinding
import com.google.firebase.database.*


lateinit var binding: ActivityMainBinding
lateinit var databaseReference: DatabaseReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn.setOnClickListener {
            val et = binding.editTextName.text.toString()
            if(et == "") {
                Toast.makeText(this , "Вы не ввели имя" , Toast.LENGTH_SHORT).show()
            }
            else{
                //database(et)
                getId(et)
                startActivity(Intent(this , HomeActivity::class.java))
            }
            }
        }

    }
    fun database(name : String){
        val ref = FirebaseDatabase.getInstance().reference.child("Users").child(name).push()
        ref.child("Id").setValue(0)
    }
/////////////Получение Id последнего добавленного пользователя и добавление нового/////////////////////////////////////////////////////////////////////////
    fun getId(name : String){
        val databaseReference = FirebaseDatabase.getInstance().reference
        val lastQuery = databaseReference.child("Users").orderByKey()
        lastQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot : DataSnapshot) {
                val message = dataSnapshot.value.toString()
                val ide = message.indexOf("Id=", 0, false)+3
                val id = message.indexOf("}", ide, false)
                val tid = message.substring(ide, id).toInt()
                Log.d("String", message)
                Log.d("Ide", ide.toString())
                Log.d("Id", id.toString())
                Log.d("Substring", tid.toString())
                val ref = FirebaseDatabase.getInstance().reference.child("Users").child(name).push()
                ref.child("Id").setValue(tid+1)
            }
            override fun onCancelled(databaseError : DatabaseError) {
                // Handle possible errors.
            }
        })
    }