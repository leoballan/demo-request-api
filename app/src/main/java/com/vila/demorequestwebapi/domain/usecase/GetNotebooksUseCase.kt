package com.vila.demorequestwebapi.domain.usecase

import com.vila.demorequestwebapi.domain.models.Notebook
import com.vila.demorequestwebapi.domain.models.WebResponse
import com.vila.demorequestwebapi.domain.repositories.WebRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetNotebooksUseCase
@Inject
constructor(private val repository: WebRepository){

    suspend operator fun invoke():WebResponse<List<Notebook>>{
        return repository.getNotebooks()
    }
}