package dev.pol.safetyguide.di

import android.content.Context
import dev.pol.safetyguide.data.db.AppDatabase
import dev.pol.safetyguide.data.db.ChecklistDao
import dev.pol.safetyguide.data.repository.BackupRepository
import dev.pol.safetyguide.data.repository.ChecklistRepository
import dev.pol.safetyguide.data.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideChecklistDao(database: AppDatabase): ChecklistDao {
        return database.checklistDao()
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(@ApplicationContext context: Context): PreferencesRepository {
        return PreferencesRepository(context)
    }

    @Provides
    @Singleton
    fun provideBackupRepository(repository: ChecklistRepository): BackupRepository {
        return BackupRepository(repository)
    }
}
