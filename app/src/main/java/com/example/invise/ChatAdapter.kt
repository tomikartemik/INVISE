package com.example.invise

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter (private val context : FragmentActivity?, private val chatList : ArrayList<Chat>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_row_new_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtUserName: TextView = view.findViewById(R.id.name)
        val avatar: ImageView = view.findViewById(R.id.avatar)
        val txtMsg: TextView = view.findViewById(R.id.name)
        val layoutUser: ConstraintLayout = view.findViewById(R.id.layoutUser)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}