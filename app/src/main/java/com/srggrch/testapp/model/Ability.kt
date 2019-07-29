package com.srggrch.testapp.model

data class Ability (
    val id: Int,
    val name: String,
    val is_main_series: Boolean,
    val effect_entries: VerboseEffect
)