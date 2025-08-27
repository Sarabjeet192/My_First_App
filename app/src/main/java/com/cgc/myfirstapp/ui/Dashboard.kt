package com.cgc.myfirstapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cgc.myfirstapp.R
import com.cgc.myfirstapp.databinding.ActivityDashboardBinding
import com.cgc.myfirstapp.ui.fragment.HomeFragment
import com.cgc.myfirstapp.ui.fragment.ProductFragment
import com.cgc.myfirstapp.ui.fragment.SearchFragment

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

   // private val networkViewModel: NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Set default fragment
        loadFragment(HomeFragment())

        // Handle Bottom Navigation Clicks
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_search -> loadFragment(SearchFragment())
                R.id.nav_profile -> loadFragment(ProductFragment())
            }
            true
        }

        // Observe network changes
//        networkViewModel.networkLiveData.observe(this, Observer { isConnected ->
//            if (isConnected) {
//                binding.networkStatus.text = "Connected to Internet"
//            } else {
//                binding.networkStatus.text = "No Internet Connectivity"
//            }
//        })


//        LocalBroadcastManager.getInstance(thiscom.cgc.firststep.ui.Dashboard)
//            .registerReceiver(receiver, IntentFilter("com.cgc.firststep.MY_LOCAL_BROADCAST"))
    }

    // Receiving a local broadcast
//    private val receiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            val msg = intent!!.getStringExtra("message")
//            binding.broadcastMessage.text = msg
//        }
//    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}