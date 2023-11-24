package com.jtn.transitmobile.SacarPartesEmpadronado.Presenter


import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.SacarPartesEmpadronado.Contract.SacarParteEmpadronadoContract

class SacarParteEmpadronadoPresenter(private val _view: SacarParteEmpadronadoContract.View, private val model_ : SacarParteEmpadronadoContract.Model):SacarParteEmpadronadoContract.Presenter {
    var view: SacarParteEmpadronadoContract.View = _view
    var model: SacarParteEmpadronadoContract.Model = model_
    override fun loadSpinnerOptions() {
        model.getSpinnerOptions { options, exception ->
            if(exception!=null){
                view.showError("error")
            }else if(options!=null){
                view.showSpinnerOptions(options)
            }
        }
    }

    override fun buscarPersonaPorPatente(patente: String) {
        model.getPersonaPorPatente(patente){persona ->
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