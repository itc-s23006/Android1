package jp.ac.it_college.std.s23006.fragmentsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.ac.it_college.std.s23006.fragmentsample.databinding.FoodMenuRowBinding

class FoodMenuListAdapter(
    private val data: List<FoodMenu>,
    private val onItemSelected: (item: FoodMenu) -> Unit    // 外部へ選択されたメニュー通知用の関数
) : RecyclerView.Adapter<FoodMenuListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: FoodMenuRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // binding.root をクリックされたときに呼び出す、アダプタへのコールバック関数
        var onItemClick: (item: FoodMenu) -> Unit = {}

        // アイテムを各ビューへ反映させるためのメソッド
        fun bind(item: FoodMenu) {
            // スコープ関数 apply 使って記述を簡略化する
            binding.apply {
                name.text = item.name
                price.text = item.price.toString()
                // root(ConstraintLayout)をクリックされたら、アダプタへ通知するコールバック関数を実行する
                root.setOnClickListener { onItemClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).let { inflater ->
            ViewHolder(FoodMenuRowBinding.inflate(inflater, parent, false)).apply {
                onItemClick = onItemSelected
            }
        }
        /* // 複数文で書く場合
        val inflater = LayoutInflater.from(parent.context)
        val binding = FoodMenuRowBinding.inflate(inflater, parent, false)
        val holder = ViewHolder(binding)
        holder.onItemClick = onItemSelected
        return holder
         */
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // ViewHolder の binding プロパティが private でアクセスできないので
        // bind メソッド経由でビューへのデータ反映をする。
        holder.bind(data[position])
    }
}