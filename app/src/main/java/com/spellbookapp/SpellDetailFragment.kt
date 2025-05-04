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

// Fragment for displaying the details of a spell
class SpellDetailFragment : Fragment(R.layout.fragment_spell_detail) {

    // Get the navigation arguments
    private val args: SpellDetailFragmentArgs by navArgs()

    // Lazy initialize the SpellDetailViewModel with its factory
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

    // UI Views
    private lateinit var spellName: TextView
    private lateinit var spellLevel: TextView
    private lateinit var spellDescription: TextView
    private lateinit var spellComponents: TextView
    private lateinit var spellCastingTime: TextView
    private lateinit var prepareButton: Button

    // On view created instance
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the UI elements
        spellName = view.findViewById(R.id.spellName)
        spellLevel = view.findViewById(R.id.spellLevel)
        spellDescription = view.findViewById(R.id.spellDescription)
        spellComponents = view.findViewById(R.id.spellComponents)
        spellCastingTime = view.findViewById(R.id.spellCastingTime)
        prepareButton = view.findViewById(R.id.prepareButton)

        // Setup the back button to go to the previous page when clicked
        val backButton = view.findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Initially set the button as Prepare Spell and otherwise Remove spell if prepared
        prepareButton.text = if (args.isPrepared) "Remove Spell" else "Prepare Spell"

        // Observe and update UI from the spell data
        viewModel.getSpell(args.spellId).observe(viewLifecycleOwner) { spell ->
            spell?.let { loadedSpell ->
                // Update with the spell components
                spellName.text = loadedSpell.name
                spellLevel.text = "Level: ${loadedSpell.level}"
                spellDescription.text = loadedSpell.description?.joinToString("\n") ?: "No description available."
                spellComponents.text = loadedSpell.components?.joinToString(", ") ?: "No components listed."
                spellCastingTime.text = loadedSpell.casting_time

                // Handle the preparation vs removal button
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
