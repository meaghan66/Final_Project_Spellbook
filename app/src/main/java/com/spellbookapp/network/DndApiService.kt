package com.spellbookapp.network

import com.spellbookapp.data.models.Spell
import com.spellbookapp.data.models.SpellResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DndApiService {

    // Get all spells (returns a list of indexes and names)
    @GET("spells")
    suspend fun getAllSpells(): Response<SpellResponse>

    // Get a spell by its index (ID)
    @GET("spells/{index}")
    suspend fun getSpellByIndex(@Path("index") index: String): Response<Spell>
}
