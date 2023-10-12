package com.umutdemir.countryapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.umutdemir.countryapp.model.Country
import com.umutdemir.countryapp.service.CountryApiService
import com.umutdemir.countryapp.service.CountryDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

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
                       storeInsSQLite(t)
                        Toast.makeText(getApplication(),"api",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                    }

                })
        )
    }


   fun storeInsSQLite(list : List<Country>){
       launch {
           val countryDao = CountryDatabase(getApplication()).countryDao()
           countryDao.deleteAllCountries()

           val idList = countryDao.insertAll(*list.toTypedArray())

           list.forEachIndexed{index, country ->
               country.id = idList[index].toInt()
           }

           showData(list)
       }
   }

    fun getDataFromSQLite(){

        launch {
            val countryDao = CountryDatabase(getApplication()).countryDao()
            val list = countryDao.getAllCountries()
            showData(list)

            Toast.makeText(getApplication(),"sql",Toast.LENGTH_SHORT).show()
        }
    }

    fun showData(countryList : List<Country>) {
        countries.value = countryList
        countryLoading.value = false
        countryError.value = false
    }

}