package com.ccc.remind.data.source.socket

import android.util.Log
import com.ccc.remind.data.util.LocalDataTypeConverter
import com.ccc.remind.domain.entity.user.JwtToken
import com.google.gson.GsonBuilder
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.URI
import java.time.ZonedDateTime

class SocketManager {
    private lateinit var socket: Socket
    private val gson =
        GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(ZonedDateTime::class.java, LocalDataTypeConverter()) // ZonedDateTime converter
            .create() // include null value
    private val eventsFlows = mutableMapOf<String, MutableSharedFlow<Any>>()
    private var token: String? = null
    private lateinit var host: String

    companion object {
        private const val TAG = "SocketManager"
    }

    fun socketConnect(host: String, token: Flow<JwtToken?>) {
        CoroutineScope(Dispatchers.Default).launch {
            token.collect { jwt ->
                this@SocketManager.token = jwt?.accessToken
                this@SocketManager.host = host
                socketConnect()
            }
        }
    }

    private fun initSocket() {
        val options = IO.Options.builder()
            .setAuth(mapOf("authorization" to "Bearer $token"))
            .setReconnection(true)
            .setReconnectionDelay(5000)
            .setReconnectionAttempts(5)
            .build()

        socket = IO.socket(
            URI.create("${host}:1234"),
            options
        )
    }

    private fun socketConnect() {
        if (!this::socket.isInitialized) {
            initSocket()
        }
        if (!socket.connected()) {
            socket.connect()
        }
    }

    fun <T> listen(event: String, classOfT: Class<T>, scope: CoroutineScope): SharedFlow<T> {
        val flow = eventsFlows.getOrPut(event) {
            val newFlow = MutableSharedFlow<Any>()
            val listener = Emitter.Listener { args ->
                scope.launch {
                    try {
                        val data: T? =
                            if (args[0] is JSONObject)
                                gson.fromJson((args[0] as JSONObject).toString(), classOfT)
                            else if (args[0] is JSONArray)
                                gson.fromJson((args[0] as JSONArray).toString(), classOfT)
                            else
                                null

                        if (data != null) {
                            Log.d(TAG, "listen - data emit: ${data}")
                            newFlow.emit(data as Any)
                        } else {
                            Log.d(TAG, "listen - data: null")
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "listen - error: ${e}")
                        e.printStackTrace()
                    }
                }
            }

            // Here you might want to use the scope as well for launching coroutines
            scope.launch {
                while (!this@SocketManager::socket.isInitialized || !socket.connected()) {
                    delay(100) // wait for 100ms before checking again
                }
                socket.on(event, listener)
            }
            newFlow
        }

        @Suppress("UNCHECKED_CAST")
        return flow as SharedFlow<T>
    }
}