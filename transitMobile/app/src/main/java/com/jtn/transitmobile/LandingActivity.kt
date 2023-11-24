package com.jtn.transitmobile

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import com.jtn.transitmobile.Login.View.MainActivity
import com.jtn.transitmobile.Register.View.RegisterActivity

class LandingActivity : AppCompatActivity() {
    lateinit var btnCuenta: Button
    lateinit var btnUnirme: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        initComponents()
        setintents()
    }
    fun initComponents(){
        btnCuenta = findViewById(R.id.btn_empadrondado)
        btnUnirme = findViewById(R.id.btn_persona)
    }
    fun setintents(){
        btnCuenta.setOnClickListener {
            val intentLogin = Intent(this, MainActivity::class.java)
            startActivity(intentLogin)
            finish()
        }
        btnUnirme.setOnClickListener {
            val intentRegistro = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegistro)
            finish()
        }
    }
}