package com.jtn.transitmobile.Login.View

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jtn.transitmobile.Home.View.HomeActivity
import com.jtn.transitmobile.Login.Contract.LoginContract
import com.jtn.transitmobile.Login.Model.LoginModel
import com.jtn.transitmobile.Login.Presenter.LoginPresenter
import com.jtn.transitmobile.R
import com.jtn.transitmobile.Register.View.RegisterActivity

class MainActivity : AppCompatActivity(),LoginContract.View {

    lateinit var txtCorreo: EditText
    lateinit var txtContraseña: EditText
    lateinit var btnMenuRegistro: Button
    lateinit var btnLogin: Button
    lateinit var barra_progres: ProgressBar
    lateinit var dialog: Dialog
    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        presenter = LoginPresenter(this, LoginModel())
        dialog = Dialog(this)
        initComponent()

    }

    fun initComponent(){
        initProgressBar()
        initInputCorreo()
        initInputContraseña()
        initButtonMenuRegistro()
        initButtonLogin()
    }
    fun showPopupError(){
        val dialogview = LayoutInflater.from(this)
            .inflate(R.layout.error_modal,null,false)
        dialog?.setCancelable(true)
        dialog?.setContentView(dialogview)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val lbl_error = dialog.findViewById(R.id.lblError) as TextView
        lbl_error.text = "Error al iniciar sesion!"
        lbl_error.gravity = Gravity.CENTER_VERTICAL

        val lbl_error_desc = dialog.findViewById(R.id.lblError_descripcion) as TextView
        lbl_error_desc.text = "Hubo un error al iniciar sesion, intente nuevamente"
        lbl_error_desc.gravity = Gravity.CENTER_VERTICAL

        val layoutParams = lbl_error_desc.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.leftMargin = 50 // Reemplaza 20 con el valor que desees
        lbl_error_desc.layoutParams = layoutParams

        val btn_aceptar = dialog.findViewById(R.id.btn_aceptarE) as Button
        btn_aceptar.setOnClickListener {
            dialog.dismiss()
        }

        val image_cerrar= dialog.findViewById(R.id.image_cerrarE) as ImageView
        image_cerrar.setOnClickListener {
            dialog.dismiss()
        }
        dialog?.show()
    }

    fun showPopupAlert(){
        val dialogview = LayoutInflater.from(this)
            .inflate(R.layout.warning_modal,null,false)
        dialog?.setCancelable(true)
        dialog?.setContentView(dialogview)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val btn_aceptar = dialog.findViewById(R.id.btn_aceptarAlerta) as Button
        btn_aceptar.setOnClickListener {
            dialog.dismiss()
        }

        val image_cerrar= dialog.findViewById(R.id.image_cerrar_alerta) as ImageView
        image_cerrar.setOnClickListener {
            dialog.dismiss()
        }
        dialog?.show()
    }

    fun initProgressBar(){
        barra_progres = findViewById(R.id.barra_progresoL)
    }
    fun initInputCorreo(){
        txtCorreo = findViewById(R.id.txtCorreo)
    }
    fun initInputContraseña(){
        txtContraseña = findViewById(R.id.txtContrasena)
    }
    fun initButtonLogin(){
        btnLogin =findViewById(R.id.btn_iniciar)
        btnLogin.setOnClickListener {
            val correo = txtCorreo.text.toString()
            val contrasena = txtContraseña.text.toString()

            if (correo.isNotEmpty() && contrasena.isNotEmpty()) {
                barra_progres.visibility = View.VISIBLE
                presenter.EnviaUsuario(correo,contrasena)
            }else{
                barra_progres.visibility = View.GONE
                showPopupAlert()
            }


        }
    }
    fun initButtonMenuRegistro(){
        btnMenuRegistro =findViewById(R.id.btn_menu_registro)
        btnMenuRegistro.setOnClickListener {
            val intentRegistro = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegistro)
        }
    }

    override fun LoginExito(userEmail: String, nombre: String, apellido: String) {
        barra_progres.visibility = View.GONE
        Toast.makeText(this, "exito", Toast.LENGTH_SHORT).show()
        val intentHome = Intent(this, HomeActivity::class.java)
        intentHome.putExtra("userEmail", userEmail)
        intentHome.putExtra("nombre", nombre)
        intentHome.putExtra("apellido", apellido)
        startActivity(intentHome)
    }

    override fun LoginError(mensaje: String) {
        barra_progres.visibility = View.GONE
        showPopupError()
    }
}