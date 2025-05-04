package com.spellbookapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spellbookapp.viewmodel.AllSpellsViewModel
import com.spellbookapp.viewmodel.AllSpellsViewModelFactory
import com.spellbookapp.data.repository.SpellRepository
import com.spellbookapp.network.RetrofitClient
import com.spellbookapp.data.database.SpellDatabase

// Fragment to show all of the available spells
class AllSpellsFragment : Fragment(R.layout.fragment_all_spells) {

    // Lazy instantiate the ViewModel with the factory
    private val viewModel: AllSpellsViewModel by lazy {
        ViewModelProvider(
            this,
            AllSpellsViewModelFactory(
                SpellRepository(RetrofitClient.apiService, SpellDatabase.getDatabase(requireContext()).spellDao())
            )
        ).get(AllSpellsViewModel::class.java)
    }

    private lateinit var adapter: SpellAdapter

    // On create instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // On View Created instance
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewAllSpells)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the spell adapter with an empty list
        adapter = SpellAdapter(emptyList()) { spell ->
            val action = AllSpellsFragmentDirections.actionAllSpellsFragmentToSpellDetailFragment(
                spellId = spell.index,
                isPrepared = false
            )
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        // Observe the list of spells and update the list when data is changed
        viewModel.spells.observe(viewLifecycleOwner) { spells ->
            adapter.updateSpells(spells)
        }

        // Initial fetching of spells
        viewModel.fetchSpells()
    }

    // Inflate the search
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        // Set up the search
        searchView.queryHint = "Search Spells"
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            // Functionality for submitting text in the search
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            // Functionality for changing the text in search
            override fun onQueryTextChange(newText: String?): Boolean {
                filterSpells(newText.orEmpty())
                return true
            }
        })
    }

    // Filter the displayed spells by their name in the search bar
    private fun filterSpells(query: String) {
        val filteredList = viewModel.spells.value?.filter {
            it.name.contains(query, ignoreCase = true)
        } ?: emptyList()
        // Update the spells for filtering
        adapter.updateSpells(filteredList)
    }

}
