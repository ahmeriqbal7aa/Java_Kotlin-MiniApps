package com.example.quoteapp

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class MainViewModel(val context: Context) : ViewModel() {
    private var quoteList: Array<Quote> = emptyArray()
    private var index = 0

    // TODO Initialize
    init {
        quoteList = loadQuoteFromAssets()
    }

    private fun loadQuoteFromAssets(): Array<Quote> {
        val inputStream = context.assets.open("quotes.json")
        val sizeOfInputStream: Int = inputStream.available()
        // json data stored in buffer in ByteArrayForm
        val buffer = ByteArray(sizeOfInputStream)
        inputStream.read(buffer)
        inputStream.close()

        // json's encoding: ByteArray to String
        val jsonData = String(buffer, Charsets.UTF_8)
        // parse string type json to java object (Type Casting)
        val gson = Gson()
        return gson.fromJson(jsonData, Array<Quote>::class.java)
    }

    fun getQuote() = quoteList[index]

    fun nextQuote() = quoteList[++index % quoteList.size]

    fun previousQuote() = quoteList[(--index + quoteList.size) % quoteList.size]
}