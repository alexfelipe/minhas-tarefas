package br.com.alexf.minhastarefas.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import br.com.alexf.minhastarefas.database.MinhasTarefasDatabase
import br.com.alexf.minhastarefas.repositories.TasksRepository
import br.com.alexf.minhastarefas.repositories.UsersRepository
import br.com.alexf.minhastarefas.ui.viewmodels.SignInViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.SignUpViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.TaskFormViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.TasksListViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.UsersViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::TaskFormViewModel)
    viewModelOf(::TasksListViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::UsersViewModel)
}

val storageModule = module {
    singleOf(::TasksRepository)
    single {
        UsersRepository(
            get(),
            Dispatchers.IO
        )
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            MinhasTarefasDatabase::class.java, "minhas-tarefas.db"
        ).build()
    }
    single {
        get<MinhasTarefasDatabase>().taskDao()
    }
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("user_preferences")
        }
    }
}