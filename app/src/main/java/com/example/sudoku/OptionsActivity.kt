package com.example.sudoku

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sudoku.data.File

class OptionsActivity : AppCompatActivity(), View.OnClickListener {
    private var difficulty = 3
    private lateinit var button: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var textView: TextView
    private var file = File()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        val res = resources
        button = findViewById<View>(R.id.button) as Button
        button.setOnClickListener(this)
        button1 = findViewById<View>(R.id.button1) as Button
        button1.setOnClickListener(this)
        button2 = findViewById<View>(R.id.button2) as Button
        button2.setOnClickListener(this)
        button3 = findViewById<View>(R.id.button3) as Button
        button3.setOnClickListener(this)
        textView = findViewById<View>(R.id.textView83) as TextView
        textView.text = res.getString(R.string.Difficulty) + " - " + res.getString(R.string.Diff3)
    }

    override fun onClick(view: View) {
        var intent: Intent
        val anim = AnimationUtils.loadAnimation(this, R.anim.translate)
        val res = resources
        when (view.id) {
            R.id.button -> {
                button.startAnimation(anim)
                intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.button1 -> {
                button1.startAnimation(anim)
                textView.text =
                    res.getString(R.string.Difficulty) + " - " + res.getString(R.string.Diff1)
                difficulty = 1
                file.saveFile(this, "difficulty", difficulty.toString())
            }
            R.id.button2 -> {
                button2.startAnimation(anim)
                textView.text =
                    res.getString(R.string.Difficulty) + " - " + res.getString(R.string.Diff2)
                difficulty = 2
                file.saveFile(this, "difficulty", difficulty.toString())
            }
            R.id.button3 -> {
                button3.startAnimation(anim)
                textView.text =
                    res.getString(R.string.Difficulty) + " - " + res.getString(R.string.Diff3)
                difficulty = 3
                file.saveFile(this, "difficulty", difficulty.toString())
            }
        }
    }

    override fun onKeyDown(keyKode: Int, event: KeyEvent): Boolean {
        if (keyKode == KeyEvent.KEYCODE_BACK) {
        }
        return true
    }
}
