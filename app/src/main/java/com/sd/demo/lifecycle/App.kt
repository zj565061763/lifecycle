package com.sd.demo.lifecycle

import android.app.Application
import com.sd.lib.lifecycle.fAppLifecycleScope
import com.sd.lib.lifecycle.fAppOnStart
import com.sd.lib.lifecycle.fAppOnStop
import kotlinx.coroutines.launch

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    fAppLifecycleScope.launch {
      fAppOnStart {
        logMsg { "fAppOnStart" }
      }
    }
    fAppLifecycleScope.launch {
      fAppOnStop {
        logMsg { "fAppOnStop" }
      }
    }
  }
}