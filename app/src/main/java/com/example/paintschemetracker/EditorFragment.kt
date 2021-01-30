package com.example.paintschemetracker
//These are my imports for this fragment, anything here is functionality that cannot be accessed without an import
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


// This is the editor fragment, any functions that are used while on the editor screen are here
class EditorFragment : Fragment() {


    //here are my global variables, an editorviewmodel, the edtior fragment args that allows for navigation, and databinding for the editor fragment
    private lateinit var viewModel: EditorViewModel
    private val args: EditorFragmentArgs by navArgs()
    private lateinit var binding: EditorFragmentBinding

    //this is my on createview function, anything that is contained within here is functionality for the user interface of the editor fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //This block of code gives the "home button" the check on the top left properties, displaying it, placing it on top and giving it the check icon
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }
        //This generates an options menu
        setHasOptionsMenu(true)

        //This shows whether or not we are creating a new scheme, or editing an existing scheme by checking if the id already exists
        requireActivity().title =
            if (args.miniId == NEW_MINIATURE_ID) {
                getString(R.string.new_paint_scheme)
            } else {
                getString(R.string.edit_paint_scheme)
            }

        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)
        //This sets the order for our properties, name, used paints, techniques, paint method, model used
        binding = EditorFragmentBinding.inflate(inflater, container, false)
        binding.nameEditor.setText("")
        binding.usedPaintsEditor.setText("")
        binding.techniquesEditor.setText("")
        binding.paintMethodEditor.setText("")
        binding.modelUsedEditor.setText("")
        //This saves and returns to the home page on back button press
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }
        )
        //This is where the observer follows the changes in the object
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
    //This handles what happens when the menu item "home", it means the data entered will be saved when pressed and you will return to the home screen
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }
    //This is the save and return function that is called above, it saves the data using the input method manager, and calls the update miniature function from the view model
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
//this saves the state, so that if the app closes it will save the instance of each field
    override fun onSaveInstanceState(outState: Bundle) {
        with(binding.nameEditor) {
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        with(binding.usedPaintsEditor) {
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        with(binding.techniquesEditor) {
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        with(binding.paintMethodEditor) {
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        with(binding.modelUsedEditor) {
            outState.putString(MINIATURE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

}