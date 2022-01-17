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
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.zxing.WriterException
import java.util.*
import java.util.Base64.getDecoder


class QrGenerate : AppCompatActivity() {
    lateinit var binding: ActivityQrGenerateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrGenerateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            onBackPressed()
        }
        val keyGen: KeyGenerator = KeyGenerator.getInstance("AES")
        keyGen.init(1048576)
        val symkey: SecretKey = keyGen.generateKey()
        val randomString = pref.getString("UserId", null).toString() + "   " + UUID.randomUUID().toString().substring(0,15)
        val qr = QRGEncoder(randomString.toString(), null, QRGContents.Type.TEXT, 1000)
        try{
            val bMap = qr.bitmap
            binding.yourqr.setImageBitmap(bMap)
        }catch (e: WriterException){

        }
    }
}