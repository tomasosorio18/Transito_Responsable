package com.jtn.transitmobile.VerPartes.Contract

import com.jtn.transitmobile.Commons.Parte

interface VerPartesContract {

    interface view{
        fun showPartes(partes: List<Parte>)
        fun mostrarError(message: String)

    }

    interface Presenter {
        fun loadPartesPorResponsable(responsable: String)
    }
    // Interfaz del modelo (Model)
    interface Model {

        fun getPartesPorResponsable(responsable: String, callback: (List<Parte>?, Exception?) -> Unit)
    }
}