package com.jtn.transitmobile.Vistas_pruebas.ver_partes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Acá se verán los partes"
    }
    val text: LiveData<String> = _text
}