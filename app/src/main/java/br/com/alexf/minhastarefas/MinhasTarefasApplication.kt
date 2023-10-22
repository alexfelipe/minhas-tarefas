package br.com.alexf.minhastarefas

import android.app.Application
import br.com.alexf.minhastarefas.di.appModule
import br.com.alexf.minhastarefas.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MinhasTarefasApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MinhasTarefasApplication)
            modules(
                appModule,
                storageModule
            )
        }
    }
}