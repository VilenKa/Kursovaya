package com.example.vilev.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket
import android.widget.Toast
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.android.synthetic.main.layout1.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import java.io.DataInputStream
import java.nio.ByteBuffer
import moe.codeest.rxsocketclient.meta.ThreadStrategy
import kotlin.text.Charsets
import moe.codeest.rxsocketclient.meta.SocketConfig
import moe.codeest.rxsocketclient.RxSocketClient
import moe.codeest.rxsocketclient.SocketClient
import moe.codeest.rxsocketclient.SocketSubscriber
import java.net.SocketOption


var ipd = ""
class MainActivity : AppCompatActivity() {
    companion object {
        var serIpAddress: String? = null       // адрес сервера
        var port = 1089       // порт
        var msg: String = ""               // Сообщение
        val codeMsg: Byte = 1 // Оправить сообщение
        val codeHello: Byte = 3
        var codeCommand: Byte = 0
    }

    lateinit var ipAddress: InetAddress


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

    fun Heloo(){

//        launch{ listen() }
//                launch { netShit(codeHello) }

        val mClient = RxSocketClient
                .create(SocketConfig.Builder()
                        .setIp(ipd)
                        .setPort(port)
                        .setCharset(Charsets.UTF_8)
                        .setThreadStrategy(ThreadStrategy.ASYNC)
                        .setTimeout(30 * 1000)
                        .build())

        var ref  = mClient.connect()
        ref.subscribe(object : SocketSubscriber(){
            override fun onConnected() {
                mClient.sendData("3")
            }

            override fun onDisconnected() {
                print("h")
            }

            override fun onResponse(data: ByteArray) {
                launch(UI) { Toast.makeText(applicationContext, "HI!", Toast.LENGTH_LONG).show() }
            }

        }
        )

    }



//    suspend fun listen() {
//        val inputStream: DataInputStream
//        val socket = Socket(ipAddress, port)
//        inputStream = DataInputStream(socket.getInputStream())
//        while (true){
//           if(inputStream.read() > -1) {
//               val theBytes = ByteArray(inputStream.available())
//               inputStream.read(theBytes, 0, inputStream.available())
//               inputStream.close()
//               if (theBytes.toInt() == 42) {
//                   Toast.makeText(applicationContext, "HI!", Toast.LENGTH_LONG).show()
//               }
//           }
//        }
//    }


    fun onConnectClick() = runBlocking {
        async{
            val etIPaddress = findViewById<View>(R.id.etIp) as EditText
            ipd = etIPaddress.text.toString()
            // ip адрес сервера
            ipAddress = InetAddress.getByName(ipd)
//            val socket = Socket(ipAddress, port)
            // Создаем сокет



//                val socket = Socket(ipAddress, port)
            // Получаем потоки ввод/вывода


        }

    }






fun onClick() {
    msg = etMessage.text.toString()
    launch() { netShit( codeMsg ) }
}

    suspend fun netShit(codeX: Byte){
        try {
            val socket = Socket(ipAddress, port)
            val outputStream = socket.getOutputStream()
            val dataOutputStream = DataOutputStream(outputStream)
                when (codeX) {
                // В зависимости от кода команды посылаем сообщения
                    codeMsg    // Сообщение
                    -> {
                        dataOutputStream.write(codeMsg.toInt())
                        // Устанавливаем кодировку символов UTF-8
                        val outMsg = msg?.toByteArray()
                        dataOutputStream.write(outMsg!!)
                    }
                    codeHello ->{
                        dataOutputStream.write(codeHello.toInt())
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                println("other $ex")
            }

    }

//    fun ByteArray.toInt() : Int{
//        var num = -1
//        if(this.isNotEmpty()){
//            val wrapped = ByteBuffer.wrap(this) // big-endian by default
//            num = wrapped.getInt() // 1
//        }


//    val dbuf = ByteBuffer.allocate(2)
//    dbuf.putShort(num)
//    val bytes = dbuf.array() // { 0, 1 }
//        return num
//    }


}

