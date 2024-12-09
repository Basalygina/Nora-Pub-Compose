package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CraftViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is CraftFragment"
    }
    val text: LiveData<String> = _text
}