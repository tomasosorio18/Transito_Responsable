package com.jtn.transitmobile.SacarPartesEmpadronado.Model

import com.jtn.transitmobile.Commons.BDServices
import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.Commons.Persona
import com.jtn.transitmobile.SacarPartesEmpadronado.Contract.SacarParteEmpadronadoContract

class SacarParteEmpadronadoModel(private val firestore: BDServices): SacarParteEmpadronadoContract.Model {
    override fun addNewParte(parte: Parte, callback: (Exception?) -> Unit) {
        firestore.addParte(parte,callback)
    }

    override fun getSpinnerOptions(callback: (List<String>?, Exception?) -> Unit) {
        firestore.getSpinnerOptions(callback)
    }

    override fun getPersonaPorPatente(vehiculo_patente: String, callback: (Persona?) -> Unit) {
        firestore.getPersonaPorpatente(vehiculo_patente,callback)
    }

}