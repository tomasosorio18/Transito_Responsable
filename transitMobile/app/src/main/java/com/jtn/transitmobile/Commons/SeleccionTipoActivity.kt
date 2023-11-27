package com.jtn.transitmobile.Commons

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.jtn.transitmobile.Login.View.MainActivity
import com.jtn.transitmobile.R
import com.jtn.transitmobile.SacarPartes.View.SacarPartesActivity
import com.jtn.transitmobile.SacarPartesEmpadronado.View.SacarParteEmpadronadoActivity

class SeleccionTipoActivity : AppCompatActivity() {
    lateinit var dialog: Dialog
    lateinit var btnpersona: Button
    lateinit var btnempadrondado: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_tipo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        dialog = Dialog(this)
        initComponents()
        setintents()
    }
    fun initComponents(){
        btnempadrondado = findViewById(R.id.btn_empadrondado)
        btnpersona = findViewById(R.id.btn_persona)
    }
    fun setintents(){
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val userEmail = intent.getStringExtra("userEmail")

        btnpersona.setOnClickListener {
            startActivity(Intent(this, SacarPartesActivity::class.java).apply {
                putExtras(getUserBundle(nombre, apellido, userEmail))
            })
            finish()
        }
        btnempadrondado.setOnClickListener {
            startActivity(Intent(this, SacarParteEmpadronadoActivity::class.java).apply {
                putExtras(getUserBundle(nombre, apellido, userEmail))
            })
            finish()
        }
    }
    private fun getUserBundle(nombre: String?, apellido: String?, userEmail: String?): Bundle {
        return Bundle().apply {
            putString("nombre", nombre)
            putString("apellido", apellido)
            putString("userEmail", userEmail)
        }
    }
    fun showPopupLogout() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.cerrar_sesion)
        dialog?.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnExit = dialog.findViewById<Button>(R.id.btn_aceptarlogout)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancelar)
        val image_cerrar= dialog.findViewById<ImageView>(R.id.image_cerrar_logout)

        btnExit.setOnClickListener {
            // Cierra la actividad al hacer clic en el botón "Salir"
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        image_cerrar.setOnClickListener {
            // Cierra la actividad al hacer clic en el botón "Salir"
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            // No hagas nada o realiza alguna acción adicional al hacer clic en "Cancelar"
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

        // Llama al método que muestra el modal
        showPopupLogout()

    }
}