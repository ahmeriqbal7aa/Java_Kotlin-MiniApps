package com.example.todolist

// Here we have an "object" of data class "CardInfo"
// We make a list bcz list passed to Adapter as a Parameter
// "Object" is used to set data to values declared in "CardInfo" class
object DataObject {
    var listData = mutableListOf<CardInfo>()

    fun setData(title:String,priority:String){
        listData.add(CardInfo(title,priority))
    }

    fun getAllData() : List<CardInfo> {
        return listData
    }

    fun deleteAll() {
        listData.clear()
    }

    fun getData(pos: Int): CardInfo {
        return listData[pos]
    }

    fun deleteData(pos: Int): CardInfo {
        return  listData.removeAt(pos)
    }

    fun updateData(pos: Int, title: String, priority: String) {
        listData[pos].title = title
        listData[pos].priority = priority
    }
}