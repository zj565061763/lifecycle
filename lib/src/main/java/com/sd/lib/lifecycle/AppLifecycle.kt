package com.sd.lib.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.eventFlow
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope

/** App生命周期 */
val fAppLifecycle: Lifecycle
  get() = ProcessLifecycleOwner.get().lifecycle

/** App生命周期绑定的[CoroutineScope] */
val fAppLifecycleScope: CoroutineScope
  get() = ProcessLifecycleOwner.get().lifecycleScope

/** App生命周期是否至少处于[Lifecycle.State.STARTED]状态 */
val fAppIsStarted: Boolean
  get() = fAppLifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

suspend fun fAppOnStart(block: () -> Unit) {
  fAppLifecycle.eventFlow.collect {
    if (it == Lifecycle.Event.ON_START) {
      block()
    }
  }
}

suspend fun fAppOnStop(block: () -> Unit) {
  fAppLifecycle.eventFlow.collect {
    if (it == Lifecycle.Event.ON_STOP) {
      block()
    }
  }
}

suspend fun fAppRepeatOnStarted(
  block: suspend CoroutineScope.() -> Unit,
) {
  fAppLifecycle.repeatOnLifecycle(
    state = Lifecycle.State.STARTED,
    block = block,
  )
}

suspend fun fAwaitAppStarted(): Boolean = fAppLifecycle.fAwaitState(Lifecycle.State.STARTED)