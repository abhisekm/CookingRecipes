package com.example.cookingrecipes.di.module

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cookingrecipes.model.db.AppDatabase
import com.example.cookingrecipes.model.db.RecipesDao
import com.example.cookingrecipes.utils.Constants
import com.example.cookingrecipes.utils.Utils
import com.example.cookingrecipes.viewmodel.AddRecipesViewModelFactory
import com.example.cookingrecipes.viewmodel.RecipesDetailsViewModelFactory
import com.example.cookingrecipes.viewmodel.RecipesViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app: Application) {

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Change table when version changes
            }
        }
    }

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideRecipesDatabase(app: Application): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java, Constants.DATABASE_NAME
    )
        /*.addMigrations(MIGRATION_1_2)*/
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideRecipesDao(
        database: AppDatabase
    ): RecipesDao = database.recipesDao()

    @Provides
    @Singleton
    fun provideRecipesViewModelFactory(
        factory: RecipesViewModelFactory
    ): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideRecipesDetailsViewModelFactory(
        factory: RecipesDetailsViewModelFactory
    ): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideAddRecipesViewModelFactory(
        factory: AddRecipesViewModelFactory
    ): ViewModelProvider.Factory = factory


    @Provides
    @Singleton
    fun provideUtils(): Utils = Utils(app)
}