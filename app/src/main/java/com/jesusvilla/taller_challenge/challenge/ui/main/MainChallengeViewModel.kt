package com.jesusvilla.taller_challenge.challenge.ui.main

import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusvilla.taller_challenge.challenge.ui.util.Event
import com.jesusvilla.taller_challenge.challenge.ui.util.MultipleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainChallengeViewModel @Inject constructor(): ViewModel() {

    private val data = MutableLiveData<Event<MultipleResponse<String, String>>>()
    private val error = MutableLiveData<Event<String>>()
    private val loading = MutableLiveData<Event<Boolean>>()

    fun login(username: String, password: String, hasError: Boolean = false) {
        viewModelScope.launch {
            loading.value = Event(true)
            delay(4000)
            loading.value = Event(false)
            if(!hasError) {
                data.value = Event(MultipleResponse(username, password))
            } else {
                error.value  = Event("error!!!")
            }
        }
    }

    @CheckResult
    fun onData(): LiveData<Event<MultipleResponse<String, String>>> {
        return data
    }

    @CheckResult
    fun onError(): LiveData<Event<String>> {
        return error
    }

    @CheckResult
    fun onLoading(): LiveData<Event<Boolean>> {
        return loading
    }
}