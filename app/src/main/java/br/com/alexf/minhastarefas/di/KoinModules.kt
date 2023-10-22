package br.com.alexf.minhastarefas.di

import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.alexf.minhastarefas.database.MinhasTarefasDatabase
import br.com.alexf.minhastarefas.repositories.TasksRepository
import br.com.alexf.minhastarefas.ui.viewmodels.TaskFormViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.TasksListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModules = module {
    viewModelOf(::TaskFormViewModel)
    viewModelOf(::TasksListViewModel)
}

val storageModule = module {
    val databaseName = "minhas-tarefas"
    singleOf(::TasksRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            MinhasTarefasDatabase::class.java, databaseName
        ).build()
    }
    single {
        get<MinhasTarefasDatabase>().tasksDao()
    }
}