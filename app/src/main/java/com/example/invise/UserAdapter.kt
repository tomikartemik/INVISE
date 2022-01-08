package com.example.invise

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context : FragmentActivity? , private val userList : ArrayList<UserItem>):
        RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row_new_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder : ViewHolder , position : Int) {
        val user = userList[position]
        val colors = arrayOf(
                Color.parseColor("#E6E6FA"),
                Color.parseColor("#D8BFD8"),
                Color.parseColor("#DDA0DD"),
                Color.parseColor("#EE82EE"),
                Color.parseColor("#DA70D6"),
                Color.parseColor("#BA55D3"),
                Color.parseColor("#9370DB"),
                Color.parseColor("#8A2BE2"),
                Color.parseColor("#9400D3"),
                Color.parseColor("#9932CC"),
                Color.parseColor("#6A5ACD"),
                Color.parseColor("#FFFFFF"),
        )
        val color = colors.random()
        holder.txtUserName.text = user.username
        holder.txtUserName.setTextColor(color)
        holder.avatar.setColorFilter(color)
        holder.layoutUser.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("userId", user.userId)
            intent.putExtra("username", user.username)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount() : Int {
        return userList.size
    }
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txtUserName:TextView = view.findViewById(R.id.name)
        val avatar:ImageView = view.findViewById(R.id.avatar)
        val txtMsg:TextView = view.findViewById(R.id.name)
        val layoutUser: ConstraintLayout = view.findViewById(R.id.layoutUser)
    }
}