package com.example.paintschemetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paintschemetracker.data.MiniatureEntity
import com.example.paintschemetracker.databinding.ListItemBinding

class MinisListAdapter(private val miniaturesList: List<MiniatureEntity>,
                       private val listener: ListItemListener):
    RecyclerView.Adapter<MinisListAdapter.ViewHolder>() {

    val selectedMiniatures = arrayListOf<MiniatureEntity>()


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = miniaturesList.size


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
        interface ListItemListener{
            fun editMiniature(miniId: Int)
            fun onItemSelectionChanged()
        }

}
