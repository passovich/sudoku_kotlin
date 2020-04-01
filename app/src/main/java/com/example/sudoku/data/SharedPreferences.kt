package com.example.sudoku.data

import android.content.Context
import com.example.sudoku.data.File

class SPreferences {
    fun onCreateXMLFile(context: Context, fileName: String?) {
        //MODE_PRIVATE - создать файл или пересоздать, если он есть
        val sPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sPref.edit()
        editor.apply()
    }

    fun onSaveXMLData(context: Context, fileName: String?, nameKey: String?, data: String?) {
        //MODE_APPEND - Открыть файл для работы, или создать новый, если такового нету
        val sPref = context.getSharedPreferences(fileName, Context.MODE_APPEND)
        val editor = sPref.edit()
        editor.putString(nameKey, data)
        editor.apply()
    }

    fun onReadXMLData(context: Context, fileName: String?, nameKey: String?): String? {
        val sPref = context.getSharedPreferences(fileName, Context.MODE_APPEND)
        return sPref.getString(nameKey, "")
    }

    fun onDeleteXMLData(context: Context, fileName: String?, nameKey: String?) {
        val sPref = context.getSharedPreferences(fileName, Context.MODE_APPEND)
        val editor = sPref.edit()
        editor.remove(nameKey)
        editor.apply()
    }

    fun onDeleteXMLFile(context: Context, fileName: String) {
        val filePath = context.applicationContext.filesDir
            .parent + "/shared_prefs/" + fileName + ".xml"
        val deletePrefFile:File = File()
        deletePrefFile.deleteFile(context,filePath)
    }
}
