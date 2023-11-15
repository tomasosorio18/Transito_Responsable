package com.jtn.transitmobile.Home.View

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Acá se verá el homer"
    }
    val text: LiveData<String> = _text
}