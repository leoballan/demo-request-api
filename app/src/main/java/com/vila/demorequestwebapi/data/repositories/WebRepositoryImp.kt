package com.vila.demorequestwebapi.data.repositories

import com.vila.demorequestwebapi.data.datasource.RemoteSource
import com.vila.demorequestwebapi.domain.models.Notebook
import com.vila.demorequestwebapi.domain.models.WebResponse
import com.vila.demorequestwebapi.domain.repositories.WebRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WebRepositoryImp
@Inject
constructor(private val dataSource: RemoteSource)
    : WebRepository {

    override suspend fun getNotebooks(): WebResponse<List<Notebook>> {
        return dataSource.getNotebooks()
    }

    override fun getNotebooksByFlow(): Flow<WebResponse<List<Notebook>>> {
        return dataSource.getNotebookByFlow()
    }

}