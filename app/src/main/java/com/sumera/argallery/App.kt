package com.sumera.argallery

import android.app.Activity
import android.app.Application
import com.sumera.argallery.injection.component.DaggerApplicationComponent
import com.sumera.argallery.tools.log.StateLogger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var stateLogger: StateLogger

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        initializeApplicationComponent()

        if (BuildConfig.DEBUG) {
            stateLogger.init()
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    private fun initializeApplicationComponent() {
        DaggerApplicationComponent
                .builder()
                .create(this)
                .inject(this)
    }
}
