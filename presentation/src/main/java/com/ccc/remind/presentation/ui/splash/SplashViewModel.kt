package com.ccc.remind.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.LoggedInUser
import com.ccc.remind.domain.usecase.GetLoggedInUserUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoggedInUserUserCase: GetLoggedInUserUserCase
) : ViewModel() {
    private val _isGetLoggedInUserDone = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean>
        get() = _isGetLoggedInUserDone

    private val _loggedInUser : MutableStateFlow<LoggedInUser?> = MutableStateFlow(null)

    val loggedInUser : StateFlow<LoggedInUser?>
        get() = _loggedInUser

    init {
        viewModelScope.launch {
            getLoggedInUserUserCase().collect {
                _loggedInUser.value = it
            }

            _isGetLoggedInUserDone.value = true
        }
    }



}