package com.example.sudoku.data

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import com.example.sudoku.R

class Item (x: Int, y: Int,rightSolution: Int, taskItem: Int, auxMatrix0: Int, context: Context) {
    private val tag = "myLog"
    private var context = context
    var x = 0                                       //X coordinate on gameField
    var y = 0                                       //Y coordinate on gameField
    private var solved = 0                          //'-1' aux, '0' not solved, '1' solved, '2' immutable
    private var userSolution = 0;                   //user variant of solution in solved matrix
    private var rightSolution = 0;                  //calculated solution
    var auxTable = IntArray(9) {0}                  //users auxTable for 'pencil marks'
    var auxNumbers: Array<TextView> = Array(9){TextView(context)}

    init {
        if (taskItem > 0) { solved = 2; }
        else { if (taskItem == 0) { solved = auxMatrix0; }}
        auxTableInit();
        auxTableSetToZero()
        this.x = x
        this.y = y
        this.rightSolution = rightSolution;
    }

    fun checkUserSolution():Boolean{
        return ((solved == 1 && userSolution == rightSolution) || solved == 2)// return true
        //return false
    }

    fun changeAuxTable(uSolution: Int){
        Log.d(tag,"changeAuxTable")
        if (auxTableZeroCheck()) {
            solved = -1
            userSolution = 0
            auxTable[uSolution - 1] = uSolution
        } else {
            if (auxTable[uSolution - 1] == uSolution) auxTable[uSolution - 1] = 0
            else auxTable[uSolution - 1] = uSolution
            if (auxTableCheckForLastElement()!=-1){
                solved = 1;
                userSolution = auxTableCheckForLastElement()
            }
        }
        auxNumbersUpdate()
    }

    fun setItemSolved(userSolution: Int){
        if ( userSolution != this.userSolution){
            this.solved = 1
            this.userSolution = userSolution
        }
        else { setItemNotSolved() }
    }

    fun auxTableFill() {
        for (i: Int in 0..8){
            auxTable[i] = i+1
        }
        auxNumbersUpdate()
    }

    fun getSolved(): Int { return solved }
    fun setSolved(solved: Int) {this.solved = solved}
    fun getUserSolution(): Int {return userSolution}
    fun setUserSolution(userSolution: Int) {this.userSolution = userSolution}
    fun getRightSolution(): Int{return rightSolution}
    fun setRightSolution(rightSolution: Int) {this.rightSolution = rightSolution}

    override fun toString(): String{
        return """$solved$userSolution$rightSolution"""+auxTableToString()+"#"
    }

    fun fromString (inputString: String){
        var str = inputString;
        if (str[0]=='-') {solved = -1; str = str.substringAfter('-')}
        else solved = str[0].toInt() - 48
        userSolution = str[1].toInt() - 48
        rightSolution = str[2].toInt() - 48
        auxTableFromString(str.substring(3))
        auxNumbersUpdate()
        Log.d(tag, "item inputString = $inputString, solved = $solved" )
    }

    private fun auxTableToString():String{
        var result = ""
        for (item in auxTable)
            result += item.toString()
        return result
    }

    private fun auxTableFromString(inputString: String){
        for (i in 0..8)
            auxTable[i] = inputString[i].toInt() - 48
    }

    private fun setItemNotSolved(){
        this.solved = 0
        this.userSolution = 0
        auxTableSetToZero()
    }

    private fun auxTableInit() {
        for (i: Int in 0..8){
            auxTable[i] = i+1
            auxNumbers[i] = getAuxNumberTextView(i+1)
        }
    }

    private fun auxTableSetToZero(){
        for (i: Int in 0..8) auxTable[i] = 0
    }

    private fun auxTableZeroCheck(): Boolean{
        for (i: Int in 0..8) if (auxTable[i] != 0) return false
        return true
    }

    private fun auxTableCheckForLastElement(): Int {
        var counter = 0;
        var element = 0;
        for (i: Int in 0..8) if (auxTable[i] != 0) {counter++; element = i+1}
        return if (counter == 1) element; else -1
    }

    private fun auxNumbersUpdate() {
        for (i: Int in 0..8){
            if (auxTable[i] != 0) auxNumbers[i].text = auxTable[i].toString()
            else auxNumbers[i].text = ""
        }
    }
    private fun getAuxNumberTextView(textValue: Int): TextView{
        var result = TextView(context)
        result.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            0.1f
        )
        result.textSize = 8f
        result.text = textValue.toString()
        result.setTextColor(context.resources.getColor(R.color.textColor))
        result.gravity = Gravity.CENTER
        return result
    }
}