package com.spellbookapp.data.models

// Data class to hold the response from the D&D API
data class SpellResponse(
    // Number of spells
    val count: Int,
    // List of actual spell data
    val results: List<Spell?>
)
