package jp.ac.it_college.std.s23006.fragmentsample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import jp.ac.it_college.std.s23006.fragmentsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.apply {
            setFragmentResultListener(
                "selectedMenu", this@MainActivity, ::onSelectedMenu
            )
            // setFragmentResultListener("Key", this@MainActivity) { requestKey, bundle ->
            //      onSelectedMenu(requestKey, bundle)
            // }
            setFragmentResultListener(
                "backToList", this@MainActivity, ::onBackToList
            )
        }
    }

    private fun onSelectedMenu(requestKey: String, bundle: Bundle) {
        supportFragmentManager.commit {
            // トランザクションが正しく動作するように設定。
            setReorderingAllowed(true)

            // fragmentMainContainer があればデフォルトレイアウト
            if (binding.fragmentMainContainer != null) {
                // 現在の表示内容をバックスタックに追加
                addToBackStack("Only List")
                // 注文完了フラグメントに切り替え
                replace(R.id.fragmentMainContainer, MenuThanksFragment::class.java, bundle)
            } else {
                // タブレット横向き版のレイアウトが使われている場合
                replace(R.id.fragmentThanksContainer, MenuThanksFragment::class.java, bundle)
            }
        }
    }

    private fun onBackToList(requestKey: String, bundle: Bundle) {
        // fragmentMainContainer があればデフォルトレイアウト
        if (binding.fragmentMainContainer != null) {
            supportFragmentManager.popBackStack()
        } else {
            // タブレット横向き版のレイアウトが使われている場合
            supportFragmentManager.commit {
                binding.fragmentThanksContainer?.let { container ->
                    remove(container.getFragment())
                }
            }
        }
    }
}