package com.example.invise

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.nfc.Tag
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.invise.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.FirebaseCommonKtxRegistrar
import kotlin.properties.Delegates


lateinit var binding: ActivityMainBinding
lateinit var auth : FirebaseAuth
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
        pref = getSharedPreferences("user_info", MODE_PRIVATE)
        if (pref.getString("Name", null)?.toString() != null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        binding.suBtn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            val ename = binding.editTextName.text.toString()
            val epass = binding.editTextPass.text.toString()
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
                                val userId:String = currentUser!!.uid
                                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)
                                var hashMap:HashMap<String, String> = HashMap()
                                hashMap.put("userId", userId)
                                hashMap.put("username", ename)
                                databaseReference.setValue(hashMap).addOnCompleteListener(this){
                                    if (it.isSuccessful){
                                        Toast.makeText(this, userId , Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, HomeActivity::class.java))
                                        val creator = pref.edit()
                                        creator.putString("Name", ename)
                                        creator.putString("UserId", userId)
                                        creator.apply()
                                        Log.d("Name", pref.getString("Name", null).toString())
                                        Log.d("UserId", pref.getString("UserId", null).toString())
                                        finish()
                                    }
                                }
                            }else{
                                Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
            }

        binding.siBtn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            val ename = binding.editTextName.text.toString()
            val epass = binding.editTextPass.text.toString()
            if(ename == "") {
                Toast.makeText(this , "Вы не ввели имя" , Toast.LENGTH_SHORT).show()
            }
            if(epass == "") {
                Toast.makeText(this , "Вы не ввели пароль" , Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(ename+"@gmail.com", epass)
                        .addOnCompleteListener(this) {
                            if (it.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login", "signInWithEmail:success")
                                startActivity(Intent(this, HomeActivity::class.java))
                                val currentUser = auth.currentUser
                                val userId:String = currentUser!!.uid
                                val creator = pref.edit()
                                creator.putString("Name", ename)
                                creator.putString("UserId", userId)
                                creator.apply()
                                Log.d("Name", pref.getString("Name", null).toString())
                                Log.d("UserId", pref.getString("UserId", null).toString())
                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
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