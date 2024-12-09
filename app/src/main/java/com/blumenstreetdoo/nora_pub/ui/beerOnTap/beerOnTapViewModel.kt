package com.blumenstreetdoo.nora_pub.ui.beerOnTap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BeerOnTapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is BeerOnTapFragment"
    }
    val text: LiveData<String> = _text
}