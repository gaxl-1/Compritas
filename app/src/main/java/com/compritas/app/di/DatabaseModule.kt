package com.compritas.app.di

import android.content.Context
import androidx.room.Room
import com.compritas.app.data.local.AppDatabase
import com.compritas.app.data.local.dao.ShoppingListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de Hilt que provee las dependencias relacionadas con la base de datos local (Room).
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provee una instancia única de la base de datos [AppDatabase].
     * @param context Contexto de la aplicación.
     * @return Instancia de [AppDatabase].
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "compritas_db"
        ).build()
    }

    /**
     * Provee una instancia del DAO [ShoppingListDao].
     * @param database Instancia de la base de datos.
     * @return Instancia de [ShoppingListDao].
     */
    @Provides
    @Singleton
    fun provideShoppingListDao(database: AppDatabase): ShoppingListDao {
        return database.shoppingListDao()
    }
}
