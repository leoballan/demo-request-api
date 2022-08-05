package com.vila.demorequestwebapi.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vila.demorequestwebapi.domain.models.Notebook
import com.vila.demorequestwebapi.domain.models.WebResponse
import com.vila.demorequestwebapi.domain.usecase.GetNotebookByFlowUseCase
import com.vila.demorequestwebapi.domain.usecase.GetNotebooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNotebooksUseCase: GetNotebooksUseCase,
    private val getNotebookByFlowUseCase: GetNotebookByFlowUseCase) :
    ViewModel() {

    init {
        fetchNoteBooksByFlow()
    }

    private val _notebookStateFlow =
        MutableStateFlow<WebResponse<List<Notebook>>>(WebResponse.InitState)
    val notebookStateFlow = _notebookStateFlow.asStateFlow()

    private val _notebookSharedFlow =
        MutableSharedFlow<WebResponse<List<Notebook>>>()
    val notebooksharedFlow = _notebookSharedFlow.asSharedFlow()

    private val _selectedNotebookState = MutableStateFlow(Notebook())
    val selectedNotebookState = _selectedNotebookState.asStateFlow()

    private val _progressBarShared = MutableSharedFlow<Boolean>()
    val progressBarShared = _progressBarShared.asSharedFlow()

    private val _notebookList = MutableStateFlow<List<Notebook>>(emptyList())
    val notebookList = _notebookList.asStateFlow()


    fun setProgressBarShared(state:Boolean){
        viewModelScope.launch {
            Log.d("webservice"," progress state..............$state")
            _progressBarShared.emit(state)
        }
    }

    fun setListNotebooks(list:List<Notebook>){
        _notebookList.value = list
    }

    fun fetchNoteBooks() {
        viewModelScope.launch {
            _notebookStateFlow.value = WebResponse.Loading
            _notebookStateFlow.value = getNotebooksUseCase.invoke()
        }
    }

    fun fetchNoteBooksByFlow(){
        viewModelScope.launch {
            getNotebookByFlowUseCase.invoke().collect {
                _notebookSharedFlow.emit(it)
            }
        }
    }

    fun resetNotebookState(){
        _notebookStateFlow.value = WebResponse.InitState
    }

    fun setSelectedNotebook(notebooK: Notebook) {
        _selectedNotebookState.value = notebooK
    }

}