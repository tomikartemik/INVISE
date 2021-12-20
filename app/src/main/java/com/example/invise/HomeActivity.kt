package com.example.invise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.invise.databinding.ActivityHomeBinding

lateinit var bin: ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bin.root)
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