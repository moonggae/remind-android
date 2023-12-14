package com.ccc.remind.presentation.ui.mindPost

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.usecase.PostImagesUseCase
import com.ccc.remind.domain.usecase.mind.GetMindCardsUseCase
import com.ccc.remind.domain.usecase.post.DeleteMindUseCase
import com.ccc.remind.domain.usecase.post.GetMindPostUseCase
import com.ccc.remind.domain.usecase.post.PostMindUseCase
import com.ccc.remind.domain.usecase.post.UpdateMindUseCase
import com.ccc.remind.presentation.MyApplication
import com.ccc.remind.presentation.ui.component.model.MindFilter
import com.ccc.remind.presentation.util.extensions.toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MindPostViewModel @Inject constructor(
    private val getMindCards: GetMindCardsUseCase,
    private val postImages: PostImagesUseCase,
    private val postMind: PostMindUseCase,
    private val updateMind: UpdateMindUseCase,
    private val deleteMind: DeleteMindUseCase,
    private val getMindPost: GetMindPostUseCase
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

    private val _initialCardIds = MutableStateFlow(emptyList<Int>())

    init {
        initMindCards()
        initUiState()
    }

    private fun initUiState() {
        viewModelScope.launch {
            _uiState.update { state ->
                MindPostUiState(
                    mindFilters = MindFilter.values().filter { it.value == null || it.value!! > 0},
                    selectedMindFilters = listOf(MindFilter.ALL),
                    mindCards = state.mindCards,
                    selectedMindCards = state.mindCards.filter { card -> _initialCardIds.value.contains(card.id) },
                    selectCardScreenBackStackEntryId = state.selectCardScreenBackStackEntryId
                )
            }

            _initialCardIds.update { emptyList() }
        }
    }

    fun updateBackStackEntryId(id: String?) {
        if(id == _uiState.value.selectCardScreenBackStackEntryId) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectCardScreenBackStackEntryId = id
                )
            }
            initUiState()
        }
    }

    fun setInitialCard(cardIds: List<Int>) {
        viewModelScope.launch {
            _initialCardIds.update {
                cardIds
            }
        }
    }

    private fun initMindCards() {
        viewModelScope.launch {
            getMindCards().collect { mindCards ->
                _uiState.update {
                    it.copy(
                        mindCards = mindCards
                    )
                }
            }
        }
    }

    fun updateMindCardFilter(filter: MindFilter) {
        when {
            filter == MindFilter.ALL ->
                _uiState.update {
                    it.copy(
                        selectedMindFilters = listOf(MindFilter.ALL)
                    )
                }

            filter.value == null -> _uiState.update {
                it.copy(
                    selectedMindFilters = it.selectedMindFilters
                        .toMutableList()
                        .minus(MindFilter.ALL)
                        .toggle(filter)
                )
            }

            else -> {
                val updateFilter = _uiState.value.mindFilters.toMutableList()
                val updatedSelectedFilter = _uiState.value.selectedMindFilters
                    .filter { it != filter }
                    .filter { it != MindFilter.ALL }
                    .toMutableList()
                val currentFilterIndex = updateFilter.indexOf(filter)
                val newFilter =
                    if(_uiState.value.selectedMindFilters.contains(filter))
                        MindFilter.values().find { it.value != filter.value && it.text == filter.text }
                    else
                        MindFilter.values().find { it.value == filter.value && it.text == filter.text }
                newFilter?.let {
                    updateFilter[currentFilterIndex] = it
                    updatedSelectedFilter.remove(filter)
                    updatedSelectedFilter.add(it)
                }

                _uiState.update {
                    it.copy(
                        mindFilters = updateFilter,
                        selectedMindFilters = updatedSelectedFilter
                    )
                }
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
            try {
                val mindCards = _uiState.value.selectedMindCardsMap
                val images = _uiState.value.uploadedPhotos.map { it.id }.toList()
                val memo = _uiState.value.memo

                val response: Flow<MindPost> =
                    if (_uiState.value.openedPost == null) {
                        postMind(
                            mindCards, images, memo
                        )
                    } else {
                        updateMind(
                            id = _uiState.value.openedPost!!.id,
                            mindCards, images, memo
                        )
                    }

                response.collect { post ->
                    _uiState.update {
                        it.copy(openedPost = post)
                    }

                    onSuccess(post)
                }
            } catch (e:Exception) {
                Log.e(TAG, "submitMind: $e", )
            }
            
        }
    }

    fun deleteMind(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            deleteMind(_uiState.value.openedPost!!.id).collect {
                _uiState.update {
                    it.copy(
                        openedPost = null
                    )
                }
                onSuccess()
            }
        }
    }

    fun setOpenedMindPost(id: Int) {
        viewModelScope.launch {
            getMindPost(id).collect { post ->
                setOpenedMindPost(post)
            }
        }
    }

    private fun setOpenedMindPost(post: MindPost?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    openedPost = post
                )
            }
        }
    }

    fun setViewType(postViewType: PostViewType) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    viewType = postViewType
                )
            }
        }
    }

    fun initEditPost(post: MindPost) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    selectedMindCards = post.cards.map { it.card },
                    memo = post.memo?.text,
                    uploadedPhotos = post.images,
                )
            }
        }
    }
}