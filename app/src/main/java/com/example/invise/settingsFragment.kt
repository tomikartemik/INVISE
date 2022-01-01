package com.example.invise

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.invise.databinding.FragmentSettingsBinding

class settingsFragment : Fragment() {

    lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        binding.nameText.text = pref.getString("Name", "")
        binding.logout.setOnClickListener {
            val creator = pref.edit()
            creator.remove("Name")
            creator.remove("UserId")
            creator.apply()
            startActivity(Intent(this.context, MainActivity::class.java))
            activity?.finish()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = settingsFragment()
    }
}