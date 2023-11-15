package com.jtn.transitmobile.Register.View

import android.app.Dialog
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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import com.jtn.transitmobile.R
import com.jtn.transitmobile.Register.Contract.RegisterContract
import com.jtn.transitmobile.Register.Model.RegisterModel
import com.jtn.transitmobile.Register.Presenter.RegisterPresenter

class RegisterActivity : AppCompatActivity(),RegisterContract.View {

    lateinit var txtNombre: EditText
    lateinit var txtApellido: EditText
    lateinit var txtCorreo: EditText
    lateinit var txtContraseña: EditText
    lateinit var btnRegistrar: Button
    lateinit var btnVolver: ImageButton
    lateinit var lblVolver: TextView
    lateinit var barra_progres: ProgressBar
    lateinit var dialog: Dialog
    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        presenter = RegisterPresenter(this, RegisterModel())
        dialog = Dialog(this)
        initComponent()



    }

    fun showPopupExito(){
        val dialogview = LayoutInflater.from(this)
            .inflate(R.layout.exito_modal,null,false)
        dialog?.setCancelable(true)
        dialog?.setContentView(dialogview)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn_aceptar = dialog.findViewById(R.id.btn_aceptarE) as Button
        btn_aceptar.setOnClickListener {
            dialog.dismiss()
        }

        val image_cerrar= dialog.findViewById(R.id.image_cerrar) as ImageView
        image_cerrar.setOnClickListener {
            dialog.dismiss()
        }
        dialog?.show()
    }
    fun showPopupError() {
        if (dialog != null) {
            val dialogview = LayoutInflater.from(this)
                .inflate(R.layout.error_modal, null, false)
            dialog?.setCancelable(true)
            dialog?.setContentView(dialogview)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val btn_aceptar = dialog.findViewById(R.id.btn_aceptarE) as Button
            btn_aceptar.setOnClickListener {
                dialog.dismiss()
            }

            val image_cerrar = dialog.findViewById(R.id.image_cerrarE) as ImageView
            image_cerrar.setOnClickListener {
                dialog.dismiss()
            }
            dialog?.show()
        } else {
            // Handle the case where dialog is null
        }
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

    fun initComponent(){
        initProgressBar()
        initInputNombre()
        initInputApellido()
        initInputCorreo()
        initInputContraseña()
        initButtonRegistrar()
        initButtonVolver()
        initlblVolver()

    }
    fun initProgressBar(){
        barra_progres = findViewById(R.id.barra_progresoR)
    }
    fun initInputNombre(){
        txtNombre = findViewById(R.id.txtNombreR)
    }
    fun initInputApellido(){
        txtApellido = findViewById(R.id.txtApellidoR)
    }
    fun initInputCorreo(){
        txtCorreo = findViewById(R.id.txtCorreoR)
    }
    fun initInputContraseña(){
        txtContraseña = findViewById(R.id.txtContrasenaR)
    }
    fun initlblVolver(){
       lblVolver = findViewById(R.id.lblVolver)
        lblVolver.setOnClickListener{ finish() }
    }


    fun initButtonVolver(){
        btnVolver=findViewById(R.id.btn_volver)
        btnVolver.setOnClickListener { finish() }
    }
    fun initButtonRegistrar(){
        btnRegistrar =findViewById(R.id.btn_registro)
        btnRegistrar.setOnClickListener {
            val correo = txtCorreo.text.toString()
            val contrasena = txtContraseña.text.toString()
            val nombre = txtNombre.text.toString()
            val apellido = txtApellido.text.toString()
            if (correo.isNotEmpty() && contrasena.isNotEmpty() && nombre.isNotEmpty() && apellido.isNotEmpty()) {
                // Llamar a Firebase para el registro aquí
                barra_progres.visibility = View.VISIBLE
                presenter.EnviaUsuario(correo, contrasena,nombre,apellido)
            } else {
                barra_progres.visibility = View.GONE
                showPopupAlert()
            }
        }


    }

    override fun RegistroExito() {
        barra_progres.visibility = View.GONE
        showPopupExito()
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
    }

    override fun RegistroError(mensaje: String) {
        barra_progres.visibility = View.GONE
        showPopupError()
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
    }
}