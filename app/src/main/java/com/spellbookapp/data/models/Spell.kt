package com.spellbookapp.data.models

data class Spell(
    val index: String, // Unique ID used in the API
    val name: String,
    val level: Int,
    val school: ApiReference,
    val casting_time: String,
    val range: String,
    val duration: String,
    val components: List<String>,
    val material: String?, // Optional, only exists for some spells
    val description: List<String>,
    val higher_level: List<String>?,
    val classes: List<ApiReference>,
    var isFavorite: Boolean = false // App-specific field, not from API
)

// Reusable type from the DnD API for references to other resources
data class ApiReference(
    val name: String,
    val index: String,
    val url: String
)
