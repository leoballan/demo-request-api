package com.vila.demorequestwebapi.domain.repositories

import com.vila.demorequestwebapi.domain.models.Notebook
import com.vila.demorequestwebapi.domain.models.WebResponse
import kotlinx.coroutines.flow.Flow

interface WebRepository {
    suspend fun getNotebooks():WebResponse<List<Notebook>>
    fun getNotebooksByFlow(): Flow<WebResponse<List<Notebook>>>
}