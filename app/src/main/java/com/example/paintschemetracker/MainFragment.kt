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

//this is our main fragment. any global functionality, objects or variables are all declared here
class MainFragment : Fragment(), 
    MinisListAdapter.ListItemListener{

//Here 3 other files are instantiated, the main view model, which represents all of our main fragments data, the main fragment binding
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: MinisListAdapter

    //this is my on createview function, anything that is contained within here is functionality for the user interface of the main fragment. LayoutInflater, ViewGroup and Bundle are all passed in
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //This also shows the action bar which contains an options menu from the "menu_main.xml
        (activity as AppCompatActivity)
            .supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        //This is the title in the action bar
        requireActivity().title = getString(R.string.app_name)

        //We have our view model provider, which generates our main view model, and our main fragment binding that inflates or renders our view binding
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //this shows our recycler view for the main fragment, which we populate with list items, it also has a divider to separate list items
        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }
        //This keeps the list updated and the observer watches for changes within the list.
        //The mini list adapter is passed in which allows for the list of data objects to be interpreted by the recycler view
        //A the edit miniature function is also given an on click listneer for a floating action button
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

    //This is the function that gives the options menu its properties. It is given an id, and if the selected miniatures are not empty then it will show the delete icon when an item is selected otherwise the expandable item menu is there instead
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
    //These are all of the options within the item menu, add sample data adds 3 set objects to the database, delete selected will delete the selected miniatures, delete all clears the database, and light and dark mode switch, turn on and off light or dark mode
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
//this function deletes all miniatures, it is in the main view model and always returns true
    private fun deleteAllMiniatures(): Boolean {
        viewModel.deleteAllMiniatures()
        return true
    }
//This is the delete selected minis function, they delete miniatures from the view model, and also delay by 100 milliseconds so that the list is updated in the ui as well as the database simultaneously
    private fun deleteSelectedMinis(): Boolean {
        viewModel.deleteMinis(adapter.selectedMiniatures)
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedMiniatures.clear()
            requireActivity().invalidateOptionsMenu()
        }, 100)
    return true
    }
//this is the add sample data function whcih goes to the view model and returns true
    private fun addSampleData(): Boolean {
     viewModel.addSampleData()
        return true
    }


    // This turns on night mode or dark mode by setting the default to yes
    private fun turnOnDarkMode(): Boolean {

      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        return true
    }
    // This turns on light mode or day mode by setting the default to no
    private fun turnOnLightMode(): Boolean {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return true
    }

    //this outputs to the log to show that the mini id output is correct when the list item is clicked
    override fun editMiniature(miniId: Int) {
        Log.i(TAG, "onItemClick: received mini id $miniId")
        val action = MainFragmentDirections.actionEditMiniature(miniId)
        findNavController().navigate(action)
    }

    override fun onItemSelectionChanged() {
        requireActivity().invalidateOptionsMenu()
    }
    //This saves the instance state so that even if the app is closed the state will be preserved
    override fun onSaveInstanceState(outState: Bundle) {
        if (this::adapter.isInitialized){
            outState.putParcelableArrayList(SELECTED_MINIATURES_KEY,
            adapter.selectedMiniatures)
        }
        super.onSaveInstanceState(outState)
        }
    }




