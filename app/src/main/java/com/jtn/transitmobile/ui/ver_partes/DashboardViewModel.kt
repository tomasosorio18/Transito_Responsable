package com.jtn.transitmobile.ui.ver_partes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Acá se verán los partes"
    }
    val text: LiveData<String> = _text
}