package com.jtn.transitmobile.SacarPartes.Presenter

import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.SacarPartes.Contract.SacarPartesContract


class SacarPartePresenter(private val _view: SacarPartesContract.View, private val model_ : SacarPartesContract.Model):
    SacarPartesContract.Presenter {
    var view: SacarPartesContract.View = _view
    var model: SacarPartesContract.Model = model_
    override fun loadSpinnerOptions() {
        model.getSpinnerOptions { options, exception ->
            if(exception!=null){
                view.showError("error")
            }else if(options!=null){
                view.showSpinnerOptions(options)
            }
        }
    }

    override fun buscarPersonaPorRut(rut: String) {
        model.getPersonaPorRut(rut){persona ->
            if(persona!=null){
                view.mostrarDatosPersona(persona)
            }else{
                view.mostrarError("Persona no encontrada")
            }
        }
    }

    override fun addParte(parte: Parte) {
        model.addNewParte(parte){ exception ->
            if (exception != null) {
                view?.showError("Error al agregar el elemento: ${exception.message}")
            } else {
                view?.showSuccess("Elemento agregado con éxito")
                // También puedes llamar a loadItems() aquí para actualizar la lista después de agregar un elemento.
            }
        }
    }
}