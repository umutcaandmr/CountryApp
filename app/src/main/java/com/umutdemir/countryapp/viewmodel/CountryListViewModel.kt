package com.umutdemir.countryapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.umutdemir.countryapp.model.Country
import com.umutdemir.countryapp.service.CountryApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class CountryListViewModel(application: Application) : BaseViewModel(application) {


    val countries = MutableLiveData<List<Country>>()
    val countryLoading = MutableLiveData<Boolean>()
    val countryError = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    private val countryApiService = CountryApiService()

    fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            countryApiService.getData().observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                       showData(t)
                    }

                    override fun onError(e: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        )
    }


    fun getDataFromSQLite() {

    }

    fun showData(countryList : List<Country>) {
        countries.value = countryList
        countryLoading.value = false
        countryError.value = false
    }

}