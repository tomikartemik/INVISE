package com.example.invise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.invise.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

lateinit var binding_chat: ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding_chat = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding_chat.root)
        var back_btn: ImageView = findViewById(R.id.back)
        back_btn.setOnClickListener {
            onBackPressed()
        }

        val intent = getIntent()
        val userId = intent.getStringExtra("userId")
        val username = intent.getStringExtra("username")

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)
        reference!!.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserItem::class.java)
                var txt_username: TextView = findViewById(R.id.name_on_top)
                txt_username.text = user!!.username
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        binding_chat.sendBtn.setOnClickListener {
            var message: String = binding_chat.edMessage.text.toString()
            if (message == ""){
                Toast.makeText(this, "Message is empty!", Toast.LENGTH_SHORT).show()
            }else{
                sendMessage(firebaseUser!!.uid, userId, message)
                binding_chat.edMessage.setText("")
            }
        }
    }
    private fun sendMessage(senderId: String, receiverId: String, message: String){
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()
        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat").push().setValue(hashMap )

    }
}