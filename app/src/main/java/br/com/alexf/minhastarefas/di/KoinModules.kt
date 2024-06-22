package br.com.alexf.minhastarefas.di

import android.net.ConnectivityManager
import androidx.room.Room
import br.com.alexf.minhastarefas.authentication.FirebaseAuthRepository
import br.com.alexf.minhastarefas.connection.NetworkConnectionViewModel
import br.com.alexf.minhastarefas.database.MinhasTarefasDatabase
import br.com.alexf.minhastarefas.repositories.TasksRepository
import br.com.alexf.minhastarefas.repositories.UsersRepository
import br.com.alexf.minhastarefas.ui.viewmodels.AppViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.SignInViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.SignUpViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.TaskFormViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.TasksListViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val androidModule = module {
    single {
        androidContext()
            .getSystemService(ConnectivityManager::class.java)
                as ConnectivityManager
    }
}

val appModule = module {
    viewModelOf(::TaskFormViewModel)
    viewModelOf(::TasksListViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::NetworkConnectionViewModel)
}

val storageModule = module {
    singleOf(::TasksRepository)
    singleOf(::UsersRepository)
    singleOf(::FirebaseAuthRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            MinhasTarefasDatabase::class.java, "minhas-tarefas.db"
        ).build()
    }
    single {
        get<MinhasTarefasDatabase>().taskDao()
    }
}

val firebaseModule = module {
    single {
        Firebase.auth
    }
}