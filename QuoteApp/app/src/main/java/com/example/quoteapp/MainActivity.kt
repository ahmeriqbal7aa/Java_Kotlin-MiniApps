package com.example.quoteapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quoteapp.DataObject.setData


class MainActivity : AppCompatActivity() {
    // TODO Variables
    lateinit var mainViewModel: MainViewModel

    private val quoteText: TextView
        get() = findViewById(R.id.quoteText)

    private val quoteAuthor: TextView
        get() = findViewById(R.id.quoteAuthor)

    // TODO Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // we pass "application's context" instead of "context" to avoid activity's obj
        // destruction when we'll change mobile orientation or do any other configuration
        // viewMode; obj retains in both cases so we don't need to change it
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(application)
        ).get(MainViewModel::class.java)

        setQuote(mainViewModel.getQuote())
    }

    // Set
    fun setQuote(quote: Quote) {
        quoteText.text = quote.text
        quoteAuthor.text = quote.author
        // set data to data object
        setData(quote.text, quote.author)
    }

    // Next
    fun onNext(view: View) {
        setQuote(mainViewModel.nextQuote())
    }

    // Previous
    fun onPrevious(view: View) {
        setQuote(mainViewModel.previousQuote())
    }

    // Fab
    fun onShare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
//            .setType("vnd.android-dir/mms-sms")
        intent.putExtra(Intent.EXTRA_TEXT, DataObject.getData().toString())
        startActivity(Intent.createChooser(intent, "Share to..."))
    }
}