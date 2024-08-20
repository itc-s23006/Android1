package jp.ac.it_college.std.s23006.implicitlntentsample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jp.ac.it_college.std.s23006.implicitlntentsample.databinding.ActivityMainBinding
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var latitude = 0.0

    private var longitude = 0.0

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

        binding.apply {
            binding.btMapSearch.setOnClickListener(::onMapSearchButtonClick)

            binding.btMapShowCurrent.setOnClickListener(::onMapShowCurrentButtonClick)
        }
    }

    private fun onMapShowCurrentButtonClick(view: View) {
        val uri = Uri.parse("geo:$latitude,$longitude")

        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
    private fun onMapSearchButtonClick(view: View) {
        val searchWord = binding.etSearchWord.text.toString().let { keyword ->
            URLEncoder.encode(keyword, Charsets.UTF_8.name())
        }

        val uri = Uri.parse("geo:0,0?q=$searchWord")

        Intent(Intent.ACTION_VIEW, uri).let { intent ->
            startActivity(intent)
        }
    }
}