package com.androidstudio.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.androidstudio.androidapp.databinding.ActivityMainBinding
import sample.Analytics
import sample.AnalyticsSender
import sample.EventType
import sample.Parameters

class Sender : AnalyticsSender {

    var lastEvent: String = ""
        private set

    override fun sendAnalytics(name: String, parameters: Map<String, Any>) {
        val params = parameters.entries.fold("[") { result, item ->
            result + "${item.key} => ${item.value} "
        } + "]"
        lastEvent = "Sending analytics: $name with parameters: $params"
    }

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            val sender = Sender()
            Analytics(sender).prepareAnalytics(EventType.BACKGROUND_TRIGGER) {
                Parameters(
                    timestamp = System.currentTimeMillis(),
                    account = "test account"
                )
            }

            text = sender.lastEvent
        }
    }
}
