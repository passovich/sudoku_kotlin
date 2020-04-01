package com.example.sudoku.data

import android.util.Log
import java.util.*

class MatrixK (difficulty: Int) {
    private val tag = "myLogs"
    private var difficulty = 1
    private val basicMatrix = arrayOf(
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 2, 4, 8, 7, 9, 5, 6, 3, 1),
        arrayOf(0, 5, 1, 7, 6, 2, 3, 9, 8, 4),
        arrayOf(0, 6, 3, 9, 1, 8, 4, 7, 5, 2),
        arrayOf(0, 8, 9, 6, 4, 5, 2, 1, 7, 3),
        arrayOf(0, 3, 7, 2, 9, 1, 8, 4, 6, 5),
        arrayOf(0, 1, 5, 4, 3, 6, 7, 2, 9, 8),
        arrayOf(0, 9, 6, 5, 2, 3, 1, 8, 4, 7),
        arrayOf(0, 4, 8, 1, 5, 7, 6, 3, 2, 9),
        arrayOf(0, 7, 2, 3, 8, 4, 9, 5, 1, 6)
    )
    var solvedMatrix = arrayOf(
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 2, 4, 8, 7, 9, 5, 6, 3, 1),
        arrayOf(0, 5, 1, 7, 6, 2, 3, 9, 8, 4),
        arrayOf(0, 6, 3, 9, 1, 8, 4, 7, 5, 2),
        arrayOf(0, 8, 9, 6, 4, 5, 2, 1, 7, 3),
        arrayOf(0, 3, 7, 2, 9, 1, 8, 4, 6, 5),
        arrayOf(0, 1, 5, 4, 3, 6, 7, 2, 9, 8),
        arrayOf(0, 9, 6, 5, 2, 3, 1, 8, 4, 7),
        arrayOf(0, 4, 8, 1, 5, 7, 6, 3, 2, 9),
        arrayOf(0, 7, 2, 3, 8, 4, 9, 5, 1, 6)
    )
    var matrix = arrayOf(
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 4, 0, 7, 0, 5, 0, 0, 0),
        arrayOf(0, 0, 1, 7, 6, 2, 0, 0, 0, 0),
        arrayOf(0, 6, 0, 9, 0, 0, 4, 7, 0, 2),
        arrayOf(0, 0, 9, 0, 0, 0, 0, 0, 7, 3),
        arrayOf(0, 3, 0, 2, 9, 0, 8, 4, 0, 5),
        arrayOf(0, 1, 5, 0, 0, 0, 0, 0, 9, 0),
        arrayOf(0, 9, 0, 5, 2, 0, 0, 8, 0, 7),
        arrayOf(0, 0, 0, 0, 0, 7, 6, 3, 2, 0),
        arrayOf(0, 0, 0, 0, 8, 0, 9, 0, 1, 0)
    )
    var taskMatrix = arrayOf(
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 4, 0, 7, 0, 5, 0, 0, 0),
        arrayOf(0, 0, 1, 7, 6, 2, 0, 0, 0, 0),
        arrayOf(0, 6, 0, 9, 0, 0, 4, 7, 0, 2),
        arrayOf(0, 0, 9, 0, 0, 0, 0, 0, 7, 3),
        arrayOf(0, 3, 0, 2, 9, 0, 8, 4, 0, 5),
        arrayOf(0, 1, 5, 0, 0, 0, 0, 0, 9, 0),
        arrayOf(0, 9, 0, 5, 2, 0, 0, 8, 0, 7),
        arrayOf(0, 0, 0, 0, 0, 7, 6, 3, 2, 0),
        arrayOf(0, 0, 0, 0, 8, 0, 9, 0, 1, 0)
    )
    var auxMatrix = Array(10) {Array(10) {Array(10) {0}}}
    var undoMatrix = Array (10) {Array(10) {Array(10) {Array(10) {0} } } }
    var undoCounter = 0

    init{
        this.difficulty = difficulty
        getSolvedMatrix(basicMatrix,solvedMatrix)
        getNonSolvedMatrix(basicMatrix, solvedMatrix, matrix)
        generateAuxMatrix(matrix, auxMatrix)
        copyMatrix(matrix, taskMatrix)
     }

    fun undoSet(auxMatrix: Array<Array<Array<Int>>>, undoMatrix: Array<Array<Array<Array<Int>>>>) {
        //сдвигаем массивы auxMatrix в массиве undoMatrix вправо
        for (y in 8 downTo 0) {
            for (i in 0..9) {
                for (j in 0..9) {
                    for (k in 0..9) {
                        undoMatrix[y + 1][i][j][k] =
                            undoMatrix[y][i][j][k]
                    }
                }
            }
        }
        //в нулевой элемент записываем новый auxMatrix
        for (i in 0..9) {
            for (j in 0..9) {
                for (k in 0..9) {
                    undoMatrix[0][i][j][k] = auxMatrix[i][j][k]
                }
            }
        }
        if (undoCounter < 10) undoCounter++
        Log.d(tag,"undoSet, undo counter=" + undoCounter)
        Log.d(tag, toString(auxMatrix))
    }

    fun undoGet(auxMatrix: Array<Array<IntArray>>, undoMatrix: Array<Array<Array<IntArray>>>) {
        if (undoCounter > 0) {
            //берём auxMatrix из нулевой ячейки
            for (i in 0..9) {
                for (j in 0..9) {
                    for (k in 0..9) {
                        auxMatrix[i][j][k] = undoMatrix[0][i][j][k]
                    }
                }
            }
            for (y in 1..9) {
                for (i in 0..9) {
                    for (j in 0..9) {
                        for (k in 0..9) {
                            undoMatrix[y - 1][i][j][k] =
                                undoMatrix[y][i][j][k]
                        }
                    }
                }
            }
            undoCounter--
            Log.d(tag, "undoGet, undo counter=$undoCounter")
         //   Log.d(tag,toString(auxMatrix))
        }
    }

    private fun lineProcess(auxMatrix: Array<Array<Array<Int>>>, rowNumber: Int) {
        //удалить лишние элементы доп матрицы в указанной строке
        for (i in 1..9) {
            if (auxMatrix[rowNumber][i][0] != 0) {
                for (j in 1..9) {
                    if (j != i) auxMatrix[rowNumber][j][auxMatrix[rowNumber][i][0]] = 0
                }
            }
        }
    }
    private fun lineProcessAll(auxMatrix: Array<Array<Array<Int>>>) {
        //Удалить лишние элементиы доп матрицы во всех строках
        for (i in 1..9)  lineProcess(auxMatrix, i)
    }
    private fun columnProcess(
        auxMatrix: Array<Array<Array<Int>>>,
        columnNumber: Int
    ) {
        //удалить лишние элементы доп матрицы в указанном столбце
        for (i in 1..9) {
            if (auxMatrix[i][columnNumber][0] != 0) {
                for (j in 1..9) {
                    if (j != i) auxMatrix[j][columnNumber][auxMatrix[i][columnNumber][0]] = 0
                }
            }
        }
    }
    fun columnProcessAll(auxMatrix: Array<Array<Array<Int>>>) {
        //Удалить лишние элементиы доп матрицы во всех столбцах
        for (i in 1..9) {
            columnProcess(auxMatrix, i)
        }
    }

    private fun squareProcess(
        auxMatrix: Array<Array<Array<Int>>>,
        iStart: Int,
        jStart: Int
    ) {
        //Удалить лишние элементы в квадрате с указанным началом координат
        var element = 0
        for (i in iStart..iStart + 2) {
            for (j in jStart..jStart + 2) {
                if (auxMatrix[i][j][0] != 0) {
                    element = auxMatrix[i][j][0]
                    for (k in iStart..iStart + 2) {
                        for (l in jStart..jStart + 2) {
                            if (k != i || l != j) auxMatrix[k][l][element] = 0
                        }
                    }
                }
            }
        }
    }
    private fun squareProcessAll(auxMatrix: Array<Array<Array<Int>>>) {
        //Удаляем лишние элементы во всех квадратах
        var i = 1
        while (i <= 7) {
            var j = 1
            while (j <= 7) {
                squareProcess(auxMatrix, i, j)
                j += 3
            }
            i += 3
        }
    }
    fun auxMatrixProcessAll(auxMatrix: Array<Array<Array<Int>>>) {
        // решение судоку из полной дополнительной матрицы
        var counter = 0
        val tempMatrix = Array(10) { Array(10) { Array(10){0} } }
        copyMatrix(auxMatrix, tempMatrix)
        var comparation = false
        while (!comparation) {              //работаем над матрицей, пока есть изменения от логики
            copyMatrix(auxMatrix, tempMatrix)
            columnProcessAll(auxMatrix)
            lineProcessAll(auxMatrix)
            squareProcessAll(auxMatrix)
            auxMatrixCheck(auxMatrix)
            counter++
            comparation = compareMatrix(auxMatrix, tempMatrix)
        }
    }
    private fun auxMatrixCheck(auxMatrix: Array<Array<Array<Int>>>): Boolean {
        //Проверка доп матрицы на вычисленные элементы
        var counter = 0
        var element = 0
        for (i in 1..9) {
            for (j in 1..9) {
                if (auxMatrix[i][j][0] == 0) {
                    for (k in 1..9) {
                        if (auxMatrix[i][j][k] == 0) counter++
                        else element = auxMatrix[i][j][k]
                    }
                    if (counter == 8) auxMatrix[i][j][0] = element
                    if (counter == 9) return false
                    counter = 0
                }
            }
        }
        return true
    }
    private fun copyMatrix(
        matrix1: Array<Array<Int>>,
        matrix2: Array<Array<Int>>
    ) {
        // копирывание матрицы 1 в матрицу 2
        for (i in 0..9) {
            for (j in 0..9) {
                matrix2[i][j] = matrix1[i][j]
            }
        }
    }
    private fun copyMatrix(
        matrix1: Array<Array<Array<Int>>>,
        matrix2: Array<Array<Array<Int>>>
    ) {
        // копирывание матрицы 1 в матрицу 2
        for (i in 0..9) {
            for (j in 0..9) {
                for (k in 0..9) {
                    matrix2[i][j][k] = matrix1[i][j][k]
                }
            }
        }
    }
    private fun compareMatrix(
        matrix1: Array<Array<Int>>,
        matrix2: Array<Array<Int>>
    ): Boolean {
        var answer = true
        for (i in 0..9) {
            for (j in 0..9) {
                if (matrix2[i][j] != matrix1[i][j]) answer = false
            }
        }
        return answer
    }

    private fun compareMatrix(
        matrix1: Array<Array<Array<Int>>>,
        matrix2: Array<Array<Int>>
    ): Boolean {
        var answer = true
        for (i in 0..9) {
            for (j in 0..9) {
                if (matrix2[i][j] != matrix1[i][j][0]) answer = false
            }
        }
        return answer
    }

    private fun compareMatrix(
        matrix1: Array<Array<Array<Int>>>,
        matrix2: Array<Array<Array<Int>>>
    ): Boolean {
        var answer = true
        for (i in 0..9) {
            for (j in 0..9) {
                for (k in 0..9) {
                    if (matrix2[i][j][k] != matrix1[i][j][k]) answer = false
                }
            }
        }
        return answer
    }
    fun auxMatrixGenerate(
        matrix: Array<Array<Int>>,
        auxMatrix: Array<Array<Array<Int>>>
    ) {
        for (i in 1..9) {
            for (j in 1..9) {
                if (matrix[i][j] != 0) {
                    auxMatrix[i][j][matrix[i][j]] = matrix[i][j]
                    auxMatrix[i][j][0] =
                        matrix[i][j] //Если 0, то значение ячейки неизвестно
                } else for (k in 1..9) {
                    auxMatrix[i][j][k] = k
                }
            }
        }
    }
    private fun generateAuxMatrix( //формирование полной дополнительной матрицы
        matrix: Array<Array<Int>>,
        auxMatrix: Array<Array<Array<Int>>>
    ) {
        Log.d(tag, "generateAuxMatrix")
        for (i in 1..9) {
            for (j in 1..9) {
                if (matrix[i][j] != 0) {
                    auxMatrix[i][j][0] = matrix[i][j]
                } else auxMatrix[i][j][0] = 0
            }
        }
    }
    fun generateZeroAuxMatrix( //формирование чистой дополнительной матрицы
        matrix: Array<IntArray>,
        auxMatrix: Array<Array<IntArray>>
    ) {
        for (i in 1..9) {
            for (j in 1..9) {
                if (matrix[i][j] != 0) {
                    auxMatrix[i][j][0] = matrix[i][j]
                } else if (auxMatrix[i][j][0] == 0) {
                    auxMatrix[i][j][0] = -1
                    for (k in 1..9) {
                        auxMatrix[i][j][k] = k
                    }
                }
            }
        }
    }
    private fun getSolvedMatrix( //перетасовка матрицы (получение новой полной не решенной матрицы)
        basicMatrix: Array<Array<Int>>,
        solvedMatrix: Array<Array<Int>>
    ) {
        copyMatrix(basicMatrix, solvedMatrix)
        val r = Random()
        val counter = r.nextInt(1000)
        for (i in 0 until counter) {
            val variant = r.nextInt(4)
            when (variant) {
                0 -> matrixLineExchange(solvedMatrix)
                1 -> matrixColumnExchange(solvedMatrix)
                2 -> matrixTrippleLineExchange(solvedMatrix)
                3 -> matrixTripleColumnExchange(solvedMatrix)
            }
        }
    }
    private fun matrixLineExchange(BaseMatrix: Array<Array<Int>>) {
        //перестановка двух случайных строк в матрице судоку
        val r = Random()
        val numberLine = r.nextInt(3)
        val numberGroupLine = r.nextInt(3)
        var temp: Int
        for (i in 1..9) {
            if (numberLine == 0) {
                temp = BaseMatrix[1 + numberGroupLine * 3][i]
                BaseMatrix[1 + numberGroupLine * 3][i] =
                    BaseMatrix[2 + numberGroupLine * 3][i]
                BaseMatrix[2 + numberGroupLine * 3][i] = temp
            }
            if (numberLine == 1) {
                temp = BaseMatrix[2 + numberGroupLine * 3][i]
                BaseMatrix[2 + numberGroupLine * 3][i] =
                    BaseMatrix[3 + numberGroupLine * 3][i]
                BaseMatrix[3 + numberGroupLine * 3][i] = temp
            }
            if (numberLine == 2) {
                temp = BaseMatrix[1 + numberGroupLine * 3][i]
                BaseMatrix[1 + numberGroupLine * 3][i] =
                    BaseMatrix[3 + numberGroupLine * 3][i]
                BaseMatrix[3 + numberGroupLine * 3][i] = temp
            }
        }
    }
    private fun matrixColumnExchange(BaseMatrix: Array<Array<Int>>) {
        //перестановка двух случайных столбцов в матрице судоку
        val r = Random()
        val numberColumn = r.nextInt(3)
        val numberGroupColumn = r.nextInt(3)
        var temp: Int
        for (i in 1..9) {
            if (numberColumn == 0) {
                temp = BaseMatrix[i][1 + numberGroupColumn * 3]
                BaseMatrix[i][1 + numberGroupColumn * 3] =
                    BaseMatrix[i][2 + numberGroupColumn * 3]
                BaseMatrix[i][2 + numberGroupColumn * 3] = temp
            }
            if (numberColumn == 1) {
                temp = BaseMatrix[i][2 + numberGroupColumn * 3]
                BaseMatrix[i][2 + numberGroupColumn * 3] =
                    BaseMatrix[i][3 + numberGroupColumn * 3]
                BaseMatrix[i][3 + numberGroupColumn * 3] = temp
            }
            if (numberColumn == 2) {
                temp = BaseMatrix[i][1 + numberGroupColumn * 3]
                BaseMatrix[i][1 + numberGroupColumn * 3] =
                    BaseMatrix[i][3 + numberGroupColumn * 3]
                BaseMatrix[i][3 + numberGroupColumn * 3] = temp
            }
        }
    }
    private fun matrixTripleColumnExchange(BaseMatrix: Array<Array<Int>>) {
        //перестановка двух случайных столбцов в матрице судоку
        val r = Random()
        val numberTrippleColumn = r.nextInt(3)
        var temp: Int
        for (i in 1..9) {
            if (numberTrippleColumn == 0) {
                for (j in 1..3) {
                    temp = BaseMatrix[i][j]
                    BaseMatrix[i][j] = BaseMatrix[i][j + 3]
                    BaseMatrix[i][j + 3] = temp
                }
            }
            if (numberTrippleColumn == 1) {
                for (j in 1..3) {
                    temp = BaseMatrix[i][j + 3]
                    BaseMatrix[i][j + 3] = BaseMatrix[i][j + 6]
                    BaseMatrix[i][j + 6] = temp
                }
            }
            if (numberTrippleColumn == 2) {
                for (j in 1..3) {
                    temp = BaseMatrix[i][j]
                    BaseMatrix[i][j] = BaseMatrix[i][j + 6]
                    BaseMatrix[i][j + 6] = temp
                }
            }
        }
    }
    private fun matrixTrippleLineExchange(BaseMatrix: Array<Array <Int>>) {
        //перестановка двух случайных столбцов в матрице судоку
        val r = Random()
        val numberTrippleLine = r.nextInt(3)
        var temp: Int
        for (i in 1..9) {
            if (numberTrippleLine == 0) {
                for (j in 1..3) {
                    temp = BaseMatrix[j][i]
                    BaseMatrix[j][i] = BaseMatrix[j + 3][i]
                    BaseMatrix[j + 3][i] = temp
                }
            }
            if (numberTrippleLine == 1) {
                for (j in 1..3) {
                    temp = BaseMatrix[j + 3][i]
                    BaseMatrix[j + 3][i] = BaseMatrix[j + 6][i]
                    BaseMatrix[j + 6][i] = temp
                }
            }
            if (numberTrippleLine == 2) {
                for (j in 1..3) {
                    temp = BaseMatrix[j][i]
                    BaseMatrix[j][i] = BaseMatrix[j + 6][i]
                    BaseMatrix[j + 6][i] = temp
                }
            }
        }
    }
   class Element {
        //класс для одного элемента матрицы с координатами (для метода создания)
        var i = 0
        var j = 0
        var element = 0

    }
    fun getNonSolvedMatrix( //удаление лишних элементов из перетасованной матрицы
        basicMatrix: Array<Array<Int>>,
        solvedMatrix: Array<Array<Int>>,
        nonSolvedMatrix: Array<Array<Int>>
    ) {
        // 30-35 элементов - легко - 51 - 46 удалений
        // 25-30 элементов - средне -56 - 51 удалений
        // 20-25 элементов - сложно - 61 - 56 удалений
        getSolvedMatrix(basicMatrix, solvedMatrix)
        val elements: MutableList<Element> =
            ArrayList(100)
        var counter = 0
        //получаем линейное представление матрицы в виде списка
        for (i in 1..9) {
            for (j in 1..9) {
                val element = Element()
                element.i = i
                element.j = j
                element.element = solvedMatrix[i][j]
                elements.add(element)
                counter++
            }
        }
        //пытаемся удалить лишние эл-ты и получить матрицу для решения
        val r = Random()
        val size = elements.size
        //Перемешивем случайным образом матрицу (линейный список)
        for (i in 0..999) {
            var element1: Int
            var element2: Int
            var temp1 = Element()
            var temp2 = Element()
            element1 = r.nextInt(size)
            element2 = r.nextInt(size)
            temp1 = elements[element1]
            temp2 = elements[element2]
            elements[element1] = temp2
            elements[element2] = temp1
        }
        val tempMatrix = Array(10) { Array(10) {0} }
        val tempAuxMatrix = Array(10) { Array(10) { Array(10) {0} } }
        //получаем временную матрицу
        copyMatrix(solvedMatrix, tempMatrix)
        //пытаемся удалить максимум элементов из временной матрицы
        copyMatrix(solvedMatrix, tempMatrix)
        val elementsSize = elements.size
        counter = 0
        var maxDeletes = 0
        when (difficulty) {
            1 -> maxDeletes = 46 + r.nextInt(6)
            2 -> maxDeletes = 51 + r.nextInt(6)
            3 -> maxDeletes = 81
        }
        for (k in elements.indices) {
            tempMatrix[elements[k].i][elements[k].j] = 0
            for (i1 in 0..9) {
                for (j1 in 0..9) {
                    for (k1 in 0..9) {
                        tempAuxMatrix[i1][j1][k1] = 0
                    }
                }
            }
            auxMatrixGenerate(tempMatrix, tempAuxMatrix)
            auxMatrixProcessAll(tempAuxMatrix)
            if (winnerCheck(
                    tempAuxMatrix,
                    solvedMatrix
                ) && counter <= maxDeletes
            ) {
                tempMatrix[elements[k].i][elements[k].j] = 0
                counter++
            } else tempMatrix[elements[k].i][elements[k].j] =
                elements[k].element
        }
        Log.d(tag, "counter=$counter")
        //возвращаемся к двумерной матрице
        copyMatrix(tempMatrix, nonSolvedMatrix)
    }
    private fun winnerCheck(
        auxMatrix: Array<Array<Array<Int>>>,
        solvedMatrix: Array<Array<Int>>
    ): Boolean {
        for (i in 1..9) {
            for (j in 1..9) {
                if (auxMatrix[i][j][0] != solvedMatrix[i][j]) {
                    return false
                }
            }
        }
        return true
    }

    fun checkForEndGame(auxMatrix: Array<Array<IntArray>>): Boolean {
        for (i in 1..9) {
            for (j in 1..9) {
                if (auxMatrix[i][j][0] <= 0) return false
            }
        }
        return true
    }
    fun elementAuxMatrixReset(
        iMatrix: Int,
        jMatrix: Int,
        auxMatrix: Array<Array<IntArray>>
    ) {
        for (i in 1..9) {
            auxMatrix[iMatrix][jMatrix][i] = 0
        }
    }
    fun removeButtonNumberFromAuxMatrix(
        iMatrix: Int,
        jMatrix: Int,
        buttonNumber: Int,
        auxMatrix: Array<Array<IntArray>>
    ) {
        for (i in 1..9) {
            if (auxMatrix[i][jMatrix][buttonNumber] == buttonNumber) {
                auxMatrix[i][jMatrix][buttonNumber] = 0 //убрать элемент из строки
            }
            if (auxMatrix[iMatrix][i][buttonNumber] == buttonNumber) auxMatrix[iMatrix][i][buttonNumber] =
                0 //убрать элемент из столбца
        }
        //убрать элемент из квадрата
        //было удаление лишних элементов в квадрате
        //сначала нужно определить квадрат
        var i1 = 0
        var j1 = 0
        i1 = if (iMatrix <= 3) 1 else if (iMatrix <= 6) 4 else 7
        j1 = if (jMatrix <= 3) 1 else if (jMatrix <= 6) 4 else 7
        //удалить элемент из квадрата
        for (i in i1..i1 + 2) {
            for (j in j1..j1 + 2) {
                if (auxMatrix[i][j][buttonNumber] == buttonNumber) auxMatrix[i][j][buttonNumber] = 0
            }
        }
    }
    fun checkAuxMatrixForLastElement(
        iMatrix: Int,
        jMatrix: Int,
        auxMatrix: Array<Array<IntArray>>
    ): Int {
        var counter = 0
        var element = 0
        for (k in 1..9) {
            if (auxMatrix[iMatrix][jMatrix][k] > 0) {
                counter++
                element = auxMatrix[iMatrix][jMatrix][k]
            }
        }
        return if (counter == 1) element else 0
    }

    private fun toString(matrix: Array<Array<Int>>): String {
        var stringMatrix = ""
        for (i in 0..9) {
            for (j in 0..9) {
                stringMatrix += matrix[i][j].toString()
            }
        }
        return stringMatrix
    }
    fun fromString(
        stringMatrix: String,
        Matrix: Array<IntArray>
    ) {
        var counter = 0
        for (i in 0..9) {
            for (j in 0..9) {
                val ch = stringMatrix[counter]
                if (ch == '-') {
                    Matrix[i][j] = -1
                    counter++
                } else {
                    Matrix[i][j] = Character.getNumericValue(ch)
                }
                counter++
            }
        }
        Log.d(tag,"matrix taken from string = $stringMatrix")
    }

    fun fromString(
        stringMatrix: String,
        auxMatrix: Array<Array<IntArray>>
    ) {
        var counter = 0
        for (i in 0..9) {
            for (j in 0..9) {
                for (k in 0..9) {
                    val ch = stringMatrix[counter]
                    if (ch == '-') {
                        auxMatrix[i][j][k] = -1
                        counter++
                    } else {
                        auxMatrix[i][j][k] = Character.getNumericValue(ch)
                    }
                    counter++
                }
            }
        }
        Log.d(tag,"matrix taken from string = $stringMatrix")
    }
    fun checkAllElementsSolved(auxMatrix: Array<Array<IntArray>>): Boolean {
        for (i in 0..9) {
            for (j in 0..9) {
                if (auxMatrix[i][j][0] <= 0) return false
            }
        }
        return true
    }

    companion object {
        private fun toString(auxMatrix: Array<Array<Array<Int>>>): String {
            var stringMatrix = ""
            for (i in 0..9) {
                for (j in 0..9) {
                    for (k in 0..9) {
                        stringMatrix += auxMatrix[i][j][k].toString()
                    }
                }
            }
            return stringMatrix
        }
    }
}