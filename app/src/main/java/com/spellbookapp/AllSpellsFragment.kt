package com.spellbookapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

class AllSpellsFragment : Fragment(R.layout.fragment_all_spells) {

    private val viewModel: AllSpellsViewModel by lazy {
        ViewModelProvider(
            this,
            AllSpellsViewModelFactory(
                SpellRepository(RetrofitClient.apiService, SpellDatabase.getDatabase(requireContext()).spellDao())
            )
        ).get(AllSpellsViewModel::class.java)
    }

    private lateinit var adapter: SpellAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewAllSpells)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        adapter = SpellAdapter(emptyList()) { spell ->
            val action = AllSpellsFragmentDirections.actionAllSpellsFragmentToSpellDetailFragment(
                spellId = spell.index,
                isPrepared = false
            )
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        viewModel.spells.observe(viewLifecycleOwner) { spells ->
            adapter.updateSpells(spells)

            val spinner = view.findViewById<androidx.appcompat.widget.AppCompatSpinner>(R.id.classSpinner)
            val classes = listOf(
                "All",
                "Barbarian",
                "Bard",
                "Cleric",
                "Druid",
                "Fighter",
                "Monk",
                "Paladin",
                "Ranger",
                "Rogue",
                "Sorcerer",
                "Warlock",
                "Wizard"
            )
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, classes)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedClass = classes[position]
                    filterByClass(selectedClass)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // No action needed
                }
            }
        }


        viewModel.fetchSpells()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.queryHint = "Search Spells"
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSpells(newText.orEmpty())
                return true
            }
        })
    }

    private fun filterSpells(query: String) {
        val filteredList = viewModel.spells.value?.filter {
            it.name.contains(query, ignoreCase = true)
        } ?: emptyList()
        adapter.updateSpells(filteredList)
    }

    private fun filterByClass(selectedClass: String) {
        val allSpells = viewModel.spells.value ?: return
        val filtered = if (selectedClass == "All") {
            allSpells
        } else {
            allSpells.filter { spell ->
                spell.classes?.any { it.name.equals(selectedClass, ignoreCase = true) } == true
            }
        }
        adapter.updateSpells(filtered)
    }


}
