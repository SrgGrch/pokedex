package com.srggrch.testapp.model

class Wrapper<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: ArrayList<T>
)