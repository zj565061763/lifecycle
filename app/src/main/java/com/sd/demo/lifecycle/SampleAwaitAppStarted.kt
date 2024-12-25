package com.sd.demo.lifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.sd.lib.lifecycle.fAwaitAppStarted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SampleAwaitAppStarted : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycleScope.launch {
      while (true) {
        fAwaitAppStarted()
        logMsg { "loop" }
        delay(1_000)
      }
    }
  }
}