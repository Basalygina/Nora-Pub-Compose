package com.blumenstreetdoo.nora_pub.domain.models

import java.io.Serializable

class Brewery(
    val id: String,
    val name: String,
    val country: String,
    val city: String,
    val url: String,
) : Serializable