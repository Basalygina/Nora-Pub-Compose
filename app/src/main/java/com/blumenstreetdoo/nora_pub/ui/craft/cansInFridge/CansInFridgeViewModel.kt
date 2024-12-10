package com.blumenstreetdoo.nora_pub.ui.craft.cansInFridge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CansInFridgeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is CansInFridgeFragment"
    }
    val text: LiveData<String> = _text
}