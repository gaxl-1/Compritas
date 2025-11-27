package com.compritas.app.di

import com.compritas.app.data.repository.ShoppingListRepository
import com.compritas.app.data.repository.ShoppingListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de Hilt para vincular interfaces de repositorio con sus implementaciones.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Vincula [ShoppingListRepositoryImpl] a la interfaz [ShoppingListRepository].
     */
    @Binds
    @Singleton
    abstract fun bindShoppingListRepository(
        impl: ShoppingListRepositoryImpl
    ): ShoppingListRepository
}
