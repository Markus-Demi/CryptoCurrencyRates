package com.gmail.demidovich.cryptotracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gmail.demidovich.cryptotracker.R.id.cryptoRecyclerView
import com.gmail.demidovich.cryptotracker.adapter.CryptoAdapter
import com.gmail.demidovich.cryptotracker.model.CryptoModel
import com.gmail.demidovich.cryptotracker.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class CryptoMain : AppCompatActivity() {

    private lateinit var adapter: CryptoAdapter

    private lateinit var cryptoRV: RecyclerView

    private val apiUrl = Constants.apiUrl

    private val client by lazy {
        OkHttpClient()
    }

    private val request by lazy {
        Request.Builder()
                .url(apiUrl)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_main)
        cryptoRV.findViewById<RecyclerView>(cryptoRecyclerView)
        cryptoRV.layoutManager = LinearLayoutManager(this)
        adapter = CryptoAdapter()
        cryptoRV.adapter = adapter
        getCoins()
    }

    fun getCoins() {
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed ${e?.toString()}")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println("Body: ${body}")
                val gson = Gson()
                val cryptoCoins: List<CryptoModel> = gson.fromJson(body, object : TypeToken<List<CryptoModel>>() {}.type)

                runOnUiThread {
                    adapter.updateData(cryptoCoins)
                }
            }
        })
    }
}
