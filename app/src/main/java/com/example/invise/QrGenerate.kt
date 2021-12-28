package com.example.invise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.invise.databinding.ActivityQrGenerateBinding
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import android.util.Base64
import java.util.Base64.getEncoder
import javax.crypto.spec.SecretKeySpec
import android.R.raw
import java.util.Base64.getDecoder


class QrGenerate : AppCompatActivity() {
    lateinit var binding: ActivityQrGenerateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrGenerateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val keyGen: KeyGenerator = KeyGenerator.getInstance("AES")
        keyGen.init(1048576)
        val symkey: SecretKey = keyGen.generateKey()
        binding.textView2.text =symkey.toString()



    }
}