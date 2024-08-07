package jp.ac.it_college.std.s23006.fragmentsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s23006.fragmentsample.databinding.FragmentMenuListBinding
import kotlinx.serialization.json.Json

class MenuListFragment : Fragment() {
    // Bindingクラスのインスタンスを保持するプロパティ(Nullable)
    private var _binding: FragmentMenuListBinding? = null

    // シンプルな「binding」でアクセスするための拡張プロパティ
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 定食リストデータの読み込み
        val setMealList = Json.decodeFromString<List<FoodMenu>>(
            resources.openRawResource(R.raw.set_meal).reader().readText()
        )

        // ビューの設定
        binding.apply {
            // RecyclerView の設定
            lvMenu.apply {
                adapter = FoodMenuListAdapter(setMealList, ::order)
                val manager = LinearLayoutManager(context)
                layoutManager = manager
                addItemDecoration(DividerItemDecoration(context, manager.orientation))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun order(item: FoodMenu) {
        // 後で記述する
    }
}