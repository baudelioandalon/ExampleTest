package com.boreal.test.core.utils.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class CUBaseViewModel : ViewModel() {

    protected fun executeFlow(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block.invoke()
        } catch (e: Exception) {
            e.stackTraceToString().log("ERROR_VMODEL")
        }
    }
}