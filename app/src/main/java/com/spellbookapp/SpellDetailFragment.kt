package com.spellbookapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.spellbookapp.data.database.SpellDatabase
import com.spellbookapp.data.models.Spell
import com.spellbookapp.data.repository.SpellRepository
import com.spellbookapp.network.RetrofitClient
import com.spellbookapp.viewmodel.SpellDetailViewModel
import com.spellbookapp.viewmodel.SpellDetailViewModelFactory

class SpellDetailFragment : Fragment(R.layout.fragment_spell_detail) {

    private val args: SpellDetailFragmentArgs by navArgs()

    // ViewModel initialized lazily with repository and factory
    private val viewModel: SpellDetailViewModel by lazy {
        ViewModelProvider(
            this,
            SpellDetailViewModelFactory(
                SpellRepository(
                    RetrofitClient.apiService,
                    SpellDatabase.getDatabase(requireContext()).spellDao()
                )
            )
        ).get(SpellDetailViewModel::class.java)
    }

    // Views
    private lateinit var spellName: TextView
    private lateinit var spellLevel: TextView // New TextView for spell level
    private lateinit var spellDescription: TextView
    private lateinit var spellComponents: TextView
    private lateinit var spellCastingTime: TextView
    private lateinit var prepareButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views manually
        spellName = view.findViewById(R.id.spellName)
        spellLevel = view.findViewById(R.id.spellLevel) // Bind new TextView
        spellDescription = view.findViewById(R.id.spellDescription)
        spellComponents = view.findViewById(R.id.spellComponents)
        spellCastingTime = view.findViewById(R.id.spellCastingTime)
        prepareButton = view.findViewById(R.id.prepareButton)
        val backButton = view.findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        prepareButton.text = if (args.isPrepared) "Remove Spell" else "Prepare Spell"

        // Observe and update UI
        viewModel.getSpell(args.spellId).observe(viewLifecycleOwner) { spell ->
            spell?.let { loadedSpell ->
                spellName.text = loadedSpell.name

                spellLevel.text = "Level: ${loadedSpell.level}" // Assuming the level property exists

                spellDescription.text = loadedSpell.description?.joinToString("\n") ?: "No description available."

                spellComponents.text = loadedSpell.components?.joinToString(", ") ?: "No components listed."

                spellCastingTime.text = loadedSpell.casting_time

                prepareButton.setOnClickListener {
                    if (args.isPrepared) {
                        viewModel.removeSpell(loadedSpell.index)
                    } else {
                        viewModel.prepareSpell(loadedSpell)
                    }
                    findNavController().navigateUp()
                }
            }
        }
    }
}
