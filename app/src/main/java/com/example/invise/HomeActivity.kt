package com.example.invise

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.invise.databinding.ActivityHomeBinding

lateinit var bin: ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bin.root)
        val colors = arrayOf(
                Color.parseColor("#e48826"),
                Color.parseColor("#bb99b7"),
                Color.parseColor("#ecc8c9"),
                Color.parseColor("#c6a78f"),
                Color.parseColor("#a0b3a8"),
        )
        val color = colors.random()
        supportFragmentManager.beginTransaction().replace(R.id.frame, chatsFragment.newInstance()).commit()
        bin.btmNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.chats -> supportFragmentManager.beginTransaction().replace(R.id.frame, chatsFragment.newInstance()).commit()
                R.id.qr -> supportFragmentManager.beginTransaction().replace(R.id.frame, qrFragment.newInstance()).commit()
                R.id.settings -> supportFragmentManager.beginTransaction().replace(R.id.frame, settingsFragment.newInstance()).commit()
            }
            true
        }
    }
}