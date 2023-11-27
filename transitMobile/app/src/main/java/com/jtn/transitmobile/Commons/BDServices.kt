package com.jtn.transitmobile.Commons

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class BDServices {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val itemsCollection: CollectionReference = db.collection("parte")

    fun getSpinnerOptions(callback: (List<String>?, Exception?) -> Unit) {
        val spinnerOptions = ArrayList<String>()
        val db = FirebaseFirestore.getInstance()

        db.collection("infraccion").get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val codigo = document.getLong("codigo")

                    if (codigo != null) {
                        val codigoStr = codigo.toString()
                        spinnerOptions.add(codigoStr)
                    }
                }
                callback(spinnerOptions, null)
            }
            .addOnFailureListener { e ->
                callback(null, e)
            }
    }
    fun getPersonaPorRut(rut: String, callback: (Persona?) -> Unit) {
        db.collection("persona")
            .whereEqualTo("rut", rut)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val nombre = document.getString("nombre")
                    val apellido_paterno = document.getString("apellido_paterno")
                    val apellido_materno = document.getString("apellido_materno")
                    val domicilio = document.getString("domicilio")
                    val patente = document.getString("patente")

                    if (nombre != null && apellido_paterno != null && apellido_materno != null && domicilio != null && patente !=null) {
                        val persona = Persona(rut, nombre, apellido_paterno,apellido_materno,domicilio,patente)
                        callback(persona)
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                callback(null)
            }


    }
    fun addParte(parte: Parte, callback: (Exception?) -> Unit) {
        itemsCollection.add(parte)
            .addOnSuccessListener { _ ->
                callback(null)
            }
            .addOnFailureListener { e ->
                callback(e)
            }
    }
    fun getPartesPorResponsable(responsable: String, callback: (List<Parte>?, Exception?) -> Unit) {
        // Supongamos que 'itemsCollection' es tu referencia a la colección de Firebase que contiene las partes
        // y que quieres filtrar por el campo 'responsable'
        itemsCollection.whereEqualTo("responsable", responsable)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val partesList = ArrayList<Parte>()
                for (document in querySnapshot) {
                    val parte = document.toObject(Parte::class.java)
                    partesList.add(parte)
                }
                callback(partesList, null) // Llamar al callback con la lista de partes y sin errores
            }
            .addOnFailureListener { e ->
                callback(null, e) // Llamar al callback con un error
            }
    }
    fun getPersonaPorpatente(vehiculo_patente: String, callback: (Persona?) -> Unit) {
        db.collection("persona")
            .whereEqualTo("vehiculo_patente", vehiculo_patente)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val rut=document.getString("rut")
                    val nombre = document.getString("nombre")
                    val apellido_paterno = document.getString("apellido_paterno")
                    val apellido_materno = document.getString("apellido_materno")
                    val domicilio = document.getString("domicilio")

                    if (rut != null && nombre != null && apellido_paterno != null && apellido_materno != null && domicilio != null && vehiculo_patente !=null) {
                        val persona = Persona(rut, nombre, apellido_paterno,apellido_materno,domicilio,vehiculo_patente)
                        callback(persona)
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                callback(null)
            }


    }
}