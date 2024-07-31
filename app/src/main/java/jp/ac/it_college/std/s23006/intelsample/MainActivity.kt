package jp.ac.it_college.std.s23006.intelsample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s23006.intelsample.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val jsonString = resources.openRawResource(R.raw.set_meal).reader().readText()

        val setMealList = Json.decodeFromString<List<SetMeal>>(jsonString)

        binding.lvMenu.apply {
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = linearLayoutManager
            val SetMealAdapter = SetMealAdapter(setMealList) { item ->
                Toast.makeText(
                    this@MainActivity,
                    "選択された定食: ${item.name}",
                    Toast.LENGTH_LONG
                ).show()
            }
            adapter = SetMealAdapter(setMealList) { item ->
                val menuName = item.name
                val menuPrice = item.price

                val intent2MenuThanks = Intent(
                    this@MainActivity,
                    MenuThanksActivity::class.java
                )
                intent2MenuThanks.putExtra("menuName", menuName)
                intent2MenuThanks.putExtra("menuPrice", menuPrice)

                startActivity(intent2MenuThanks)
            }

            addItemDecoration(
                DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation)
            )
        }
    }
}