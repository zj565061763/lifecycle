package com.sd.demo.lifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.sd.lib.lifecycle.fAppRepeatOnStarted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SampleAppRepeatOnStarted : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycleScope.launch {
      fAppRepeatOnStarted {
        loop()
      }
    }
  }

  private suspend fun loop() {
    while (true) {
      logMsg { "loop" }
      runCatching {
        delay(1_000)
      }.onFailure {
        logMsg { "loop error:$it" }
        throw it
      }
    }
  }
}