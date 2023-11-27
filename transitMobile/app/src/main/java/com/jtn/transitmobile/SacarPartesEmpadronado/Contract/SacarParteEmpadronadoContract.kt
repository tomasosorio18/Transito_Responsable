package com.jtn.transitmobile.SacarPartesEmpadronado.Contract

import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.Commons.Persona

interface SacarParteEmpadronadoContract {

    interface View {
        fun showSpinnerOptions(options: List<String>)
        fun showError(message: String)
        fun mostrarDatosPersona(persona: Persona)
        fun mostrarError(mensaje: String)
        fun showSuccess(message: String)
    }

    interface Presenter{
        fun loadSpinnerOptions()
        fun buscarPersonaPorPatente(vehiculo_patente: String)
        fun addParte(parte: Parte)

    }
    interface Model {
        fun addNewParte(parte: Parte, callback: (Exception?) -> Unit)
        fun getSpinnerOptions(callback: (List<String>?, Exception?) -> Unit)
        fun getPersonaPorPatente(patente: String, callback: (Persona?) -> Unit)

    }
}