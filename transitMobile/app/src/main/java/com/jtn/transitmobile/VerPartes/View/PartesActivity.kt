package com.jtn.transitmobile.VerPartes.View

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
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jtn.transitmobile.Commons.BDServices
import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.Home.View.HomeActivity
import com.jtn.transitmobile.Login.View.MainActivity
import com.jtn.transitmobile.R
import com.jtn.transitmobile.SacarPartes.View.SacarPartesActivity
import com.jtn.transitmobile.VerPartes.Adapter.VerParteAdapter
import com.jtn.transitmobile.VerPartes.Contract.VerPartesContract
import com.jtn.transitmobile.VerPartes.Model.VerPartesModel
import com.jtn.transitmobile.VerPartes.Presenter.VerPartesPresenter

class PartesActivity : AppCompatActivity(), VerPartesContract.view {
    private lateinit var presenter: VerPartesContract.Presenter
    private lateinit var adapter: VerParteAdapter
    private val itemsList = ArrayList<Parte>()
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partes)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        BottomNavview()
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val responsable = nombre + " " + apellido

        adapter = VerParteAdapter(this, R.layout.activity_ver_parte_adapter, itemsList)
        listView = findViewById(R.id.listViewInItemListActivity)
        listView.adapter = adapter
        val firestoreService = BDServices()
        presenter = VerPartesPresenter(this, VerPartesModel(firestoreService))
        presenter.loadPartesPorResponsable(responsable)

    }


    fun BottomNavview(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        ((bottomNavigationView)).setSelectedItemId(R.id.Partes)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeActivity -> {
                    val nombre = intent.getStringExtra("nombre")
                    val apellido = intent.getStringExtra("apellido")
                    val userEmail = intent.getStringExtra("userEmail")
                    // Acción para el botón Home
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("nombre", nombre)
                    intent.putExtra("apellido", apellido)
                    intent.putExtra("userEmail", userEmail)
                    startActivity(intent)
                    true
                }
                R.id.SacarParte -> {
                    val nombre = intent.getStringExtra("nombre")
                    val apellido = intent.getStringExtra("apellido")
                    val userEmail = intent.getStringExtra("userEmail")
                    // Acción para el botón Dashboard
                    val intent = Intent(this, SacarPartesActivity::class.java)
                    intent.putExtra("nombre", nombre)
                    intent.putExtra("apellido", apellido)
                    intent.putExtra("userEmail", userEmail)
                    startActivity(intent)
                    true
                }

                else -> false
            }
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

    override fun showPartes(partes: List<Parte>) {
        itemsList.clear()
        itemsList.addAll(partes)
        adapter.notifyDataSetChanged()
    }

    override fun mostrarError(message: String) {
        Toast.makeText(this, "${message}", Toast.LENGTH_SHORT).show()
    }
}