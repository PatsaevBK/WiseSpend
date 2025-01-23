package info.javaway.spend_sense.di

import info.javaway.`spend-sense`.db.AppDb
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.list.CategoriesListViewModel
import info.javaway.spend_sense.categories.models.CategoryDao
import info.javaway.spend_sense.common.ui.calendar.DatePickerViewModel
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.creation.CreateEventViewModel
import info.javaway.spend_sense.events.list.EventsScreenViewModel
import info.javaway.spend_sense.events.models.EventDao
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.platform.DeviceInfo
import info.javaway.spend_sense.root.RootViewModel
import info.javaway.spend_sense.settings.SettingsViewModel
import info.javaway.spend_sense.settings.auth.register.RegisterViewModel
import info.javaway.spend_sense.settings.auth.signIn.SignInViewModel
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
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
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
        single { RootViewModel(get()) }
        factory { SettingsViewModel(get(), get(), get(), get(), get()) }
        single(DatePickerSingleQualifier) { DatePickerViewModel() }
        factory(DatePickerFactoryQualifier) { DatePickerViewModel() }
        single { CategoriesListViewModel(get()) }
        single { EventsScreenViewModel(get(), get()) }
        single { CreateEventViewModel() }
        factory { RegisterViewModel(get(), get()) }
        factory { SignInViewModel(get(), get()) }
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
