package jp.ac.it_college.std.s23006.listviewsample2

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jp.ac.it_college.std.s23006.listviewsample2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding   // 追加

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)   // 追加
        enableEdgeToEdge()
        setContentView(binding.root)    // 置き換え
        ViewCompat.setOnApplyWindowInsetsListener(/* 置き換え */ binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // リストビューに表示するリストデータを作成。
        var menuList = mutableListOf(
            "から揚げ定食", "ハンバーグ定食", "生姜焼き定食", "ステーキ定食", "野菜炒め定食",
            "とんかつ定食", "ミンチかつ定食", "チキンカツ定食", "コロッケ定食",
            "回鍋肉定食", "麻婆豆腐定食", "青椒肉絲定食", "八宝菜定食", "酢豚定食",
            "豚の角煮定食", "焼き鳥定食", "焼き魚定食", "焼肉定食"
        )
        // アダプタオブジェクトを生成
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menuList)
        // リストビューにアダプタオブジェクトを設定
        binding.lvMenu.adapter = adapter

        binding.lvMenu.setOnItemClickListener { _, _, _, _ ->

            val dialogFragment = OrderConfirmDialogFragment()

            dialogFragment.show(supportFragmentManager, "OrderConfigDialogFragment")
        }
    }
}