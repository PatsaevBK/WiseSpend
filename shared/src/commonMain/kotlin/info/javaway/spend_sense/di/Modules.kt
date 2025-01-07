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
import info.javaway.spend_sense.platform.DeviceInfo
import info.javaway.spend_sense.root.RootViewModel
import info.javaway.spend_sense.settings.SettingsViewModel
import info.javaway.spend_sense.storage.DbAdapters
import info.javaway.spend_sense.storage.SettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
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

object RepositoriesModule {
    val repository = module {
        single { CategoriesRepository(get()) }
        single { EventsRepository(get()) }
    }
}

object ViewModelModule {
    val viewModels = module {
        single { RootViewModel(get()) }
        factory { SettingsViewModel(get(), get()) }
        single(DatePickerSingleQualifier) { DatePickerViewModel() }
        factory(DatePickerFactoryQualifier) { DatePickerViewModel() }
        single { CategoriesListViewModel(get()) }
        single { EventsScreenViewModel(get(), get()) }
        single { CreateEventViewModel() }
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
