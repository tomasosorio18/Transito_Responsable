package com.jtn.transitmobile.ui.sacar_partes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Acá se sacarán los partes"
    }
    val text: LiveData<String> = _text
}