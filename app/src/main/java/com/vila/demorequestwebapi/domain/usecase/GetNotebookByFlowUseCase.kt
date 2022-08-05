package com.vila.demorequestwebapi.domain.usecase

import com.vila.demorequestwebapi.domain.models.Notebook
import com.vila.demorequestwebapi.domain.models.WebResponse
import com.vila.demorequestwebapi.domain.repositories.WebRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotebookByFlowUseCase @Inject constructor(private val repository: WebRepository){
      operator fun invoke() : Flow<WebResponse<List<Notebook>>> {
        return repository.getNotebooksByFlow()
    }
}