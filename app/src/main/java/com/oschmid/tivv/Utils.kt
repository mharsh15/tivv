package com.oschmid.tivv

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun convertUnixToDate(dateMills:Long?):String{
    if(dateMills != null){
        val calendar= Calendar.getInstance()
        calendar.timeInMillis = dateMills*1000
        Log.v("time",dateMills.toString())
        val formatter = SimpleDateFormat("dd-MMM-yyyy")
        return formatter.format(calendar.time)
        //return "${calendar.get(Calendar.DATE)}-${calendar.get(Calendar.MONTH)-calendar.get(Calendar.YEAR)}"
    }
    return "No Date Selected"


}