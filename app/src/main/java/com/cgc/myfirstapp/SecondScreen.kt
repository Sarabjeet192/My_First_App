package com.cgc.myfirstapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cgc.myfirstapp.databinding.ActivityMainBinding
import com.cgc.myfirstapp.databinding.ActivitySecondScreenBinding
import com.cgc.myfirstapp.ui.MainActivity

class SecondScreen : AppCompatActivity() {

    // Declare binding
    private lateinit var binding: ActivitySecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //setContentView(R.layout.activity_second_screen)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {
       // val fullName = intent.getStringExtra("name")

       // val tvName = findViewById<TextView>(R.id.ssName)

       // binding.ssName.text = fullName

        binding.ssSetResult.setOnClickListener {
            startActivity(Intent(this@SecondScreen, MainActivity::class.java).putExtra("date", "2025-08-26T14:55:03.681Z"))
//                val resultIntent = Intent()
//                resultIntent.putExtra("resultMessage", "Hello, back from Second Activity")
//                setResult(RESULT_OK, resultIntent)
//                finish()
        }
    }

    override fun onDestroy() {
        Log.d("=============", "onDestroy Second activity")
        super.onDestroy()
    }

}