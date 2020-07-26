package com.akameko.testforindeed.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akameko.testforindeed.dagger.App
import com.akameko.testforindeed.repository.pojos.Jeans
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    var repository = App.component!!.repository
    var jeansDatabase = App.component!!.jeansDatabase

    var jeansList = MutableLiveData<List<Jeans>>()

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadJeans() {
        val disposable = repository.jeans
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: List<Jeans> ->
                    jeansList.value = result
                    Log.d("123", "Items loaded!!")
                }) { throwable: Throwable? -> Log.d("123", "Items loading failed", throwable) }
        compositeDisposable.add(disposable)
    }

    fun addToFavourite(likedJeans: Jeans?) {
        jeansDatabase.jeansDao.insert(likedJeans)
    }

    fun removeFromFavourite(dislikedJeans: Jeans?) {
        jeansDatabase.jeansDao.delete(dislikedJeans)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}