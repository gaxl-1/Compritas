package com.compritas.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.compritas.app.data.local.entity.ShoppingItemEntity
import com.compritas.app.data.local.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para interactuar con las tablas de listas de compras y artículos.
 * Proporciona métodos para realizar operaciones CRUD.
 */
@Dao
interface ShoppingListDao {
    // --- Operaciones para Listas ---

    /**
     * Obtiene todas las listas de compras activas (no archivadas), ordenadas por fecha de creación descendente.
     * @return Un Flow que emite la lista de entidades de listas de compras.
     */
    @Query("SELECT * FROM shopping_lists WHERE isArchived = 0 ORDER BY createdAt DESC")
    fun getAllLists(): Flow<List<ShoppingListEntity>>

    /**
     * Inserta una nueva lista de compras o reemplaza una existente si hay conflicto de ID.
     * @param list La entidad de la lista a insertar.
     * @return El ID de la fila insertada.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: ShoppingListEntity): Long

    /**
     * Actualiza una lista de compras existente.
     * @param list La entidad de la lista a actualizar.
     */
    @Update
    suspend fun updateList(list: ShoppingListEntity)

    /**
     * Elimina una lista de compras.
     * @param list La entidad de la lista a eliminar.
     */
    @Delete
    suspend fun deleteList(list: ShoppingListEntity)

    // --- Operaciones para Artículos ---

    /**
     * Obtiene todos los artículos asociados a una lista específica.
     * @param listId El ID de la lista de compras.
     * @return Un Flow que emite la lista de artículos.
     */
    @Query("SELECT * FROM shopping_items WHERE listId = :listId")
    fun getItemsForList(listId: Int): Flow<List<ShoppingItemEntity>>

    /**
     * Inserta un nuevo artículo en una lista.
     * @param item La entidad del artículo a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingItemEntity)

    /**
     * Actualiza un artículo existente (ej. marcar como comprado).
     * @param item La entidad del artículo a actualizar.
     */
    @Update
    suspend fun updateItem(item: ShoppingItemEntity)

    /**
     * Elimina un artículo de una lista.
     * @param item La entidad del artículo a eliminar.
     */
    @Delete
    suspend fun deleteItem(item: ShoppingItemEntity)
}
