package com.example.sudoku.data

import android.content.Context
import android.widget.Toast

class Items(private var context: Context, private val settings: Settings) {
    private val tag = "myLogs"
    private var m: MatrixK = MatrixK(3)
    private var file: File = File()
    private var undoCounter = 0
    private var undoArray: Array<String> = Array(10){""}
    var cells: Array<Item> = Array(81) { Item(0, 0, 0, 0, 0, context) }

    init {
        for (id: Int in 0..80) {
            val x: Int = id / 9;
            val y: Int = ((id % 9.0)).toInt()
            cells[id] = Item(
                x,
                y,
                m.solvedMatrix[x + 1][y + 1],
                m.taskMatrix[x + 1][y + 1],
                m.auxMatrix[x + 1][y + 1][0],
                context
            )
        }
    }

    fun setZeroAuxMatrix() {
        for (id in 0..80) {
            if (cells[id].getSolved() == 0) {
                cells[id].setSolved(-1)
                cells[id].auxTableFill()
            }
        }
    }

    fun checkForWinner(): Boolean {
        if (!checkForEndGame()) {
            Toast.makeText(context, "End Game", Toast.LENGTH_LONG).show()
            return false
        }
        for (id in 0..80) if (!cells[id].checkUserSolution()) return false
        return true
    }

    private fun checkForEndGame(): Boolean {
        for (id in 0..80) if (cells[id].getSolved() in 1..2) return true
        return false
    }

    fun saveItemsToFile(){
        file.saveFile(context, "quick_save", toString())
    }

    fun saveItemsToUndo(){
        undoArray[1] = toString()
    }

    fun loadItemsFromUndo(){
        restoreItemsFromString("code to restore here")
    }

    fun loadItemsFromFile(){ restoreItemsFromString(file.readFile(context, "quick_save")) }

    private fun restoreItemsFromString(inputString: String){
        var str = inputString
        for (item in cells){
            item.fromString(str.substringBefore('#'))
            str = str.substringAfter('#')
        }
    }

    override fun toString():String{
        var str = ""
        for (item in cells)
            str += item.toString()
        return str
    }

}