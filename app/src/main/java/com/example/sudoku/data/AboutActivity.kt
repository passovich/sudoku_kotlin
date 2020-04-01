package com.example.sudoku.data

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import com.example.sudoku.MenuActivity
import com.example.sudoku.R

class AboutActivity : Activity(), View.OnClickListener {
    private lateinit var button1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        button1 = findViewById<View>(R.id.button1) as Button
        button1.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        var intent: Intent? = null
        when (view.id) {
            R.id.button1 -> {
                intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onKeyDown(keyKode: Int, event: KeyEvent): Boolean {
        if (keyKode == KeyEvent.KEYCODE_BACK) {
        }
        return true
    }
}