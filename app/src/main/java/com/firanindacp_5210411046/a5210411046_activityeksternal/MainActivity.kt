package com.firanindacp_5210411046.a5210411046_activityeksternal

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var wifiSwitch: Switch
    lateinit var wifiManager: WifiManager

    lateinit var pinLocationBtn: MaterialButton

    private val latitudeOne = ""
    private val longitudeOne = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDialNumber: Button = findViewById(R.id.dialNumber)
        btnDialNumber.setOnClickListener(this)

        val btnWebsite: Button = findViewById(R.id.btn_Website)
        btnWebsite.setOnClickListener(this)

        val btnEmail: Button = findViewById(R.id.btn_Email)
        btnEmail.setOnClickListener(this)

        val btnPesan: Button = findViewById(R.id.btn_Pesan)
        btnPesan.setOnClickListener(this)

        val btnReceiver: Button = findViewById(R.id.btn_receiverURi)
        btnReceiver.setOnClickListener(this)

        pinLocationBtn = findViewById(R.id.pinLocationBtn)

        pinLocationBtn.setOnClickListener{
          pinLocationMap(latitudeOne, longitudeOne)
        }

        title = "ActivityApp"
        wifiSwitch = findViewById(R.id.wifiSwitch)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                wifiManager.isWifiEnabled = true
                wifiSwitch.text = "Wifi is ON"
            } else {
                wifiManager.isWifiEnabled = false
                wifiSwitch.text = "Wifi is OFF"
            }
        }
    }

//    start akses maps
    private fun pinLocationMap(latitude: String, longitude: String){
        val mapUri = Uri.parse("https://maps.google.com/maps/search/$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW)
        startActivity(intent)
    }
//    end akses maps

//    Start broadcast receiver
    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)
    }

    private val wifiStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN
            )) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    wifiSwitch.isChecked = true
                    wifiSwitch.text = "Wifi is ON"
                    Toast.makeText(this@MainActivity, "Wifi is ON", Toast.LENGTH_SHORT).show()
                }

                WifiManager.WIFI_STATE_DISABLED -> {
                    wifiSwitch.isChecked = false
                    wifiSwitch.text = "Wifi is OFF"
                    Toast.makeText(this@MainActivity, "Wifi is OFF", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
//    End broadcast receiver

    override fun onClick(v: View?) {
        when (v?.id){
//            start intent dial
            R.id.dialNumber -> {
                val phoneNumber = "082136268905"
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialIntent)
            }
//            end intent dial

//  start intent website
            R.id.btn_Website -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://uty.ac.id/")
                val i = Intent.createChooser(intent, "Choose your preffered application")
                startActivity(i)
            }
//  end intent website

//  start intent email
            R.id.btn_Email -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:firaninda01@gmail.com")
                intent.putExtra(Intent.EXTRA_SUBJECT, "Ini bagian subjek")
                intent.putExtra(Intent.EXTRA_TEXT, "Ini Bagian body")
                val i = Intent.createChooser(intent, "Choose your preffered application")
                startActivity(i)
            }
//  end intent website


//  start intent pesan
            R.id.btn_Pesan -> {
                val text = "Firaninda Cahya Prista_TMR D"
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, text)
                val i = Intent.createChooser(intent, "Choose your preffered application")
                startActivity(i)
            }
//  end intent pesan

//  start mengirim data Uria
            R.id.btn_receiverURi -> {
                // Data yang ingin Anda kirim melalui URI
                val dataToSend = "Ini adalah data yang akan dikirim"

                val uri = Uri.parse("myapp://example?data=$dataToSend")

                // Membuat intent untuk memulai ReceiverActivity
                val intent = Intent(this, receriverActivity::class.java)
                intent.action = Intent.ACTION_VIEW
                intent.data = uri

                startActivity(intent)
            }
//  end mengirim data Uri


        }
    }
}
