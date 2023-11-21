package com.jtn.transitmobile.SacarPartes.Contract

import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.SacarPartes.Model.Persona

interface SacarPartesContract {

    interface View {
        fun showSpinnerOptions(options: List<String>)
        fun showError(message: String)
        fun mostrarDatosPersona(persona: Persona)
        fun mostrarError(mensaje: String)
        fun showSuccess(message: String)
    }

    interface Presenter{
        fun loadSpinnerOptions()
        fun buscarPersonaPorRut(rut: String)
        fun addParte(parte: Parte)

    }
    interface Model {
        fun addNewParte(parte: Parte, callback: (Exception?) -> Unit)
        fun getSpinnerOptions(callback: (List<String>?, Exception?) -> Unit)
        fun getPersonaPorRut(rut: String, callback: (Persona?) -> Unit)

    }
}