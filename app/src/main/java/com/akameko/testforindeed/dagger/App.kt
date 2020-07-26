package com.akameko.testforindeed.dagger

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .repositoryModule(RepositoryModule())
                .roomModule(RoomModule(this))
                .build()
    }

    companion object {
        var component: AppComponent? = null
            private set
    }
}