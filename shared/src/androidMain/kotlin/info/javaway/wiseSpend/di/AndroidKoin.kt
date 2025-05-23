package info.javaway.wiseSpend.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import info.javaway.wiseSpend.db.AppDb
import org.koin.dsl.module

actual val platformModule = module {
    single { get<Context>().getSharedPreferences("AppSettings", Context.MODE_PRIVATE) }
    single<Settings> { SharedPreferencesSettings(get()) } //<T> либо: single { SharedPreferencesSettings(get()) } bind Settings::class
    single<SqlDriver> { AndroidSqliteDriver(AppDb.Schema, get(), "AppDb") }
}