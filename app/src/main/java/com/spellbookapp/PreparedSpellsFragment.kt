package com.spellbookapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spellbookapp.data.database.SpellDatabase
import com.spellbookapp.viewmodel.PreparedSpellsViewModel
import com.spellbookapp.data.models.toSpell
import com.spellbookapp.data.repository.SpellRepository
import com.spellbookapp.network.RetrofitClient
import com.spellbookapp.viewmodel.PreparedSpellsViewModelFactory

class PreparedSpellsFragment : Fragment(R.layout.fragment_prepared_spells) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpellAdapter

    // ✅ Correct ViewModel
    private val viewModel: PreparedSpellsViewModel by lazy {
        ViewModelProvider(
            this,
            PreparedSpellsViewModelFactory(
                SpellRepository(RetrofitClient.apiService, SpellDatabase.getDatabase(requireContext()).spellDao())
            )
        ).get(PreparedSpellsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewPreparedSpells)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SpellAdapter(emptyList()) { spell ->
            // Navigate to detail screen if you want
        }
        recyclerView.adapter = adapter

        viewModel.preparedSpells.observe(viewLifecycleOwner) { spellEntities ->
            val spells = spellEntities.map { it.toSpell() }
            adapter.updateSpells(spells)
        }
    }
}
