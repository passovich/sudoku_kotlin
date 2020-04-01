package com.example.sudoku

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.sudoku.data.Items
import com.example.sudoku.data.Settings

class GameViewModel(context: Context, gA: GameActivity) {
    private var context: Context = context;
    private var settings: Settings = Settings(context)
    private var items: Items = Items(context, settings)
    private var gActivity: GameActivity = gA
    private var lastID = 0
    private var pen = false
    private var tag = "myLogs"

    private var numbers: Array<Array<TextView>> = Array(9) { Array(9) { TextView(context) } }
    private var auxNumbers: Array<Array<Array<TextView>>> =
        Array(9) { Array(9) { Array(9) { TextView(context) } } }
    private var tables: Array<Array<TableLayout>> = Array(9) { Array(9) { TableLayout(context) } }

    init {
        getBigNumbers()         //Инициализация больших цифр(Ручка)
        getAuxNumbers()         //Инициализация маленьких цифр (Карандаш)
        getAuxTables()          //Инициализация маленьцих таблиц (Карандаш)
    }

    fun saveQuickSave(){
        items.saveItemsToFile()
    }

    fun loadQuickSave(){
        items.loadItemsFromFile()
    }
    fun undo(){items.loadItemsFromUndo()}

    fun numberButtonPressed(buttonNumber: Int) {
        if (items.cells[lastID].getSolved() in -1..1 && pen) {
            items.cells[lastID].setItemSolved(buttonNumber)
        }
        if (items.cells[lastID].getSolved() in -1..0 && !pen) {
            items.cells[lastID].changeAuxTable(buttonNumber)
        }
        items.saveItemsToUndo()
        screenUpdate()
        if (items.checkForWinner())
            Toast.makeText(context, "We have a winner", Toast.LENGTH_LONG).show()
    }

    fun switchPen() {
        pen = !pen
        checkReset(lastID)
    }

    fun getMainTable(mainTableLayout: TableLayout) {
        //Инициализиция полной таблицы
        var mainTableRows: Array<TableRow> = Array(9) { TableRow(context) }
        for ((i, row) in mainTableRows.withIndex()) {
            val params = TableRow.LayoutParams()
            row.layoutParams = params
            row.weightSum = 0.9f
            for (j: Int in 0..8) {
                mainTableRows[i].addView(numbers[i][j])
                mainTableRows[i].addView(tables[i][j])
                tables[i][j].visibility = View.GONE
            }
            mainTableLayout.addView(row)
        }
    }

    private fun getAuxTables() {
        for (i: Int in 0..8) {
            for (j: Int in 0..8) {
                val params: TableRow.LayoutParams =
                    TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.1f);
                params.setMargins(2, 2, 2, 2);
                tables[i][j].layoutParams = params;
                tables[i][j].setBackgroundColor(settings.cellColors[i * 9 + j])
                tables[i][j].id = i * 9 + j
                tables[i][j].setOnClickListener(gActivity)
                for (k in 0..2) {
                    val tableRow = TableRow(context)
                    val rowParams = TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                    )
                    tableRow.layoutParams = rowParams
                    tableRow.weightSum = 0.3f
                    for (l in 0..2) {
                        tableRow.addView(items.cells[i * 9 + j].auxNumbers[k * 3 + l])
                    }
                    tables[i][j].addView(tableRow)
                }
            }
        }
    }

    private fun getAuxNumbers() {
        for (num in auxNumbers)
            for (row in num)
                for (cell in row) {
                    val params = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        0.1f
                    )
                    cell.layoutParams = params
                    cell.textSize = 8f
                    cell.text = "1"
                    cell.gravity = Gravity.CENTER

                    cell.setTextColor(ContextCompat.getColor(context, R.color.textColor))
                }
    }

    private fun getBigNumbers() {
        var counter = 0;
        for (row in numbers)
            for (cell in row) {
                val params = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.1f)
                params.setMargins(2, 2, 2, 2)
                cell.layoutParams = params
                cell.text = "1"
                cell.gravity = Gravity.CENTER
                cell.textSize = 25.0f
                cell.width = 0
                cell.id = counter;
                cell.setOnClickListener(gActivity)
                cell.setBackgroundColor(settings.cellColors[cell.id])
                cell.setTextColor(ContextCompat.getColor(context, R.color.textColor))
                counter++
            }
    }

    //Установка  шрифта задания в жирный
    fun setCheckedBold() {
        for (i in 0..80) {
            if (items.cells[i].getSolved() == 2)
                numbers[items.cells[i].x][items.cells[i].y]
                    .setTypeface(null, Typeface.BOLD)
            else
                numbers[items.cells[i].x][items.cells[i].y]
                    .setTypeface(null, Typeface.NORMAL)
        }
    }

    fun setZeroAuxTables() { items.setZeroAuxMatrix() }

    fun checkReset(id: Int) {
        numbers[items.cells[lastID].x][items.cells[lastID].y]
            .setBackgroundColor(settings.cellColors[lastID])
        numbers[items.cells[id].x][items.cells[id].y]
            .setBackgroundColor(settings.getCheckedColor(pen))

        tables[items.cells[lastID].x][items.cells[lastID].y]
            .setBackgroundColor(settings.cellColors[lastID])
        tables[items.cells[id].x][items.cells[id].y]
            .setBackgroundColor(settings.getCheckedColor(pen))

        lastID = id
    }

    fun screenUpdate() {
        for (i in 0..80) {
            //Cell is empty
            if (items.cells[i].getSolved() == 0) {
                numbers[items.cells[i].x][items.cells[i].y].text = ""
                numbers[items.cells[i].x][items.cells[i].y].visibility = View.VISIBLE
                tables[items.cells[i].x][items.cells[i].y].visibility = View.GONE
            }
            //We have auxiliary table
            if (items.cells[i].getSolved() == -1) {
                numbers[items.cells[i].x][items.cells[i].y].visibility = View.GONE
                tables[items.cells[i].x][items.cells[i].y].visibility = View.VISIBLE
            }
            // Cell is filled by user
            if (items.cells[i].getSolved() == 1) {
                numbers[items.cells[i].x][items.cells[i].y].text =
                    items.cells[i].getUserSolution().toString()
                numbers[items.cells[i].x][items.cells[i].y].visibility = View.VISIBLE
                tables[items.cells[i].x][items.cells[i].y].visibility = View.GONE
            }
            //Cell is filled by a fixed digit
            if (items.cells[i].getSolved() == 2) {
                numbers[items.cells[i].x][items.cells[i].y].text =
                    items.cells[i].getRightSolution().toString()
                numbers[items.cells[i].x][items.cells[i].y].visibility = View.VISIBLE
                tables[items.cells[i].x][items.cells[i].y].visibility = View.GONE
            }

        }
    }
}