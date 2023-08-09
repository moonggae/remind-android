package com.ccc.remind.presentation.ui.mindPost

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.usecase.GetMindCardsUseCase
import com.ccc.remind.domain.usecase.PostImagesUseCase
import com.ccc.remind.domain.usecase.PostMindUseCase
import com.ccc.remind.presentation.MyApplication
import com.ccc.remind.presentation.ui.component.model.MindFilter
import com.ccc.remind.presentation.util.toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MindPostViewModel @Inject constructor(
    private val getMindCards: GetMindCardsUseCase,
    private val postImages: PostImagesUseCase,
    private val postMind: PostMindUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "MindPostViewModel"
    }
    
    private val _uiState = MutableStateFlow(
        MindPostUiState(
            selectedMindFilters = listOf(MindFilter.ALL),
            mindCards = emptyList()
        )
    )

    val uiState: StateFlow<MindPostUiState>
        get() = _uiState


    init {
        initMindCards()
    }

    private fun initMindCards() {
        viewModelScope.launch {
            getMindCards().collect { mindCards ->
                _uiState.update {
                    it.copy(mindCards = mindCards)
                }
            }
        }
    }

    fun updateMindCardFilter(filter: MindFilter) {
        when(filter) {
            MindFilter.ALL ->
                _uiState.update {
                    it.copy(
                        selectedMindFilters = listOf(MindFilter.ALL)
                    )
                }
            else ->
                _uiState.update {
                    it.copy(
                        selectedMindFilters = it.selectedMindFilters
                            .toMutableList()
                            .minus(MindFilter.ALL)
                            .toggle(filter)
                    )
                }
        }
    }

    fun updateMindCard(mindCard: MindCard) {
        _uiState.update {
            it.copy(
                selectedMindCards = it.selectedMindCards.toggle(mindCard)
            )
        }
    }

    fun removeAllSelectedMindCards() {
        _uiState.update {
            it.copy(
                selectedMindCards = emptyList()
            )
        }
    }

    fun uploadPhotos(uris: List<Uri>) {
        if(uris.isEmpty()) return
        viewModelScope.launch {
            val uploadedImages = postImages(MyApplication.applicationContext(), uris)
            _uiState.update {
                it.copy(
                    uploadedPhotos = it.uploadedPhotos.plus(uploadedImages)
                )
            }
        }
    }

    fun deleteUploadedPhoto(imageFile: ImageFile) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    uploadedPhotos = it.uploadedPhotos.minus(imageFile)
                )
            }

            // todo: request to delete to server
        }
    }


    fun updateMemo(text: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    memo = text
                )
            }
        }
    }


    fun submitMind(onSuccess: (postedMind: MindPost) -> Unit = {}) {
        viewModelScope.launch {
            val mindCards = mutableMapOf<MindCard, MindCardSelectType>()
            _uiState.value.selectedMindCards.forEach {
                mindCards[it] =
                    if (_uiState.value.selectedMindCards.indexOf(it) == 0) MindCardSelectType.MAIN
                    else MindCardSelectType.SUB
            }

            try {
                postMind(
                    mindCards = mindCards,
                    images = _uiState.value.uploadedPhotos.map { it.id }.toList(),
                    memo = _uiState.value.memo
                ).collect { post ->
                    _uiState.update {
                        it.copy(postedMind = post)
                    }

                    onSuccess(post)
                }
            } catch (e:Exception) {
                Log.e(TAG, "submitMind: $e", )
            }
            
        }
    }
}