package com.cgc.myfirstapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cgc.myfirstapp.databinding.ActivitySharePrefrenceExampleBinding
import com.cgc.myfirstapp.utils.Constant
import com.cgc.myfirstapp.utils.MyAppPreference

class SharePrefrenceExample : AppCompatActivity() {

    private lateinit var binding: ActivitySharePrefrenceExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharePrefrenceExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        if(MyAppPreference.getStringPreference(this@SharePrefrenceExample, Constant.USER_NAME) == "") {
            binding.apShowValue.text = "User name not found"
        }else{
            binding.apShowValue.text = MyAppPreference.getStringPreference(this@SharePrefrenceExample, Constant.USER_NAME)
        }


        binding.apSubmit.setOnClickListener {

            if(binding.apShareValue.text.toString().isNotEmpty()){
                MyAppPreference.putStringPreference(this@SharePrefrenceExample, binding.apShareValue.text.toString(), Constant.USER_NAME)
                Toast.makeText(this@SharePrefrenceExample, "Saved Successfully", Toast.LENGTH_SHORT).show()
                binding.apShowValue.text = MyAppPreference.getStringPreference(this@SharePrefrenceExample, Constant.USER_NAME)
            }else{
                Toast.makeText(this@SharePrefrenceExample, "Enter value", Toast.LENGTH_SHORT).show()
            }

        }
    }
}