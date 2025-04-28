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
    private lateinit var spellDescription: TextView
    private lateinit var prepareButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views manually (no ViewBinding)
        spellName = view.findViewById(R.id.spellName)
        spellDescription = view.findViewById(R.id.spellDescription)
        prepareButton = view.findViewById(R.id.prepareButton)

        // Observe and update UI
        viewModel.getSpell(args.spellId).observe(viewLifecycleOwner) { spell: Spell? ->
            spell?.let { loadedSpell ->
                spellName.text = loadedSpell.name
                spellDescription.text = loadedSpell.description?.joinToString("\n\n") ?: "No description available."
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
