package com.ccc.remind.presentation.base

import androidx.lifecycle.ViewModel

open class ComposeLifecycleViewModel: ViewModel() {
    open fun onCreate() {
        // handle onCreate event
    }

    open fun onStart() {
        // handle onStart event
    }

    open fun onResume() {
        // handle onResume event
    }

    open fun onPause() {
        // handle onPause event
    }

    open fun onStop() {
        // handle onStop event
    }

    open fun onDispose() {
        // handle onDispose event
    }
}