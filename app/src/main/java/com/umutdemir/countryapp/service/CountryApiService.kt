package com.umutdemir.countryapp.service

import com.umutdemir.countryapp.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryApiService {

    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val api =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build().create(CountryAPI::class.java)
    fun getData() : Single<List<Country>>{
       return api.fetchData()
    }
}