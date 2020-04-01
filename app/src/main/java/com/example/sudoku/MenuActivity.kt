package com.example.sudoku

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity(), View.OnClickListener {
    private val tag = "myLogs"
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private var load = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val anim = AnimationUtils.loadAnimation(this, R.anim.alphaprozrachn)
        button1 = findViewById(R.id.button1)
        button1.setOnClickListener(this)
        button1.startAnimation(anim)
        button2 = findViewById<View>(R.id.button2) as Button
        button2.setOnClickListener(this)
        button2.startAnimation(anim)
        button3 = findViewById<View>(R.id.button3) as Button
        button3.setOnClickListener(this)
        button3.startAnimation(anim)
        button4 = findViewById<View>(R.id.button4) as Button
        button4.setOnClickListener(this)
        button4.startAnimation(anim)
        button5 = findViewById<View>(R.id.button5) as Button
        button5.setOnClickListener(this)
        button5.startAnimation(anim)
        button6 = findViewById<View>(R.id.button6) as Button
        button6.setOnClickListener(this)
        button6.startAnimation(anim)
    }

    override fun onClick(view: View) {
        var intent: Intent? = null
        val anim = AnimationUtils.loadAnimation(this, R.anim.translate)
        when (view.id) {
            //Start game
            R.id.button1 -> {
                button1.startAnimation(anim)
                load = false
                intent = Intent(this, GameActivity::class.java)
                intent.putExtra("load", load)
                startActivity(intent)
                finish()
            }
            //Load Game
            R.id.button2 -> {
                button2.startAnimation(anim)
                load = true
                intent = Intent(this, GameActivity::class.java)
                intent.putExtra("load", load)
                startActivity(intent)
                finish()
            }
            //Options
            R.id.button3 -> {
                button3.startAnimation(anim)
                intent = Intent(this, OptionsActivity::class.java)
                startActivity(intent)
                finish()
            }
            //About
            R.id.button4 -> {
                button4.startAnimation(anim)
             //   intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.button5 -> button5.startAnimation(anim)
            //Exit game
            R.id.button6 -> {
                button6.startAnimation(anim)
                finish()
            }
        }
    }

    override fun onStart() {
        Log.d(tag, "onStart Menu_Activity")
        super.onStart()
    }

    override fun onRestart() {
        Log.d(tag, "onRestart Menu_Activity")
        super.onRestart()
    }

    override fun onResume() {
        Log.d(tag, "onResume Menu_Activity")
        super.onResume()
    }

    override fun onPause() {
        Log.d(tag, "onPause Menu_Activity")
        super.onPause()
    }

    override fun onStop() {
        Log.d(tag, "onStop  Menu_Activity")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy  Menu_Activity")
        super.onDestroy()
    }
}