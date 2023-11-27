package com.jtn.transitmobile.VerPartes.Model

import com.jtn.transitmobile.Commons.BDServices
import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.VerPartes.Contract.VerPartesContract

class VerPartesModel(private val firestoreService: BDServices): VerPartesContract.Model {
    override fun getPartesPorResponsable(responsable: String, callback: (List<Parte>?, Exception?) -> Unit) {
        firestoreService.getPartesPorResponsable(responsable, callback)
    }

}