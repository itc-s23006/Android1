package jp.ac.it_college.std.s23006.menusample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.ac.it_college.std.s23006.menusample.databinding.SetMealRowBinding

class FoodMenuAdapter(
    private val data: List<FoodMenu>,
    private val onItemSelected: (item: FoodMenu) -> Unit
) : RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>() {

    var currentItem: FoodMenu? = null
        private set

    class ViewHolder(private val binding: SetMealRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
            var onItemClick: (item: FoodMenu) -> Unit = {}
            var onItemLangClick: (item: FoodMenu) -> Unit = {}

        fun bind(item: FoodMenu) {
            binding.name.text = item.name
            binding.price.text = item.price.toString()
            binding.root.setOnClickListener {
                onItemClick(item)
            }
            binding.root.setOnLongClickListener {
                onItemLangClick(item)
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SetMealRowBinding.inflate(inflater, parent, false)).apply {
            onItemClick = onItemSelected
            onItemLangClick = { item ->
                currentItem = item
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}