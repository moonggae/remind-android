package com.ccc.remind.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.user.LoggedInUser
import com.ccc.remind.domain.usecase.GetLoggedInUserUserCase
import com.ccc.remind.domain.usecase.GetMindCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoggedInUserUserCase: GetLoggedInUserUserCase,
    private val getMindCards: GetMindCardsUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "SplashViewModel"
    }


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

//            getMindCards().collect {
//                it.forEach { card ->
//                    Log.d(TAG, "SplashViewModel -  - card: ${card}")
//                }
//            }

            _isGetLoggedInUserDone.value = true
        }
    }



}