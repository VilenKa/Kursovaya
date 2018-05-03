package com.example.vilev.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.select_game_layout.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import moe.codeest.rxsocketclient.SocketSubscriber

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

    var netStuff = NetStuff()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_game_layout)
        val Game1Btn = findViewById<Button>(R.id.Game1)
        val Game2Btn= findViewById<Button>(R.id.Game2)
        val Game3Btn= findViewById<Button>(R.id.Game3)
        val Game4Btn= findViewById<Button>(R.id.Game4)
        val Game5Btn= findViewById<Button>(R.id.Game5)
        val Game6Btn= findViewById<Button>(R.id.Game6)
        val Game7Btn= findViewById<Button>(R.id.Game7)
        val Game8Btn= findViewById<Button>(R.id.Game8)
        val Game9Btn= findViewById<Button>(R.id.Game9)
        val Game10Btn = findViewById<Button>(R.id.Game10)
        val Game11Btn = findViewById<Button>(R.id.Game11)
        val Game12Btn = findViewById<Button>(R.id.Game12)
        val Game13Btn = findViewById<Button>(R.id.Game13)
        val Game14Btn = findViewById<Button>(R.id.Game14)
        val Game15Btn = findViewById<Button>(R.id.Game15)
        val Game16Btn = findViewById<Button>(R.id.Game16)
       Game1Btn.setOnClickListener { sendGameCode(codeGame1) }
        Game2Btn.setOnClickListener { sendGameCode(codeGame2) }
        Game3Btn.setOnClickListener { sendGameCode(codeGame3) }
        Game4Btn.setOnClickListener { sendGameCode(codeGame4) }
        Game5Btn.setOnClickListener { sendGameCode(codeGame5) }
        Game6Btn.setOnClickListener { sendGameCode(codeGame6) }
        Game7Btn.setOnClickListener { sendGameCode(codeGame7) }
        Game8Btn.setOnClickListener { sendGameCode(codeGame8) }
        Game9Btn.setOnClickListener { sendGameCode(codeGame9) }
        Game10Btn.setOnClickListener { sendGameCode(codeGame10) }
        Game11Btn.setOnClickListener { sendGameCode(codeGame11) }
        Game12Btn.setOnClickListener { sendGameCode(codeGame12) }
        Game13Btn.setOnClickListener { sendGameCode(codeGame13) }
        Game14Btn.setOnClickListener { sendGameCode(codeGame14) }
        Game15Btn.setOnClickListener { sendGameCode(codeGame15) }
        Game16Btn.setOnClickListener { sendGameCode(codeGame16) }
    }

    fun goToGameScreen() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun sendGameCode(code: Byte){
        netStuff.createSocketClient(ipd)
        var ref = netStuff.connect()
        ref.subscribe(object : SocketSubscriber() {
            override fun onConnected() {
                netStuff.sendCode(code)
            }

            override fun onDisconnected() {
                print("h")
            }

            override fun onResponse(data: ByteArray) {
                launch(UI) { goToGameScreen() }
            }
        }
        )

    }

}