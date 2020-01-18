package com.example.myapplication

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.myapplication.adapters.OffsetDateTimeAdapter
import com.example.myapplication.api.RemoteService
import com.example.myapplication.api.SampleData1
import com.squareup.moshi.Moshi

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val remoteService by lazy {
        val converter = Moshi.Builder()
            .add(OffsetDateTimeAdapter())
            .build()

        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/luanmm/moshi-test/master/api.json")
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
                // To get the result in the "SampleData1" format, we use:
                val sampleData1 = remoteService.test<SampleData1>()

                Log.d(TAG, "Field 1: ${sampleData1.data?.field1}")
                Log.d(TAG, "Field 2: ${sampleData1.data?.field2}")
                Log.d(TAG, "Field 3: ${sampleData1.data?.field3}")

                // And, if we need other structure, we use:
                //val sampleData2 = remoteService.test<SampleData2>()

                //sampleData2.data?.field4
                //sampleData2.data?.field5
                //sampleData2.data?.field6
            }
        }
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
