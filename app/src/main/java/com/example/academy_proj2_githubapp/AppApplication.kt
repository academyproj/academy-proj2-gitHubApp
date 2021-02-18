package com.example.academy_proj2_githubapp

import android.app.Application
import com.example.academy_proj2_githubapp.di.AppComponent
import com.example.academy_proj2_githubapp.di.AppModule
import com.example.academy_proj2_githubapp.di.DaggerAppComponent

class AppApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}