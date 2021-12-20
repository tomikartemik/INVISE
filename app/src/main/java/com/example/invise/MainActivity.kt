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

lateinit var binding: ActivityMainBinding
lateinit var auth: FirebaseAuth
lateinit var databaseReference: DatabaseReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btn.setOnClickListener {
            val editName = binding.editTextName.text.toString()
            if(editName != ""){
                registerUser(editName, "0")
            }else{
                Toast.makeText(this, "Вы не ввели имя", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun registerUser(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val hashMap:HashMap<String, String> = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("username", email)

                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
                        if(it.isSuccessful){
                            startActivity(Intent(this, HomeActivity::class.java))
                        }
                    }
                }
            }
    }
}