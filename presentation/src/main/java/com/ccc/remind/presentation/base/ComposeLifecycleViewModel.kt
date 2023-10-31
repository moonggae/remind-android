package com.ccc.remind.presentation.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class ComposeLifecycleViewModel: ViewModel() {
    private val events: MutableList<Pair<Lifecycle.Event, () -> Unit>> = mutableListOf()

    fun addOn(lifecycleEvent: Lifecycle.Event, call: () -> Unit): Pair<Lifecycle.Event, () -> Unit> {
        val event = lifecycleEvent to call
        events.add(event)
        return event
    }

    fun removeEvent(event: Pair<Lifecycle.Event, () -> Unit>) {
        events.remove(event)
    }

    fun call(lifecycleEvent: Lifecycle.Event) {
        when(lifecycleEvent) {
            Lifecycle.Event.ON_CREATE -> this.onCreate()
            Lifecycle.Event.ON_RESUME -> this.onResume()
            Lifecycle.Event.ON_PAUSE -> this.onPause()
            Lifecycle.Event.ON_START -> this.onStart()
            Lifecycle.Event.ON_STOP -> this.onStop()
            Lifecycle.Event.ON_DESTROY -> this.onDispose()
            else -> {}
        }

        events.filter { event ->
            event.first == lifecycleEvent
        }.forEach { event ->
            event.second.invoke()
        }
    }


    fun <T, R> addWatchFlow(
        start: Lifecycle.Event = Lifecycle.Event.ON_RESUME,
        end: Lifecycle.Event = Lifecycle.Event.ON_PAUSE,
        triggerFlow: Flow<T>,
        flowProvider: (T, CoroutineScope) -> Flow<R>?,
        onCollect: (R) -> Unit
    ) {
        var collectionJob: Job? = null
        var startEvent: Pair<Lifecycle.Event, () -> Unit>? = null
        var endEvent: Pair<Lifecycle.Event, () -> Unit>? = null

        viewModelScope.launch {
            triggerFlow.collect { inputData ->
                startEvent?.let { removeEvent(it) }
                endEvent?.let { removeEvent(it) }
                collectionJob?.cancel()

                flowProvider(inputData, this)?.let { provider ->
                    startEvent = addOn(start) {
                        collectionJob = viewModelScope.launch {
                            provider.collect {
                                onCollect(it)
                            }
                        }
                    }

                    endEvent = addOn(end) {
                        collectionJob?.cancel()
                        collectionJob = null
                    }
                }
            }
        }
    }

    open fun onCreate() {}
    open fun onStart() {}
    open fun onResume() {}
    open fun onPause() {}
    open fun onStop() {}
    open fun onDispose() {}
}