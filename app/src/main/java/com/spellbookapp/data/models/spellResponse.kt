package com.spellbookapp.data.models

// data class to hold the response from the meal api
data class SpellResponse(
    val count: Int,
    val results: List<Spell?>
)
