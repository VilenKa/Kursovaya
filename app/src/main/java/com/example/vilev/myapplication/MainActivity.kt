package com.example.vilev.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket
import android.widget.Toast
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.android.synthetic.main.layout1.*
import kotlinx.coroutines.experimental.android.UI
import moe.codeest.rxsocketclient.meta.ThreadStrategy
import kotlin.text.Charsets
import moe.codeest.rxsocketclient.meta.SocketConfig
import moe.codeest.rxsocketclient.RxSocketClient
import moe.codeest.rxsocketclient.SocketClient
import moe.codeest.rxsocketclient.SocketSubscriber


var ipd = ""

class MainActivity : AppCompatActivity() {
    companion object {
        var serIpAddress: String? = null       // адрес сервера
        var msg: String = ""               // Сообщение
        val codeConnect: Byte = 0
        val codeHello: Byte = 51
        var codeCommand: Byte = 0
    }

    lateinit var ipAddress: InetAddress
    //    lateinit var mClient: SocketClient
    var netStuff = NetStuff()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout1)

        btnHello.setOnClickListener {
            onConnectClick()
            Heloo()
        }


    }

    fun goToSelectGameScreen() {
        val intent = Intent(this, SelectGameActivity::class.java)
        startActivity(intent)
    }

    fun Heloo() {
        netStuff.createSocketClient(ipd)
        var ref = netStuff.connect()
        ref.subscribe(object : SocketSubscriber() {
            override fun onConnected() {
                netStuff.sendCode(codeHello)
            }

            override fun onDisconnected() {
                print("h")
            }

            override fun onResponse(data: ByteArray) {
                launch(UI) { goToSelectGameScreen() }
                netStuff.disconect()
            }
        }
        )
    }

    fun onConnectClick() = runBlocking {
        async {
            val etIPaddress = findViewById<View>(R.id.etIp) as EditText
            ipd = etIPaddress.text.toString() // ip адрес сервера
            ipAddress = InetAddress.getByName(ipd)
        }
    }

    fun onClick() {
        netStuff.createSocketClient(ipd)
        var ref = netStuff.connect()
        ref.subscribe(object : SocketSubscriber() {
            override fun onConnected() {
                netStuff.sendCode(codeConnect)
            }

            override fun onDisconnected() {
                print("h")
            }

            override fun onResponse(data: ByteArray) {
//                netStuff.disconect()
            }
        }
        )


    }
}

