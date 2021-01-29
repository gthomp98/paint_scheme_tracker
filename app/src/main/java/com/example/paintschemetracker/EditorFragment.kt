package com.example.paintschemetracker

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.paintschemetracker.data.CURSOR_POSITION_KEY
import com.example.paintschemetracker.data.MINIATURE_TEXT_KEY
import com.example.paintschemetracker.data.NEW_MINIATURE_ID
import com.example.paintschemetracker.databinding.EditorFragmentBinding

class EditorFragment : Fragment() {



    private lateinit var viewModel: EditorViewModel
    private val args: EditorFragmentArgs by navArgs()
    private lateinit var binding: EditorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }

        setHasOptionsMenu(true)

        requireActivity().title =
            if (args.miniId == NEW_MINIATURE_ID) {
                getString(R.string.new_paint_scheme)
            } else {
                getString(R.string.edit_paint_scheme)


            }

        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)

        binding = EditorFragmentBinding.inflate(inflater,container,false)
        binding.nameEditor.setText("")
        binding.usedPaintsEditor.setText("")
        binding.techniquesEditor.setText("")
        binding.paintMethodEditor.setText("")
        binding.modelUsedEditor.setText("")

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }
        )

        viewModel.currentMiniature.observe(viewLifecycleOwner, Observer {
            val savedString = savedInstanceState?.getString(MINIATURE_TEXT_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0
            binding.nameEditor.setText(it.name)
            binding.usedPaintsEditor.setText(it.usedPaints)
            binding.techniquesEditor.setText(it.techniques)
            binding.paintMethodEditor.setText(it.paintMethod)
            binding.modelUsedEditor.setText(it.modelUsed)

            binding.nameEditor.setSelection(cursorPosition)
            binding.usedPaintsEditor.setSelection(cursorPosition)
            binding.techniquesEditor.setSelection(cursorPosition)
            binding.paintMethodEditor.setSelection(cursorPosition)
            binding.modelUsedEditor.setSelection(cursorPosition)
        })
        viewModel.getMiniatureById(args.miniId)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveAndReturn(): Boolean {

        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        viewModel.currentMiniature.value?.name = binding.nameEditor.text.toString()
        viewModel.currentMiniature.value?.usedPaints = binding.usedPaintsEditor.text.toString()
        viewModel.currentMiniature.value?.techniques = binding.techniquesEditor.text.toString()
        viewModel.currentMiniature.value?.paintMethod = binding.paintMethodEditor.text.toString()
        viewModel.currentMiniature.value?.modelUsed = binding.modelUsedEditor.text.toString()

        viewModel.updateMiniature()


        findNavController().navigateUp()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        with(binding.nameEditor){
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        with(binding.usedPaintsEditor){
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        with(binding.techniquesEditor){
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        with(binding.paintMethodEditor){
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        with(binding.modelUsedEditor){
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

}