package br.com.alexf.minhastarefas.di

import br.com.alexf.minhastarefas.repositories.TasksRepository
import br.com.alexf.minhastarefas.ui.viewmodels.TaskFormViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.TasksListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModules = module {
    viewModelOf(::TaskFormViewModel)
    viewModelOf(::TasksListViewModel)
}

val storageModule = module {
    singleOf(::TasksRepository)
}