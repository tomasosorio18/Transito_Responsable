package com.jtn.transitmobile.Home.View

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jtn.transitmobile.R

class HomeActivity : AppCompatActivity() {
    lateinit var lblNombre :TextView
    lateinit var lblApellido :TextView
    lateinit var lblCorreo :TextView
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        dialog = Dialog(this)
        initTextView()
        setTextIntent()
    }

    fun initTextView() {
        lblNombre = findViewById(R.id.lblNombreU)
        lblApellido = findViewById(R.id.lblApellidoU)
        lblCorreo= findViewById(R.id.lblCorreoU)
    }
    fun setTextIntent(){
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val userEmail = intent.getStringExtra("userEmail")

        if (nombre != null && apellido != null && userEmail != null) {
            lblNombre.text = nombre
            lblApellido.text = apellido
            lblCorreo.text = userEmail
        } else {
            // Manejar el caso en que los extras no están presentes
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
            finish()
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