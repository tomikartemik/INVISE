package com.example.invise

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.invise.databinding.FragmentQrBinding

class qrFragment : Fragment() {
    lateinit var binding: FragmentQrBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrBinding.inflate(inflater)
        binding.but.setOnClickListener{
            startActivity(Intent(this@qrFragment.context , QrGenerate::class.java))
        }
        return binding.root
    }
    
    companion object {
        @JvmStatic
        fun newInstance() = qrFragment()
    }
}