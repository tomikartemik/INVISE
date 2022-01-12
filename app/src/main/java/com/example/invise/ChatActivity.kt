package com.example.invise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.invise.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

lateinit var binding_chat: ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var chatList = ArrayList<Chat>()
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding_chat = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding_chat.root)
        var llm = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        llm.stackFromEnd = true
        binding_chat.chatRecycle.layoutManager = llm

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
        readMessage(firebaseUser!!.uid, userId)
    }
    private fun sendMessage(senderId: String, receiverId: String, message: String){
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()
        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat").push().setValue(hashMap )

    }
    private fun readMessage(senderId: String, receiverId: String){
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chat")
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children){

                    val chat = dataSnapShot.getValue(Chat::class.java)
                    if (chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId)||
                    chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId)) {
                        chatList.add(chat)
                    }
                }
                val chatAdapter = ChatAdapter(this@ChatActivity, chatList)
                binding_chat.chatRecycle.adapter = chatAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}