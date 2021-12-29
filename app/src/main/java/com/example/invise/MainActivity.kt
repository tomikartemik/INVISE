package com.example.invise

import android.content.Intent
import android.content.SharedPreferences
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.invise.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.properties.Delegates


lateinit var binding: ActivityMainBinding
lateinit var databaseReference: DatabaseReference
lateinit var message: String
lateinit var pref: SharedPreferences
var ide by Delegates.notNull<Int>()
var id by Delegates.notNull<Int>()
var tid by Delegates.notNull<Int>()


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(pref.getString("Id", "")!=""){
            startActivity(Intent(this, HomeActivity::class.java))
        }
        val auth = FirebaseAuth.getInstance()
        pref = getSharedPreferences("user_info", MODE_PRIVATE)
        binding.suBtn.setOnClickListener {
            val ename = binding.editTextName.text.toString()
            val epass = binding.editTextPass.toString()
            if(ename == "") {
                Toast.makeText(this , "Вы не ввели имя" , Toast.LENGTH_SHORT).show()
            }
            if(epass == "") {
                Toast.makeText(this , "Вы не ввели пароль" , Toast.LENGTH_SHORT).show()
            }
            else{
                //database(ename)
                //addUser(ename)
                //startActivity(Intent(this , HomeActivity::class.java))
                auth.createUserWithEmailAndPassword(ename+"@gmail.com", epass)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                val currentUser = auth.currentUser
                                Toast.makeText(this, it.result!!.user!!.uid , Toast.LENGTH_SHORT).show()
                                val editor = pref.edit()
                                editor.remove("Name")
                                editor.remove("Id")
                                editor.apply()
                                editor.putString("Name", ename)
                                editor.putString("Id", it.result!!.user!!.uid)
                                editor.apply()
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            }else{
                                Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
            }

        binding.siBtn.setOnClickListener {
            val ename = binding.editTextName.text.toString()
            val epass = binding.editTextPass.toString()
            if(ename == "") {
                Toast.makeText(this , "Вы не ввели имя" , Toast.LENGTH_SHORT).show()
            }
            if(epass == "") {
                Toast.makeText(this , "Вы не ввели пароль" , Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(ename+"@gmail.com", epass)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login", "signInWithEmail:success")
                                val user = auth.currentUser
                                startActivity(Intent(this, HomeActivity::class.java))
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("login", "signInWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
        }

    }
/////////////    fun database(name : String){
/////////////        val ref = FirebaseDatabase.getInstance().reference.child("Users").child(name).push()
/////////////        ref.child("Id").setValue(0)
/////////////    }
/////////////
/////////////Получение Id последнего добавленного пользователя и добавление нового/////////////////////////////////////////////////////////////////////////
/////////////    fun addUser(name : String){
/////////////        val databaseReference = FirebaseDatabase.getInstance().reference
/////////////        val lastQuery = databaseReference.child("Users").orderByKey()
/////////////        lastQuery.addListenerForSingleValueEvent(object : ValueEventListener {
/////////////            override fun onDataChange(dataSnapshot : DataSnapshot) {
/////////////                message = dataSnapshot.value.toString()
/////////////                ide = message.indexOf("Id=" , 0 , false) + 3
/////////////                id = message.indexOf("}" , ide , false)
/////////////                tid = message.substring(ide , id).toInt()
/////////////                Log.d("String" , message)
/////////////                Log.d("Ide" , ide.toString())
/////////////                Log.d("Id" , id.toString())
/////////////                Log.d("Substring" , tid.toString())
/////////////                val ref = FirebaseDatabase.getInstance().reference.child("Users").child(name).push()
/////////////                ref.child("Id").setValue(tid + 1)
/////////////            }
/////////////
/////////////            override fun onCancelled(databaseError : DatabaseError) {
/////////////                // Handle possible errors.
/////////////            }
/////////////        })
/////////////    }