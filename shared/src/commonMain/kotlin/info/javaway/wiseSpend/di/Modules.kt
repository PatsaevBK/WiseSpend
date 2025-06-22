package info.javaway.wiseSpend.di

import info.javaway.wiseSpend.db.AppDb
import info.javaway.wiseSpend.extensions.appLog
import info.javaway.wiseSpend.features.accounts.data.AccountDao
import info.javaway.wiseSpend.features.accounts.data.AccountRepository
import info.javaway.wiseSpend.features.categories.data.CategoriesRepository
import info.javaway.wiseSpend.features.categories.data.CategoryDao
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponentImpl
import info.javaway.wiseSpend.features.events.data.EventDao
import info.javaway.wiseSpend.features.events.data.EventsRepository
import info.javaway.wiseSpend.features.events.list.EventsListComponent
import info.javaway.wiseSpend.features.events.list.EventsListComponentImpl
import info.javaway.wiseSpend.features.settings.SettingsComponent
import info.javaway.wiseSpend.features.settings.SettingsComponentImpl
import info.javaway.wiseSpend.features.settings.child.auth.AuthComponent
import info.javaway.wiseSpend.features.settings.child.auth.AuthComponentImpl
import info.javaway.wiseSpend.features.settings.child.auth.child.register.RegisterComponent
import info.javaway.wiseSpend.features.settings.child.auth.child.signIn.SignInComponent
import info.javaway.wiseSpend.features.settings.child.auth.child.signIn.SignInComponentImpl
import info.javaway.wiseSpend.features.settings.child.sync.SyncComponent
import info.javaway.wiseSpend.features.settings.child.sync.SyncComponentImpl
import info.javaway.wiseSpend.network.AppApi
import info.javaway.wiseSpend.network.serializers.DateSerializer
import info.javaway.wiseSpend.network.serializers.DateTimeSerializer
import info.javaway.wiseSpend.platform.DeviceInfo
import info.javaway.wiseSpend.root.RootComponent
import info.javaway.wiseSpend.root.RootComponentImpl
import info.javaway.wiseSpend.storage.DbAdapters
import info.javaway.wiseSpend.storage.SettingsManager
import info.javaway.wiseSpend.uiLibrary.ui.calendar.DatePickerViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.module
import org.koin.ext.getFullName

object CoreModules {
    val deviceInfo = module {
        single { DeviceInfo() }
    }
    val coroutineContext = module {
        factory { Dispatchers.IO + SupervisorJob() }
    }
}

object StorageModule {
    val settingsManager = module {
        single { SettingsManager(get()) }
    }

    val db = module {
        single {
            AppDb(
                driver = get(),
                CategoryTableAdapter = DbAdapters.categoryTableAdapter,
                EventTableAdapter = DbAdapters.eventTableAdapter,
                AccountTableAdapter = DbAdapters.accountTableAdapter,
            )
        }
    }

    val dao = module {
        single { CategoryDao(get(), get()) }
        single { EventDao(get(), get()) }
        single { AccountDao(get(), get()) }
    }
}

object NetworkModule {
    val json = module {
        single {
            Json {
                encodeDefaults = false
                isLenient = true // не очень хорошие json кушает
                ignoreUnknownKeys = true // не знает ключ не падает
                explicitNulls = false // нет в json nullable поля не падаем
                prettyPrint = true
                serializersModule = SerializersModule {
                    contextual(LocalDateTime::class, DateTimeSerializer)
                    contextual(LocalDate::class, DateSerializer)
                }
            }
        }
    }

    val httpClient = module {
        single {
            HttpClient(CIO) {
                expectSuccess = false
                install(ContentNegotiation) {
                    json(get())
                }
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            appLog(message)
                        }
                    }
                }
                install(HttpRequestRetry) {
                    retryOnServerErrors(maxRetries = 3)
                    exponentialDelay()
                }
            }
        }
    }

    val api = module {
        single { AppApi(get(), get()) }
    }
}

object RepositoriesModule {
    val repository = module {
        single { CategoriesRepository(get()) }
        single { EventsRepository(get()) }
        single { AccountRepository(get()) }
    }
}

object ViewModelModule {
    val viewModels = module {
        single(DatePickerSingleQualifier) { DatePickerViewModel() }
        factory(DatePickerFactoryQualifier) { DatePickerViewModel() }
    }
}

object ComponentsFactoryModule {
    val componentFactory = module {
        single<RootComponent.Factory> { RootComponentImpl.Factory(get(), get(), get(), get())  }
        factory<SettingsComponent.Factory> { SettingsComponentImpl.Factory(get(), get(), get(), get()) }
        factory<CategoriesListComponent.Factory> { CategoriesListComponentImpl.Factory(get()) }
        factory<EventsListComponent.Factory> { EventsListComponentImpl.Factory(get(), get(), get()) }
        factory<AuthComponent.Factory> { AuthComponentImpl.Factory(get(), get()) }
        factory<SignInComponent.Factory> { SignInComponentImpl.Factory(get(), get()) }
        factory<RegisterComponent.Factory> { info.javaway.wiseSpend.features.settings.child.auth.child.register.RegisterComponentImpl.Factory(get(), get()) }
        factory<SyncComponent.Factory> { SyncComponentImpl.Factory(get(), get(), get(), get()) }
    }
}

object DatePickerSingleQualifier : Qualifier {
    override val value: QualifierValue
        get() = this::class.getFullName()
}

object DatePickerFactoryQualifier : Qualifier {
    override val value: QualifierValue
        get() = this::class.getFullName()
}
