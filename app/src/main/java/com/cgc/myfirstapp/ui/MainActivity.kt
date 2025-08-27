package com.cgc.myfirstapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cgc.myfirstapp.R
import com.cgc.myfirstapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivityLifecycle ========"

    // Declare binding
    private lateinit var binding: ActivityMainBinding

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mDate = intent.getStringExtra("date").toString()
        val mDate2 = formatDate3(mDate)

        binding.dateOne.text = "CASE 1 = ${formatDate3(mDate)}"
        binding.dateTwo.text = "CASE 2 = ${convertDateToCustomFormat(mDate2)}"

        binding.dateThree.text = "CASE 3 = ${formatDate(mDate)}  \n\n CASE 4 = ${formatDate2(mDate)}"


        initView()

    }

    fun formatDate3(isoDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
        val date = inputFormat.parse(isoDate)
        return outputFormat.format(date!!)
    }

    fun convertDateToCustomFormat(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

            val date = inputFormat.parse(dateString)
            outputFormat.format(date!!).uppercase(Locale.ENGLISH)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }


    fun formatDate(inputDate: String): String {
        return try {
            // Input format: 2025-08-26T14:55:03.681Z
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            inputFormat.timeZone = TimeZone.getTimeZone("IST") // parse as UTC
            val date: Date = inputFormat.parse(inputDate)!!
            // Desired output format: 26-08-2025
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            outputFormat.format(date)
        } catch (e: Exception) {
            "${e.toString()}"
        }
    }

    fun formatDate2(inputDate: String): String {
        return try {
            // Input format: 2025-08-26T14:55:03.681Z
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            inputFormat.timeZone = TimeZone.getTimeZone("UTC") // parse as UTC

            val date: Date = inputFormat.parse(inputDate)!!

            // Desired output format: 26 Aug 2025
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            outputFormat.format(date)
        } catch (e: Exception) {
            "${e.toString()}"
        }
    }

    private fun initView() {

        // register
//        val resultLauncher2 =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == RESULT_OK) {
//                    if (result?.data != null) {
//                      val bitmap = result.data?.extras?.get("data") as Bitmap
//
//                        Glide.with(this@MainActivity)
//                            .load(bitmap)
//                            .into(binding.amImage)
//
//                    }
//                }
//            }
//
//        //TODO - IMAGE PICK FROM CAMERA
//        binding.amSubmit.setOnClickListener {
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            resultLauncher2.launch(cameraIntent)
//        }
//
//
//        // Register the launcher
//         resultLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) { result ->
//            if (result.resultCode == RESULT_OK) {
//                val data: Intent? = result.data
//                val message = data?.getStringExtra("resultMessage")
//                Toast.makeText(this, "Result: $message", Toast.LENGTH_SHORT).show()
//            }
//        }


 //       val submitBtn = findViewById<Button>(R.id.amSubmit)
 //       val etName = findViewById<EditText>(R.id.amName)


//        binding.amSubmit.setOnClickListener {
//
//            val intent = Intent(this, SecondScreen::class.java)
//            intent.putExtra("name", "Sarabjeet Singh")
//            resultLauncher.launch(intent)

//            startActivity(
//                Intent(this@MainActivity, SecondScreen::class.java)
//                .putExtra("name", "Sarabjeet Singh")
//            )

//            if(etName.text.isNotEmpty()) {
//                val intent = Intent(this@MainActivity, SecondScreen::class.java)
//                intent.putExtra("name", etName.text.toString())
//                startActivity(intent)
//            }else{
//                Toast.makeText(this@MainActivity, "Enter your name", Toast.LENGTH_SHORT).show()
//            }
//
//            Toast.makeText(this@MainActivity, "Clicked", Toast.LENGTH_SHORT).show()
 //       }

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
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }



}