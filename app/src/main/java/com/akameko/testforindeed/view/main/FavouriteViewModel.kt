package com.akameko.testforindeed.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.akameko.testforindeed.dagger.App
import com.akameko.testforindeed.repository.pojos.Jeans

class FavouriteViewModel : ViewModel() {
    private var jeansDatabase = App.component!!.jeansDatabase
    lateinit var likedJeansList: LiveData<List<Jeans>>

    fun initViewModel(){
        likedJeansList = jeansDatabase.jeansDao.allItemsLiveData
    }

}
