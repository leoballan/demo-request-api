package com.vila.demorequestwebapi.data

import com.vila.demorequestwebapi.domain.models.Notebook
import retrofit2.http.GET
import retrofit2.http.Url

interface MockWebApi {

    @GET("list")
    suspend fun getNotebooks(): List<Notebook>

    @GET
    suspend fun getNotebook(@Url url:String):Notebook

}