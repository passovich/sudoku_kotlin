package com.example.sudoku.data

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.sudoku.R

class Settings (context: Context) {
    private val tag = "myLogs"
    private val context = context
    val xml = SPreferences()
    var bgGray = ContextCompat.getColor(context, R.color.bgGrayColor);
    var bgLight = ContextCompat.getColor(context, R.color.bgLightColor)
    var bgCheckedPen = ContextCompat.getColor(context, R.color.bgCheckedPenColor)
    var bgCheckedPencil = ContextCompat.getColor(context, R.color.bgCheckedPencilColor)
    var l = bgLight
    var g = bgGray
    var difficulty = 3
    var maxUndoCounter = 10

    var cellColors = intArrayOf(
        l,l,l,g,g,g,l,l,l,
        l,l,l,g,g,g,l,l,l,
        l,l,l,g,g,g,l,l,l,
        g,g,g,l,l,l,g,g,g,
        g,g,g,l,l,l,g,g,g,
        g,g,g,l,l,l,g,g,g,
        l,l,l,g,g,g,l,l,l,
        l,l,l,g,g,g,l,l,l,
        l,l,l,g,g,g,l,l,l

    //retain settings

    )
    fun getCheckedColor(pen: Boolean):Int {
        return if (pen) bgCheckedPen else bgCheckedPencil
    }
    fun saveSettingsToDisk() {
        Log.d(tag, "SaveSettingsOnDisk")
        xml.onSaveXMLData(context, "settings", "difficulty", difficulty.toString())
    }

    fun loadSettingsFromDisk() {
        Log.d(tag, "loadSettingsFromDisk")
        try {
            difficulty = xml.onReadXMLData(context, "settings", "difficulty")!!.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            saveSettingsToDisk() //needed if application is started first time
        }
    }
}