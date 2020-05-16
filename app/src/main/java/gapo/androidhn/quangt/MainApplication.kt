package gapo.androidhn.quangt

import android.app.Application
import gapo.androidhn.quangt.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@MainApplication)
            androidLogger(Level.DEBUG)
            // declare modules
            modules(
                listOf(
                    viewModelModule, apiModule, netModule, databaseModule, repositoryModule
                )
            )
        }
    }
}