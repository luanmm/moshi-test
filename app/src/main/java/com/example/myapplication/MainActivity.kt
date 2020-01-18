package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.adapters.OffsetDateTimeAdapter
import com.example.myapplication.api.RemoteService
import com.example.myapplication.api.ResultJsonAdapter
import com.example.myapplication.api.SampleData1
import com.example.myapplication.api.Result
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val converter by lazy {
        Moshi.Builder()
            .add(OffsetDateTimeAdapter())
            .build()
    }

    private val remoteService by lazy {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/luanmm/moshi-test/master/app/src/main/res/raw/")
            .addConverterFactory(MoshiConverterFactory.create(converter))
            .build()
            .create(RemoteService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            GlobalScope.launch {
                try {
                    // Test with Moshi
                    handleWithMoshiDirectly()

                    // Test with Moshi + Retrofit
                    handleWithMoshiRetrofitConverter()
                } catch (ex: Exception) {
                    Log.e(TAG, "Error handling JSON data: ", ex)
                }
            }
        }
    }

    private fun handleWithMoshiDirectly() {
        val reader = resources.openRawResource(R.raw.api).bufferedReader()

        // The way we need to invoke the adapter seems to be overkill (passing the array of types),
        // but it works nicely
        val adapter = ResultJsonAdapter<SampleData1>(converter, arrayOf(SampleData1::class.java))
        val result = adapter.fromJson(reader.readText())
        if (result != null) {
            logSampleData1(result)
        }

        reader.close()
    }

    private suspend fun handleWithMoshiRetrofitConverter() {
        // To get the result in the "SampleData1" format, we use:
        val sampleData1 = remoteService.test<SampleData1>()
        logSampleData1(sampleData1)

        // And, if we need other structure, we use:
        //val sampleData2 = remoteService.test<SampleData2>()

        //sampleData2.data?.field4
        //sampleData2.data?.field5
        //sampleData2.data?.field6
    }

    private fun logSampleData1(sampleData: Result<SampleData1>) {
        Log.d(TAG, "Field 1: ${sampleData.data?.field1}")
        Log.d(TAG, "Field 2: ${sampleData.data?.field2}")
        Log.d(TAG, "Field 3: ${sampleData.data?.field3}")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        const val TAG = "MainActivity"
    }
}
