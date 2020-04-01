package com.example.sudoku

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity(), View.OnClickListener {
    private val tag = "myLog";
    private lateinit  var gameVM: GameViewModel;

    private lateinit var mainTableLayout: TableLayout;

    private lateinit var button: Button;
    private lateinit var button1: Button;
    private lateinit var button2: Button;
    private lateinit var button3: Button;
    private lateinit var button4: Button;
    private lateinit var button5: Button;
    private lateinit var button6: Button;
    private lateinit var button7: Button;
    private lateinit var button8: Button;
    private lateinit var button9: Button;
    private lateinit var button10: Button;
    private lateinit var button11: Button;
    private lateinit var button12: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameVM = GameViewModel(this,this)
        val anim = AnimationUtils.loadAnimation(this, R.anim.scale)
        val intent = intent
        var load: Boolean = false
        var booleanExtra = intent.getBooleanExtra("load", load)
        Log.d(tag,"load = $load booleanExtra = $booleanExtra")
        if (booleanExtra) gameVM.loadQuickSave()

        button   = findViewById(R.id.button);   button.setOnClickListener(this);   button.startAnimation(anim)
        button1  = findViewById(R.id.button1);  button1.setOnClickListener(this);  button1.startAnimation(anim)
        button2  = findViewById(R.id.button2);  button2.setOnClickListener(this);  button2.startAnimation(anim)
        button3  = findViewById(R.id.button3);  button3.setOnClickListener(this);  button3.startAnimation(anim)
        button4  = findViewById(R.id.button4);  button4.setOnClickListener(this);  button4.startAnimation(anim)
        button5  = findViewById(R.id.button5);  button5.setOnClickListener(this);  button5.startAnimation(anim)
        button6  = findViewById(R.id.button6);  button6.setOnClickListener(this);  button6.startAnimation(anim)
        button7  = findViewById(R.id.button7);  button7.setOnClickListener(this);  button7.startAnimation(anim)
        button8  = findViewById(R.id.button8);  button8.setOnClickListener(this);  button8.startAnimation(anim)
        button9  = findViewById(R.id.button9);  button9.setOnClickListener(this);  button9.startAnimation(anim)
        button10 = findViewById(R.id.button10); button10.setOnClickListener(this); button10.startAnimation(anim)
        button11 = findViewById(R.id.button11); button11.setOnClickListener(this); button11.startAnimation(anim)
        button12 = findViewById(R.id.button12); button12.setOnClickListener(this); button12.startAnimation(anim)

        mainTableLayout = findViewById(R.id.mainTableLayout)
        gameVM.getMainTable(mainTableLayout)
        gameVM.screenUpdate()
        gameVM.setCheckedBold()
     }

    override fun onClick(v: View?) {
        if (v!==null){
            if (v.id in 0..80) gameVM.checkReset(v.id)
             when (v.id) {
                 //pen/pencil switcher
                 R.id.button  -> {gameVM.switchPen()}
                 //Fill auxTable with numbers
                 R.id.button11-> {gameVM.setZeroAuxTables(); gameVM.screenUpdate()}
                 //Exit to menu
                 R.id.button10-> {
                     gameVM.saveQuickSave()
                     startActivity(Intent(this, MenuActivity::class.java))
                     finish()
                 }
                 //Undo button
                 R.id.button12-> {gameVM.undo(); gameVM.screenUpdate()}
                 //Number buttons
                 R.id.button1 -> {gameVM.numberButtonPressed(1)}
                 R.id.button2 -> {gameVM.numberButtonPressed(2)}
                 R.id.button3 -> {gameVM.numberButtonPressed(3)}
                 R.id.button4 -> {gameVM.numberButtonPressed(4)}
                 R.id.button5 -> {gameVM.numberButtonPressed(5)}
                 R.id.button6 -> {gameVM.numberButtonPressed(6)}
                 R.id.button7 -> {gameVM.numberButtonPressed(7)}
                 R.id.button8 -> {gameVM.numberButtonPressed(8)}
                 R.id.button9 -> {gameVM.numberButtonPressed(9)}
            }
            Log.d(tag,"onClick, id = "+v.id)
        }
    }
    override fun onStart() {
        Log.d(tag, "onStart Game_Activity")
        super.onStart()
    }

    override fun onRestart() {
        Log.d(tag, "onRestart Game_Activity")
        gameVM.loadQuickSave()
        super.onRestart()
    }

    override fun onResume() {
        Log.d(tag, "onResume Game_Activity")
        super.onResume()
    }

    override fun onPause() {
        Log.d(tag, "onPause Game_Activity")
        gameVM.saveQuickSave()
        super.onPause()
    }

    override fun onStop() {
        Log.d(tag, "onStop  Game_Activity")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy  Game_Activity")
        gameVM.saveQuickSave()
        super.onDestroy()
    }
}
