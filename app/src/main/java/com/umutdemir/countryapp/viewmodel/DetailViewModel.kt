package com.umutdemir.countryapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.umutdemir.countryapp.model.Country
import com.umutdemir.countryapp.service.CountryDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    var countryDetail = MutableLiveData<Country>()


    fun getDataFromRoom(id : Int){
        launch {
            val countryDao = CountryDatabase(getApplication()).countryDao()
            val country = countryDao.getCountry(id)

            countryDetail.value = country
        }
    }




}