package com.spellbookapp.network

import com.spellbookapp.data.models.Spell
import com.spellbookapp.data.models.SpellResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// Retrofit instance for the D&D API endpoints
interface DndApiService {

    // Fetch all the spells
    @GET("spells")
    suspend fun getAllSpells(): Response<SpellResponse>

    // Fetch detailed data for a spell by its index
    @GET("spells/{index}")
    suspend fun getSpellByIndex(@Path("index") index: String): Response<Spell>
}
