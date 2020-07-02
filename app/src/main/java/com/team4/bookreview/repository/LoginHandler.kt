package com.team4.bookreview.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler

class LoginHandler(val context: Context, val module : OAuthLogin, val preferences: SharedPreferences) : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if(success){
                val accessToken = module.getAccessToken(context)
                val refreshToken = module.getRefreshToken(context)
                val expiresAt = module.getExpiresAt(context)
                Log.e("Access Token", module.getAccessToken(context))
                Log.e("Refresh Token", module.getRefreshToken(context))
                Log.e("Expires At", expiresAt.toString())
                preferences.edit().putString("ACCESS_TOKEN", accessToken).apply()
                preferences.edit().putString("REFRESH_TOKEN", refreshToken).apply()
            }
            else{
                val errorCode: String =
                    module.getLastErrorCode(context).getCode()
                val errorDesc: String = module.getLastErrorDesc(context)
                Toast.makeText(
                    context, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                ).show()
            }
        }
}