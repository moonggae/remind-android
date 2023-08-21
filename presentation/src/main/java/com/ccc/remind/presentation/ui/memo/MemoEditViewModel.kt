package com.ccc.remind.presentation.ui.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.memo.DeleteLikeUseCase
import com.ccc.remind.domain.usecase.memo.DeleteMemoUseCase
import com.ccc.remind.domain.usecase.memo.GetMemoUseCase
import com.ccc.remind.domain.usecase.memo.PostCommentUseCase
import com.ccc.remind.domain.usecase.memo.PostLikeUseCase
import com.ccc.remind.domain.usecase.memo.PostMemoUseCase
import com.ccc.remind.domain.usecase.memo.UpdateMemoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoEditViewModel @Inject constructor(
    private val postMemo: PostMemoUseCase,
    private val getMemo: GetMemoUseCase,
    private val updateMemo: UpdateMemoUseCase,
    private val deleteMemo: DeleteMemoUseCase,
    private val postComment: PostCommentUseCase,
    private val postLike: PostLikeUseCase,
    private val deleteLike: DeleteLikeUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(MemoEditUiStatus())
    val uiStatus: StateFlow<MemoEditUiStatus>
        get() = _uiState

    fun initMemo(postId: Int, memoId: Int?) {
        viewModelScope.launch {
            if(memoId == null || memoId < 0) {
                _uiState.update {
                    MemoEditUiStatus(
                        postId = postId
                    )
                }
            }
            else {
                getMemo(memoId).collect { memo ->
                    _uiState.update {
                        MemoEditUiStatus(
                            postId = postId,
                            openedMemo = memo,
                            memoText = memo?.text ?: ""
                        )
                    }
                }
            }
        }
    }

    fun updateMemoText(text: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    memoText = text
                )
            }
        }
    }

    fun updateCommentText(text: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    commentText = text
                )
            }
        }
    }

    fun submitPostMemo() {
        viewModelScope.launch {
            postMemo(
                postId = _uiState.value.postId!!,
                text = _uiState.value.memoText
            ).collect { postedMemo ->
                _uiState.update {
                    it.copy(
                        openedMemo = postedMemo
                    )
                }
            }
        }
    }

    fun submitUpdateMemo() {
        _uiState.value.openedMemo?.let { openedMemo ->
            viewModelScope.launch {
                updateMemo(openedMemo.id, _uiState.value.memoText).collect { updatedMemo ->
                    _uiState.update {
                        it.copy(
                            openedMemo = updatedMemo,
                            memoText = updatedMemo.text,
                            commentText = "",
                        )
                    }
                }
            }
        }
    }

    fun submitDeleteMemo(onSuccess: () -> Unit = {}) {
        _uiState.value.openedMemo?.let { openedMemo ->
            viewModelScope.launch {
                deleteMemo(openedMemo.id)
                onSuccess()
            }
        }
    }

    fun submitPostComment() {
        if(_uiState.value.commentText.isEmpty()) return
        _uiState.value.openedMemo?.let { openedMemo ->
            viewModelScope.launch {
                postComment(openedMemo.id, _uiState.value.commentText).collect { postedComment ->
                    _uiState.update {
                        it.copy(
                            openedMemo = it.openedMemo?.copy(
                                comments = it.openedMemo.comments.plus(postedComment)
                            ),
                            commentText = ""
                        )
                    }
                }
            }
        }
    }

    fun submitPostLike(commentId: Int) {
        _uiState.value.openedMemo?.let {
            viewModelScope.launch {
                postLike(commentId).collect { postedLike ->
                    _uiState.update {
                        it.copy(
                            openedMemo = it.openedMemo?.copy(
                                comments = it.openedMemo.comments.map { comment ->
                                    if(comment.id == commentId) {
                                        comment.copy(
                                            likes = comment.likes.plus(postedLike)
                                        )
                                    } else {
                                        comment
                                    }
                                }
                            )
                        )
                    }
                }
            }
        }
    }

    fun submitDeleteLike(id: Int) {
        _uiState.value.openedMemo?.let {
            viewModelScope.launch {
                deleteLike(id)
                _uiState.update {
                    it.copy(
                        openedMemo = it.openedMemo?.copy(
                            comments = it.openedMemo.comments.map { comment ->
                                comment.copy(
                                    likes = comment.likes.filter { like -> like.id != id }
                                )
                            }
                        )
                    )
                }
            }
        }
    }
}