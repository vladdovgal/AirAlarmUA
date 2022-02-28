package com.alarmua.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _shortMessage: MutableLiveData<String> = MutableLiveData("")
    val shortMessage: LiveData<String> = _shortMessage

    private fun loading(loading: Boolean) {
        viewModelScope.launch {
            _isLoading.value = loading
        }
    }

    suspend fun <T> withLoading(startWith: Boolean = true, block: suspend () -> T): T {
        loading(startWith)
        val result = block()
        loading(false)
        return result
    }

    fun showToast(message: String) {
        viewModelScope.launch {
            _shortMessage.value = message
        }
    }
}