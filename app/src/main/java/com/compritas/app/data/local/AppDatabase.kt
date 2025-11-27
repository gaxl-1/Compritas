package com.compritas.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.compritas.app.data.local.dao.ShoppingListDao
import com.compritas.app.data.local.entity.ShoppingItemEntity
import com.compritas.app.data.local.entity.ShoppingListEntity

/**
 * Base de datos principal de la aplicación usando Room.
 * Define las entidades y la versión de la base de datos.
 */
@Database(entities = [ShoppingListEntity::class, ShoppingItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Proporciona acceso al DAO de listas de compras.
     */
    abstract fun shoppingListDao(): ShoppingListDao
}
