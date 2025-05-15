package info.javaway.spend_sense.di

import info.javaway.`spend-sense`.db.AppDb
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.list.CategoriesListComponent
import info.javaway.spend_sense.categories.list.CategoriesListComponentImpl
import info.javaway.spend_sense.categories.models.CategoryDao
import info.javaway.spend_sense.common.ui.calendar.DatePickerViewModel
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.creation.CreateEventViewModel
import info.javaway.spend_sense.events.list.EventsListComponent
import info.javaway.spend_sense.events.list.EventsListComponentImpl
import info.javaway.spend_sense.events.models.EventDao
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.platform.DeviceInfo
import info.javaway.spend_sense.root.RootComponent
import info.javaway.spend_sense.root.RootComponentImpl
import info.javaway.spend_sense.settings.SettingsComponent
import info.javaway.spend_sense.settings.SettingsComponentImpl
import info.javaway.spend_sense.settings.child.auth.AuthComponent
import info.javaway.spend_sense.settings.child.auth.AuthComponentImpl
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterComponent
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterComponentImpl
import info.javaway.spend_sense.settings.child.auth.child.signIn.SignInComponent
import info.javaway.spend_sense.settings.child.auth.child.signIn.SignInComponentImpl
import info.javaway.spend_sense.settings.child.sync.SyncComponent
import info.javaway.spend_sense.settings.child.sync.SyncComponentImpl
import info.javaway.spend_sense.storage.DbAdapters
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.module
import org.koin.ext.getFullName

object CoreModules {
    val deviceInfo = module {
        single { DeviceInfo() }
    }
    val coroutineScope = module {
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
                EventTableAdapter = DbAdapters.eventTableAdapter
            )
        }
    }

    val dao = module {
        single { CategoryDao(get(), get()) }
        single { EventDao(get(), get()) }
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
    }
}

object ViewModelModule {
    val viewModels = module {
        single(DatePickerSingleQualifier) { DatePickerViewModel() }
        factory(DatePickerFactoryQualifier) { DatePickerViewModel() }
        single { CreateEventViewModel() }
    }
}

object ComponentsFactoryModule {
    val componentFactory = module {
        single<RootComponent.Factory> { RootComponentImpl.Factory(get(), get(), get(), get())  }
        single<SettingsComponent.Factory> { SettingsComponentImpl.Factory(get(), get(), get(), get()) }
        factory<CategoriesListComponent.Factory> { CategoriesListComponentImpl.Factory(get()) }
        factory<EventsListComponent.Factory> { EventsListComponentImpl.Factory(get(), get()) }
        factory<AuthComponent.Factory> { AuthComponentImpl.Factory(get(), get()) }
        factory<SignInComponent.Factory> { SignInComponentImpl.Factory(get(), get()) }
        factory<RegisterComponent.Factory> { RegisterComponentImpl.Factory(get(), get()) }
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
