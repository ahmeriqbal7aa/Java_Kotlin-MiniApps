package com.example.quoteapp

object DataObject {
    var listData = mutableListOf<Quote>()

    fun setData(text: String, author: String) {
        listData.add(Quote(text, author))
    }

    fun getData(): List<Quote> {
        return listData
    }
}