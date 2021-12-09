package com.konfiotest

import com.konfiotest.network.*
import com.konfiotest.viewmodel.DogsViewModel
import com.konfiotest.viewmodel.usecases.DogsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.konfiotest.BuildConfig.BASE_URL

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        arrayListOf(
            viewModelModule,
            useCaseModule,
            repositoryModule,
            dataSourceModule,
            networkModule
        )
    )
}

val viewModelModule: Module = module {
    viewModel {
        DogsViewModel(
            dogsUseCase = get()
        )
    }
}

val useCaseModule: Module = module {
    factory { DogsUseCase(repository = get()) }
}

val repositoryModule: Module = module {
    single { RepositoryImpl(remoteDataSource = get()) as Repository }
}

val dataSourceModule: Module = module {
    single { RemoteDataSource(dogsApiService = get()) }
}

val networkModule: Module = module {
    single (named("DOGS_API")){ createNetworkClient(baseUrl = BASE_URL, debug = BuildConfig.DEBUG) }
    single { get<Retrofit>(named("DOGS_API")).create(DogsApiService::class.java) }
}