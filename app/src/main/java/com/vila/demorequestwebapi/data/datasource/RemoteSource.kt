package com.vila.demorequestwebapi.data.datasource

import com.vila.demorequestwebapi.data.MockWebApi
import com.vila.demorequestwebapi.di.IoDispatcher
import com.vila.demorequestwebapi.domain.models.Notebook
import com.vila.demorequestwebapi.domain.models.WebResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class RemoteSource @Inject constructor(
    private val webApi: MockWebApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher) {

    suspend fun getNotebooks(): WebResponse<List<Notebook>> {
        return withContext(dispatcher){
            delay(3000)
            try {
                val response = webApi.getNotebooks()
                 WebResponse.Success(response)
            } catch (e: Exception) {
                 WebResponse.Error(e.message!!)
            }
        }
    }

     fun getNotebookByFlow(): Flow<WebResponse<List<Notebook>>> = flow {
         emit(WebResponse.Loading)
        val response = webApi.getNotebooks()
        emit(WebResponse.Success(response))
    }.flowOn(dispatcher)
        .retryWhen() { cause, attempt ->
            delay(2000)
            return@retryWhen cause is IOException && attempt < 3
        }
         .catch {e ->
             emit(WebResponse.Error(e.message!!))
         }

}