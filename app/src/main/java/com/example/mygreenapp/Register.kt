package com.example.mygreenapp

data class Register(
    val name: String? = null,
    val stdId: String? = null,
    val batch: String? = null,
    val email: String? = null,
    val isAdmin: Boolean? = null,
    val following: ArrayList<String>)