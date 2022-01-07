package com.example.invise

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.invise.databinding.FragmentChatsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class chatsFragment : Fragment() {

    lateinit var binding: FragmentChatsBinding
    lateinit var adapter: UserAdapter
    var userList = ArrayList<UserItem>()

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(inflater)
        binding.recycleView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        ////////
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot : DataSnapshot) {
                userList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children){
                    val user = dataSnapShot.getValue(UserItem::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                val adapter = UserAdapter(activity, userList)
                binding.recycleView.adapter = adapter
            }

            override fun onCancelled(error : DatabaseError) {
                Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
            }

        })
        /////
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = chatsFragment()
    }

}