package jp.ac.it_college.std.s23006.asynccoroutinesample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s23006.asynccoroutinesample.databinding.ActivityMainBinding
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        // ログに記載するタグ用の文字列
        private const val DEBUG_TAG = "AsyncSample"

        // お天気情報のURL
        private const val WEATHER_INFO_URL =
            "https://api.openweathermap.org/data/2.5/weather?lang=ja"

        // お天気APIにアクセスするためのAPIキー
        private const val APP_ID = BuildConfig.apiKey
    }

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
        // 表示の初期化
        binding.apply {
            lvCityList.apply {
                // RecyclerView の初期化。詳細は過去のチャプターのコードを参照してください
                adapter = CityListAdapter(City.list) {
                    val urlString = "$WEATHER_INFO_URL&q=${it.q}&appid=$APP_ID"
                    receiveWeatherInfo(urlString)
                }
                val manager = LinearLayoutManager(this@MainActivity)
                layoutManager = manager
                addItemDecoration(
                    DividerItemDecoration(this@MainActivity, manager.orientation)
                )
            }
        }
    }

    @UiThread
    private fun receiveWeatherInfo(urlString: String) {
        val backgroundReceiver = WeatherInfoBackgroundReceiver(urlString)
        val executeService = Executors.newSingleThreadExecutor()
        val future = executeService.submit(backgroundReceiver)
        val result = future.get()
        showWeatherInfo(result)
    }

    @UiThread
    private fun showWeatherInfo(result: String) {
        // ルートのオブジェクトを生成
        val root = JSONObject(result)
        // 都市名を取得
        val cityName = root.getString("name")
        // 緯度経度情報の詰まったオブジェクトを切り出す
        val coord = root.getJSONObject("coord")
        // 緯度を取り出す
        val latitude = coord.getString("lat")
        // 経度を取り出す
        val longitude = coord.getString("lon")
        // 天気情報が入った配列を切り出す
        val weatherArray = root.getJSONArray("weather")
        // 現在の天気が詰まったオブジェクトを取り出す
        val weather = weatherArray.getJSONObject(0)
        // 現在の天気情報概要
        val description = weather.getString("description")
        // JSON のパースはここまで。

        // 画面にデータを表示
        binding.tvWeatherTelop.text = "${cityName}の天気"
        binding.tvWeatherDesc.text = """
            現在は${description}です。
            緯度は${latitude}度で経度は${longitude}度です。
        """.trimIndent()
    }

    // 非同期で実行したい内容(お天気情報APIへのアクセス)を実装したインナークラス
    private class WeatherInfoBackgroundReceiver(private val urlString: String) : Callable<String> {
        @WorkerThread
        override fun call(): String {
            // ここで OpenWeather へのアクセスをするコードを実装する
            // URLオブジェクトの生成
            val url = URL(urlString)
            // HttpURLConnection オブジェクトを取得
            val conn = (url.openConnection() as HttpURLConnection).apply {
                // 接続の設定
                // 接続が確立するまでの最大時間(ミリ秒)
                connectTimeout = 1000
                // データの読み取り時に応答待ちする最大時間(ミリ秒)
                readTimeout = 1000
                // HTTP リクエストメソッドの指定
                requestMethod = "GET"
            }
            return try {
                // 接続
                conn.connect()
                // レスポンスデータを取得
                val result = conn.inputStream.reader().readText()
                // try ブロックの結果
                result
            } catch (ex: SocketTimeoutException) {
                Log.w(DEBUG_TAG, "通信タイムアウト", ex)
                // catch ブロックの結果(空文字列)
                ""
            } finally {
                conn.disconnect()
            }
        }
    }
}