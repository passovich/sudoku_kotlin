package com.example.sudoku.data

import android.content.Context
import android.util.Log
import java.io.*

class File {
    private val tag = "myLogs"
    fun createFile(context: Context, fileName: String) {
        Log.d(tag, "CreateFile $fileName")
        try {
            val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveFile(context: Context, fileName: String, information: String ) {
        Log.d(tag, "saveFile $fileName")
        try {
            val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            outputStream.write(information.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readFile(context: Context, fileName: String): String {
        Log.d(tag, "readFile $fileName")
        var ret = ""
        try {
            val inputStream: InputStream? = context.openFileInput(fileName)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder = StringBuilder()
                var receiveString:String? = bufferedReader.readLine()
                while (receiveString!=null) {
                    stringBuilder.append(receiveString)
                    Log.d(tag, "receive String$receiveString")
                    receiveString = bufferedReader.readLine()
                }
                inputStream.close()
                ret = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            Log.e(tag, "File not found$e")
        } catch (e: IOException) {
            Log.e(tag, "Can't read file$e")
        }
        return ret
    }

    fun deleteFile(context: Context, fileName: String) {
        Log.d(tag, "deleteFile $fileName")
        context.deleteFile(fileName)
    }
}
