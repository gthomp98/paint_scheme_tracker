package com.example.paintschemetracker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paintschemetracker.data.MiniatureEntity
import com.example.paintschemetracker.data.NEW_MINIATURE_ID
import com.example.paintschemetracker.data.SELECTED_MINIATURES_KEY
import com.example.paintschemetracker.data.TAG
import com.example.paintschemetracker.databinding.MainFragmentBinding


class MainFragment : Fragment(), 
    MinisListAdapter.ListItemListener{


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: MinisListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity)
            .supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)


        requireActivity().title = getString(R.string.app_name)


            binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        viewModel.minisList?.observe(viewLifecycleOwner, Observer {
            Log.i("miniLogging", it.toString())
            adapter = MinisListAdapter(it, this@MainFragment)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager=LinearLayoutManager(activity)

            val selectedMiniatures =
                savedInstanceState?.getParcelableArrayList<MiniatureEntity>(SELECTED_MINIATURES_KEY)
            adapter.selectedMiniatures.addAll(selectedMiniatures ?: emptyList())
        })

        binding.floatingActionButton.setOnClickListener{
            editMiniature(NEW_MINIATURE_ID)
        }

        return binding.root


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId=
    if(this::adapter.isInitialized &&
            adapter.selectedMiniatures.isNotEmpty()
    ) {
        R.menu.menu_main_selected_items
    }else {
        R.menu.menu_main
        }

    inflater.inflate(menuId,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_sample_data -> addSampleData()
            R.id.action_delete -> deleteSelectedMinis()
            R.id.action_delete_all -> deleteAllMiniatures()
            R.id.action_dark_mode_switch -> turnOnDarkMode()
            R.id.action_light_mode_switch -> turnOnLightMode()
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun deleteAllMiniatures(): Boolean {
        viewModel.deleteAllMiniatures()
        return true
    }

    private fun deleteSelectedMinis(): Boolean {
        viewModel.deleteMinis(adapter.selectedMiniatures)
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedMiniatures.clear()
            requireActivity().invalidateOptionsMenu()
        }, 100)
    return true
    }

    private fun addSampleData(): Boolean {
     viewModel.addSampleData()
        return true
    }



    private fun turnOnDarkMode(): Boolean {

      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        return true
    }

    private fun turnOnLightMode(): Boolean {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return true
    }


    override fun editMiniature(miniId: Int) {
        Log.i(TAG, "onItemClick: received mini id $miniId")
        val action = MainFragmentDirections.actionEditMiniature(miniId)
        findNavController().navigate(action)
    }

    override fun onItemSelectionChanged() {
        requireActivity().invalidateOptionsMenu()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (this::adapter.isInitialized){
            outState.putParcelableArrayList(SELECTED_MINIATURES_KEY,
            adapter.selectedMiniatures)
        }
        super.onSaveInstanceState(outState)
        }
    }




