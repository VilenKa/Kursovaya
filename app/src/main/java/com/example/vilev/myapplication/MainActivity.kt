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
        var port = 1089       // порт
        var msg: String = ""               // Сообщение
        val codeConnect: Byte = 0
        val codeHello: Byte = 51
        var codeCommand: Byte = 0
    }

    lateinit var ipAddress: InetAddress
    lateinit var mClient: SocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout1)
        val sndBtn = findViewById<Button>(R.id.btnSend)
        val connectBtn  = findViewById<Button>(R.id.btnConnect)

        btnHello.setOnClickListener{
            Heloo()
        }
        connectBtn.setOnClickListener{
            onConnectClick()
        }

        sndBtn.setOnClickListener() {
            onClick()
        }

    }

    fun goToGameScreen(){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun Heloo(){

        var ref  = mClient.connect()
        ref.subscribe(object : SocketSubscriber(){
            override fun onConnected() {
                var b = ByteArray(8096)
                b[0] = codeHello
                mClient.sendData(b)
            }

            override fun onDisconnected() {
                print("h")
            }

            override fun onResponse(data: ByteArray) {
//                launch(UI) { Toast.makeText(applicationContext, "HI!", Toast.LENGTH_LONG).show() }
                launch(UI) { goToGameScreen() }
            }
        }
        )
    }

    fun onConnectClick() = runBlocking {
        async{
            val etIPaddress = findViewById<View>(R.id.etIp) as EditText
            ipd = etIPaddress.text.toString() // ip адрес сервера
            ipAddress = InetAddress.getByName(ipd)
        }
        mClient = RxSocketClient
                .create(SocketConfig.Builder()
                        .setIp(ipd)
                        .setPort(port)
                        .setCharset(Charsets.UTF_8)
                        .setThreadStrategy(ThreadStrategy.ASYNC)
                        .setTimeout(30 * 1000)
                        .build())

    }

    fun onClick() {
    msg = etMessage.text.toString()
    launch() { sendCode( codeConnect ) }
}

    suspend fun sendCode(codeX: Byte){
        try {
            val socket = Socket(ipAddress, port)
            val outputStream = socket.getOutputStream()
            val dataOutputStream = DataOutputStream(outputStream)
                        dataOutputStream.write(codeX.toInt())
                        // Устанавливаем кодировку символов UTF-8
                        val outMsg = msg?.toByteArray()
                        dataOutputStream.write(outMsg!!)
            } catch (ex: Throwable) {
                ex.printStackTrace()
                println("other $ex")
            }

    }

}

