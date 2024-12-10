package com.example.suaranusa.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT

object jwtDecoder{
    fun decode(token: String): DecodedJWT{
        Log.d("jwtDecoder", "$token")
        return JWT.decode(token)
    }
    fun getKeyValue(token: String, key: String): String{
        return decode(token).getClaim(key).asString()
    }
}

class SessionManager(context:Context) {
   private val pref: SharedPreferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE)
    companion object{
        private const val TOKEN = "token"
    }

    fun setToken(token: String) {
        pref.edit().putString(TOKEN, token).apply()
    }
    fun getToken():String? = pref.getString(TOKEN, null)

    fun clearSession(){
        pref.edit().clear().apply()
    }
}