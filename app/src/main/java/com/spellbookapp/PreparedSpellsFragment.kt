package com.spellbookapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spellbookapp.data.database.SpellDatabase
import com.spellbookapp.viewmodel.PreparedSpellsViewModel
import com.spellbookapp.data.models.toSpell
import com.spellbookapp.data.repository.SpellRepository
import com.spellbookapp.network.RetrofitClient
import com.spellbookapp.viewmodel.PreparedSpellsViewModelFactory

// Fragment to show the list of prepared spells
class PreparedSpellsFragment : Fragment(R.layout.fragment_prepared_spells) {

    // Setup the recyclerView and the adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpellAdapter

    // Lazy setup the PreparedSpellsViewModel with its factory
    private val viewModel: PreparedSpellsViewModel by lazy {
        ViewModelProvider(
            this,
            PreparedSpellsViewModelFactory(
                SpellRepository(RetrofitClient.apiService, SpellDatabase.getDatabase(requireContext()).spellDao())
            )
        ).get(PreparedSpellsViewModel::class.java)
    }

    // On view created instance
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the recyclerView and adapter
        recyclerView = view.findViewById(R.id.recyclerViewPreparedSpells)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SpellAdapter(emptyList()) { spell ->
            val action = PreparedSpellsFragmentDirections.actionPreparedSpellsFragmentToSpellDetailFragment(
                spellId = spell.index,
                isPrepared = true
            )
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        // Observe the prepared spells and update the list based on changes
        viewModel.preparedSpells.observe(viewLifecycleOwner) { spellEntities ->
            val spells = spellEntities.map { it.toSpell() }
            adapter.updateSpells(spells)
        }
    }
}
