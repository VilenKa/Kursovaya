package com.example.vilev.myapplication

import moe.codeest.rxsocketclient.RxSocketClient
import moe.codeest.rxsocketclient.SocketClient
import moe.codeest.rxsocketclient.meta.DataWrapper
import moe.codeest.rxsocketclient.meta.SocketConfig
import moe.codeest.rxsocketclient.meta.ThreadStrategy
import java.util.*

class NetStuff {

    lateinit var mClient: SocketClient

    fun createSocketClient(ipd: String, port: Int){
        mClient = RxSocketClient
                .create(SocketConfig.Builder()
                        .setIp(ipd)
                        .setPort(port)
                        .setCharset(Charsets.UTF_8)
                        .setThreadStrategy(ThreadStrategy.ASYNC)
                        .setTimeout(30 * 1000)
                        .build())
    }

    fun sendCode(code: Byte){
        var b = ByteArray(8096)
        b[0] = code
        mClient.sendData(b)
    }

    fun connect() = mClient.connect()

}