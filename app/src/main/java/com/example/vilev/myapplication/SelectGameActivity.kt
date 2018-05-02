package com.example.vilev.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.select_game_layout.*

class SelectGameActivity: AppCompatActivity() {

    companion object {
        val codeGame1: Byte = 1
        val codeGame2: Byte = 2
        val codeGame3: Byte = 3
        val codeGame4: Byte = 4
        val codeGame5: Byte = 5
        val codeGame6: Byte = 6
        val codeGame7: Byte = 7
        val codeGame8: Byte = 8
        val codeGame9: Byte = 9
        val codeGame10: Byte = 10
        val codeGame11: Byte = 11
        val codeGame12: Byte = 12
        val codeGame13: Byte = 13
        val codeGame14: Byte = 14
        val codeGame15: Byte = 15
        val codeGame16: Byte = 16
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Game1.setOnClickListener { sendGameCode(codeGame1) }
        Game2.setOnClickListener { sendGameCode(codeGame2) }
        Game3.setOnClickListener { sendGameCode(codeGame3) }
        Game4.setOnClickListener { sendGameCode(codeGame4) }
        Game5.setOnClickListener { sendGameCode(codeGame5) }
        Game6.setOnClickListener { sendGameCode(codeGame6) }
        Game7.setOnClickListener { sendGameCode(codeGame7) }
        Game8.setOnClickListener { sendGameCode(codeGame8) }
        Game9.setOnClickListener { sendGameCode(codeGame9) }
        Game10.setOnClickListener { sendGameCode(codeGame10) }
        Game11.setOnClickListener { sendGameCode(codeGame11) }
        Game12.setOnClickListener { sendGameCode(codeGame12) }
        Game13.setOnClickListener { sendGameCode(codeGame13) }
        Game14.setOnClickListener { sendGameCode(codeGame14) }
        Game15.setOnClickListener { sendGameCode(codeGame15) }
        Game16.setOnClickListener { sendGameCode(codeGame16) }
    }

    fun sendGameCode(code: Byte){

    }

}