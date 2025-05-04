package com.spellbookapp.data.models

import com.google.gson.annotations.SerializedName

// Spell data class from the D&D API
data class Spell(
    // ID used in the API
    val index: String,
    val name: String,
    val level: Int,
    val school: ApiReference,
    // Get the casting time by its serialized name
    @SerializedName("casting_time") val casting_time: String,
    val range: String,
    val duration: String,
    val components: List<String>,
    // Material only exists for some spells
    val material: String?,
    // Get the description by "desc"
    @SerializedName("desc") val description: List<String>?,
    val higher_level: List<String>?,
    val classes: List<ApiReference>?,
    // For the SpellBook app, boolean for whether the spell is prepared or not
    var isPrepared: Boolean = false
)

// Reusable type from the DnD API for references to other resources
data class ApiReference(
    val name: String,
    val index: String,
    val url: String
)

// Convert a spell to SpellEntity for storing in the database
fun Spell.toSpellEntity(): SpellEntity {
    return SpellEntity(
        spellId = this.index,
        name = this.name,
        level = this.level,
        school = this.school.name,
        castingTime = this.casting_time,
        range = this.range,
        duration = this.duration,
        components = this.components.joinToString(", "),
        material = this.material,
        description = this.description?.joinToString("\n") ?: "",
        higherLevel = this.higher_level?.joinToString("\n"),
        // Not from the API, store whether the spell is prepared
        isPrepared = false
    )
}

