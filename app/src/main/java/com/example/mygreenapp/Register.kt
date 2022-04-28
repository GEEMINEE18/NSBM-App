package com.example.mygreenapp

data class Register(
    val name: String? = null,
    val stdId: String? = null,
    val batch: String? = null,
    val email: String? = null,
    val headOf: String? = null,
    val isHost: Boolean? = null,
    val following: ArrayList<String>? = null)