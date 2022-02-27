package com.alarmua.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage: MutableLiveData<String> = MutableLiveData("")
    val toastMessage: LiveData<String> = _toastMessage

    fun loading(loading: Boolean) {
        viewModelScope.launch {
            _isLoading.value = loading
        }
    }

    fun showToast(message: String) {
        viewModelScope.launch {
            _toastMessage.value = message
        }
    }
}