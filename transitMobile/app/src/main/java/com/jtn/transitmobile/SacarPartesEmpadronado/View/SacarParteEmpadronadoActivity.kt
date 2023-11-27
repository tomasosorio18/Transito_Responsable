package com.jtn.transitmobile.SacarPartesEmpadronado.View

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jtn.transitmobile.Commons.BDServices
import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.Commons.Persona
import com.jtn.transitmobile.Home.View.HomeActivity
import com.jtn.transitmobile.Login.View.MainActivity
import com.jtn.transitmobile.R
import com.jtn.transitmobile.SacarPartes.Contract.SacarPartesContract
import com.jtn.transitmobile.SacarPartes.Model.SacarParteModel
import com.jtn.transitmobile.SacarPartes.Presenter.SacarPartePresenter
import com.jtn.transitmobile.SacarPartesEmpadronado.Contract.SacarParteEmpadronadoContract
import com.jtn.transitmobile.SacarPartesEmpadronado.Model.SacarParteEmpadronadoModel
import com.jtn.transitmobile.SacarPartesEmpadronado.Presenter.SacarParteEmpadronadoPresenter
import com.jtn.transitmobile.VerPartes.View.PartesActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class SacarParteEmpadronadoActivity : AppCompatActivity(), SacarParteEmpadronadoContract.View {
    private lateinit var presenter: SacarParteEmpadronadoContract.Presenter
    private lateinit var spinner: Spinner
    private lateinit var txtRut : EditText
    private lateinit var txtPatente: EditText
    private lateinit var txtNombreC: EditText
    private lateinit var txtApellidoP: EditText
    private lateinit var txtApellidoM: EditText
    private lateinit var txtDomicilioC: EditText
    private lateinit var btnParte:Button
    lateinit var cerrar: ImageView
    private lateinit var dialog: Dialog
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sacar_parte_empadronado)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }

        dialog = Dialog(this)
        initComponent()
        val firestoreService = BDServices()
        presenter = SacarParteEmpadronadoPresenter(this, SacarParteEmpadronadoModel(firestoreService))
        spinner = findViewById(R.id.codigo_infraccion)
        presenter.loadSpinnerOptions()

        txtPatente.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus->
            if(!hasFocus){
                val patente = txtPatente.text.toString()
                presenter.buscarPersonaPorPatente(patente)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun initComponent(){
        initTextView()
        BottomNavview()
        btnSacarParte()
        cerrar = findViewById(R.id.t_lineaSacarPartes)
        cerrar.setOnClickListener{ showPopupLogout() }
    }
    fun initTextView() {

        txtRut = findViewById(R.id.txtRut)
        txtPatente = findViewById(R.id.txtPatente)
        txtNombreC = findViewById(R.id.txtNombreC)
        txtApellidoP = findViewById(R.id.txtApellidoP)
        txtApellidoM = findViewById(R.id.txtApellidoM)
        txtDomicilioC = findViewById(R.id.txtDomicilioC)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun btnSacarParte(){
        // Obtén la fecha actual
        val calendar = Calendar.getInstance()

        // Formatea la fecha como desees, aquí se utiliza el formato "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = dateFormat.format(calendar.time)

        val nuevaFecha = agregarDosSemanasAFechaActual()
        val horaActual = obtenerHoraActual()
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val nombreCompleto = nombre + " " + apellido


        btnParte = findViewById(R.id.btnParte)
        btnParte.setOnClickListener{
            val selectedOption = spinner.selectedItem.toString()
            if (txtRut.text.isNotEmpty() && txtPatente.text.isNotEmpty() && txtNombreC.text.isNotEmpty() && txtApellidoP.text.isNotEmpty() && txtApellidoM.text.isNotEmpty() && txtDomicilioC.text.isNotEmpty()) {
                // Llamar a Firebase para el registro aquí

                val newParte= Parte("${txtRut.text.toString()}","${txtNombreC.text.toString()}","${txtApellidoP.text.toString()}","${txtApellidoM.text.toString()}","${selectedOption.toString()}","${txtDomicilioC.text.toString()}","${fechaActual.toString()}","${nuevaFecha.toString()}","${horaActual.toString()}","${nombreCompleto.toString()}","${txtPatente.text.toString()}")
                presenter.addParte(newParte)
            } else {

                showPopupAlert()
            }


        }
    }
    override fun showSpinnerOptions(options: List<String>) {
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
    }

    override fun showError(message: String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun mostrarDatosPersona(persona: Persona) {

        txtRut.setText(persona.rut)
        txtRut.keyListener=null
        txtRut.visibility = View.VISIBLE

        txtNombreC.setText(persona.nombre)
        txtNombreC.keyListener = null
        txtNombreC.visibility = View.VISIBLE

        txtApellidoP.setText(persona.apellido_paterno)
        txtApellidoP.keyListener = null
        txtApellidoP.visibility = View.VISIBLE

        txtApellidoM.setText(persona.apellido_materno)
        txtApellidoM.keyListener = null
        txtApellidoM.visibility = View.VISIBLE

        txtDomicilioC.setText(persona.domicilio)
        txtDomicilioC.keyListener = null
        txtDomicilioC.visibility = View.VISIBLE
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, "Error: $mensaje", Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        showPopupExito()
        Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
    }
    fun BottomNavview(){
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val userEmail = intent.getStringExtra("userEmail")

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        ((bottomNavigationView)).setSelectedItemId(R.id.SacarParte)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeActivity -> {

                    startActivity(Intent(this, HomeActivity::class.java).apply {
                        putExtras(getUserBundle(nombre, apellido, userEmail))
                    })
                    true
                }
                R.id.Partes -> {
                    startActivity(Intent(this, PartesActivity::class.java).apply {
                        putExtras(getUserBundle(nombre, apellido, userEmail))
                    })
                    true
                }
                else -> false
            }
        }
    }
    fun showPopupExito(){
        val dialogview = LayoutInflater.from(this)
            .inflate(R.layout.exito_modal_parte,null,false)
        dialog?.setCancelable(true)
        dialog?.setContentView(dialogview)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn_aceptar = dialog.findViewById(R.id.btn_aceptarParte) as Button
        btn_aceptar.setOnClickListener {
            dialog.dismiss()
        }

        val image_cerrar= dialog.findViewById(R.id.image_cerrarParte) as ImageView
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
    private fun getUserBundle(nombre: String?, apellido: String?, userEmail: String?): Bundle {
        return Bundle().apply {
            putString("nombre", nombre)
            putString("apellido", apellido)
            putString("userEmail", userEmail)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerHoraActual(): String {
        val horaActual = LocalTime.now()
        val formato = DateTimeFormatter.ofPattern("HH:mm") // Formato de 24 horas

        return horaActual.format(formato)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarDosSemanasAFechaActual(): String {
        val fechaActual = LocalDate.now()
        val fechaNueva = fechaActual.plusWeeks(2)
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Puedes cambiar el formato según tus necesidades

        return fechaNueva.format(formato)
    }
}