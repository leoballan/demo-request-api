package com.vila.demorequestwebapi.domain.models

sealed class WebResponse<out T>{
    data class Success<out T>(val data: List<Notebook>) : WebResponse<T>()
    data class Error(val exception: String) : WebResponse<Nothing>()
    object Loading : WebResponse<Nothing>()
    object InitState : WebResponse<Nothing>()
}
