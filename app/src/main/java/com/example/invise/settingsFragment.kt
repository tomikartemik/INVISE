package com.example.invise

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.invise.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class settingsFragment : Fragment() {

    lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(
            inflater : LayoutInflater , container : ViewGroup? ,
            savedInstanceState : Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        binding.nameText.text = pref.getString("Name" , "")
        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
            val creator = pref.edit()
            creator.remove("Name")
            creator.remove("UserId")
            creator.apply()
            startActivity(Intent(this.context , MainActivity::class.java))
            activity?.finish()
        }
        binding.avatarka.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent , 0)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode : Int , resultCode : Int , data : Intent?) {
        super.onActivityResult(requestCode , resultCode , data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver , uri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.avatarka.setBackgroundDrawable(bitmapDrawable)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = settingsFragment()
    }
}