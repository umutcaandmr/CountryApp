package com.umutdemir.countryapp.service

import com.umutdemir.countryapp.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountryAPI {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun fetchData() : Single<List<Country>>
}