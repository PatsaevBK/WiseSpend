package info.javaway.wiseSpend.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

expect val platformModule: Module

inline fun <reified T: Any> getKoinInstance(qualifier: Qualifier? = null): T {
    return object : KoinComponent {
        val value by inject<T>(qualifier)
    }.value
}

fun initKoin(appModule: Module = module {  }) = startKoin {
    modules(
        CoreModules.deviceInfo,
        CoreModules.coroutineContext,
        StorageModule.settingsManager,
        StorageModule.db,
        StorageModule.dao,
        RepositoriesModule.repository,
        ViewModelModule.viewModels,
        ComponentsFactoryModule.componentFactory,
        platformModule,
        appModule,
        NetworkModule.json,
        NetworkModule.httpClient,
        NetworkModule.api
    )
}