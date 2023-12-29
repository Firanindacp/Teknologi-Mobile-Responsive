package com.firanindacp_5210411046.a5210411046_activityeksternal

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class receriverActivity : AppCompatActivity() {

    private lateinit var tv_receivedData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receriver)

        tv_receivedData = findViewById(R.id.tv_dataReceived)

        if (intent.action == Intent.ACTION_VIEW) {
            val uri: Uri? = intent.data

            if (uri != null) {
                val receivedData: String? = uri.getQueryParameter("Data")

                tv_receivedData.text = "Data yang diterima: $receivedData"
                }
            }
        }
    }