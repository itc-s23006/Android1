package jp.ac.it_college.std.s23006.menusample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.ac.it_college.std.s23006.menusample.databinding.SetMealRowBinding

class FoodMenuAdapter(
    private val data: List<FoodMenu>,
    private val onItemSelected: (item: FoodMenu) -> Unit
) : RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>() {

    class ViewHolder(private val binding: SetMealRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FoodMenu, callback: (item: FoodMenu) -> Unit) {
            binding.name.text = item.name
            binding.price.text = item.price.toString()
            binding.root.setOnClickListener {
                callback(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SetMealRowBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], onItemSelected)
    }
}