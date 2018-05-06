package com.example.vilev.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.game_layout.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import moe.codeest.rxsocketclient.SocketSubscriber

class GameActivity: AppCompatActivity() {

    companion object {
        val codeTask: Byte = 17
        val codeDifficulty: Byte = 18
        val codeBack: Byte = 19
    }

    var netStuff = NetStuff()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout)
        taskBtn.setOnClickListener { sendGameCode(codeTask) }
        difficultyBtn.setOnClickListener{ sendGameCode(codeDifficulty) }
        backBtn.setOnClickListener { onBackClick() }
    }

    fun showToast(){
        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
    }

    fun onBackClick(){
        netStuff.createSocketClient(ipd)
        var ref = netStuff.connect()
        ref.subscribe(object : SocketSubscriber() {
            override fun onConnected() {
                netStuff.sendCode(codeBack)
            }

            override fun onDisconnected() {
                print("h")
            }

            override fun onResponse(data: ByteArray) {
                launch(UI) { goBack() }
                netStuff.disconect()
            }
        }
        )
    }


    fun goBack(){
        val intent = Intent(this, SelectGameActivity::class.java)
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
                launch(UI) { showToast() }
                netStuff.disconect()
            }
        }
        )
    }
}