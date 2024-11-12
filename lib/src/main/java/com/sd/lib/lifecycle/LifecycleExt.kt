package com.sd.lib.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlin.coroutines.cancellation.CancellationException

/**
 * [Lifecycle.fAwaitState]
 */
suspend fun LifecycleOwner.fAwaitState(
   state: Lifecycle.State = Lifecycle.State.STARTED,
): Boolean {
   return lifecycle.fAwaitState(state = state)
}

/**
 * 如果当前状态小于[state]，则挂起直到满足[state]
 * @return true-调用时已经满足[state]；false-调用时还不满足[state]，挂起等待之后满足[state]
 */
suspend fun Lifecycle.fAwaitState(
   state: Lifecycle.State = Lifecycle.State.STARTED,
): Boolean {
   require(state != Lifecycle.State.DESTROYED)
   if (currentState == Lifecycle.State.DESTROYED) throw CancellationException()
   if (currentState.isAtLeast(state)) return true
   currentStateFlow.first { it.isAtLeast(state) }
   return false
}

suspend fun LifecycleOwner.fRepeatOnStarted(
   block: suspend CoroutineScope.() -> Unit,
) {
   lifecycle.fRepeatOnStarted(block)
}

suspend fun Lifecycle.fRepeatOnStarted(
   block: suspend CoroutineScope.() -> Unit,
) {
   repeatOnLifecycle(
      state = Lifecycle.State.STARTED,
      block = block,
   )
}