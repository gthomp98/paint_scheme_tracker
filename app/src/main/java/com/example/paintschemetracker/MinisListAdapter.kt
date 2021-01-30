package com.example.paintschemetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paintschemetracker.data.MiniatureEntity
import com.example.paintschemetracker.databinding.ListItemBinding


//This is the minislistadapter, this is used to display data in the recylcer view list.
class MinisListAdapter(private val miniaturesList: List<MiniatureEntity>,
                       private val listener: ListItemListener):
    RecyclerView.Adapter<MinisListAdapter.ViewHolder>() {
//This is a variable that has the miniature entity array list assigned to it
    val selectedMiniatures = arrayListOf<MiniatureEntity>()

//this is the view holder, which gives the recycler view some of its properties
    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)

    }
//This is a function that runs when the view holder is created. The inflater and view are both called here. and the view holder class is returned.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }
//This gets the size of the array list
    override fun getItemCount() = miniaturesList.size

//this function gets the miniature list and displays the name and model used, and sets an on click listener for the edit miniature function, and the flaoting action button to select a paint scheme. it also dictates the check vs brush icon for the fab depending on if its selected or not
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val miniature = miniaturesList[position]
        with(holder.binding) {
            schemeTextName.text = miniature.name
            schemeModelUsed.text = miniature.modelUsed
            root.setOnClickListener{
                listener.editMiniature(miniature.id)
            }
            fab.setOnClickListener{
                if(selectedMiniatures.contains(miniature)) {
                    selectedMiniatures.remove(miniature)
                    fab.setImageResource(R.drawable.ic_brush)
                }else{
                    selectedMiniatures.add(miniature)
                    fab.setImageResource(R.drawable.ic_check)
                }
                listener.onItemSelectionChanged()
            }
            fab.setImageResource(
                if(selectedMiniatures.contains(miniature)) {
                    R.drawable.ic_check
                }else{
                    R.drawable.ic_brush
                }
            )
        }
    }
    //this is the list item listener that passes in the edit and on item selection functions for each list item
        interface ListItemListener{
            fun editMiniature(miniId: Int)
            fun onItemSelectionChanged()
        }

}
