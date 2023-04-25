package com.calmperson.goalachievementtree.di

import android.content.Context
import androidx.room.Room
import com.calmperson.goalachievementtree.model.source.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModuleProviders::class]
)
class FakeAppModule {

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

}