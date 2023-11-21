package com.jtn.transitmobile.SacarPartes.Model

import com.jtn.transitmobile.Commons.BDServices
import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.SacarPartes.Contract.SacarPartesContract

class SacarParteModel(private val firestore: BDServices) : SacarPartesContract.Model {
    override fun addNewParte(parte: Parte, callback: (Exception?) -> Unit) {
        firestore.addParte(parte,callback)
    }

    override fun getSpinnerOptions(callback: (List<String>?, Exception?) -> Unit) {
        firestore.getSpinnerOptions(callback)
    }

    override fun getPersonaPorRut(rut: String, callback: (Persona?) -> Unit) {
        firestore.getPersonaPorRut(rut,callback)
    }
}