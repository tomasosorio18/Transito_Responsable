package com.jtn.transitmobile.VerPartes.Presenter

import com.jtn.transitmobile.SacarPartes.Contract.SacarPartesContract
import com.jtn.transitmobile.VerPartes.Contract.VerPartesContract

class VerPartesPresenter(private val _view: VerPartesContract.view, private val model_ : VerPartesContract.Model):VerPartesContract.Presenter {

    private val view: VerPartesContract.view = _view
    private val model: VerPartesContract.Model = model_
    override fun loadPartesPorResponsable(responsable: String) {
        model.getPartesPorResponsable(responsable) { partes, exception ->
            if (exception == null) {
                if (partes != null) {
                    view.showPartes(partes)
                } else {
                    view.mostrarError("No se encontraron partes para el responsable especificado.")
                }
            } else {
                view.mostrarError("Error al obtener las partes: ${exception.message}")
            }
        }
    }
}