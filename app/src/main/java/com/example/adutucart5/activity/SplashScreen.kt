package com.example.adutucart5.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adutucart5.R
import com.example.adutucart5.loginRegister.LoginRegisterMainActivity
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar!!.hide()

        CoroutineScope(Dispatchers.IO).launch {


            CoroutineScope(Dispatchers.Main).launch {

                withContext(Dispatchers.IO) {
                    delay(6000)
                }

                startActivity(Intent(this@SplashScreen, LoginRegisterMainActivity::class.java))
                finish()
            }

        }


    }


}