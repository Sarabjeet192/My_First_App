package com.cgc.myfirstapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivityLifecycle ========"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()

    }

    private fun initView() {


        val submitBtn = findViewById<Button>(R.id.amSubmit)
        val etName = findViewById<EditText>(R.id.amName)


        submitBtn.setOnClickListener {

//            startActivity(
//                Intent(this@MainActivity, SecondScreen::class.java)
//                .putExtra("name", "Sarabjeet Singh")
//            )

            if(etName.text.isNotEmpty()) {
                val intent = Intent(this@MainActivity, SecondScreen::class.java)
                intent.putExtra("name", etName.text.toString())
                startActivity(intent)
            }else{
                Toast.makeText(this@MainActivity, "Enter your name", Toast.LENGTH_SHORT).show()
            }

      //      Toast.makeText(this@MainActivity, "Clicked", Toast.LENGTH_SHORT).show()
        }

      //  submitBtn.text = "DONE"
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onDestroy() {
        print("$TAG onDestroy")
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }



}