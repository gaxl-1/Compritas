package com.compritas.app.data.repository

import com.compritas.app.data.local.dao.ShoppingListDao
import com.compritas.app.data.local.entity.ShoppingItemEntity
import com.compritas.app.data.local.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interfaz que define las operaciones disponibles en el repositorio de listas de compras.
 * Actúa como una capa de abstracción sobre las fuentes de datos (Room).
 */
interface ShoppingListRepository {
    fun getAllLists(): Flow<List<ShoppingListEntity>>
    suspend fun createList(list: ShoppingListEntity): Long
    suspend fun updateList(list: ShoppingListEntity)
    suspend fun deleteList(list: ShoppingListEntity)

    fun getItemsForList(listId: Int): Flow<List<ShoppingItemEntity>>
    suspend fun addItem(item: ShoppingItemEntity)
    suspend fun updateItem(item: ShoppingItemEntity)
    suspend fun deleteItem(item: ShoppingItemEntity)
}

/**
 * Implementación concreta del repositorio que utiliza el DAO de Room.
 * @property dao El DAO para acceder a la base de datos local.
 */
class ShoppingListRepositoryImpl @Inject constructor(
    private val dao: ShoppingListDao
) : ShoppingListRepository {
    override fun getAllLists(): Flow<List<ShoppingListEntity>> = dao.getAllLists()
    override suspend fun createList(list: ShoppingListEntity): Long = dao.insertList(list)
    override suspend fun updateList(list: ShoppingListEntity) = dao.updateList(list)
    override suspend fun deleteList(list: ShoppingListEntity) = dao.deleteList(list)

    override fun getItemsForList(listId: Int): Flow<List<ShoppingItemEntity>> = dao.getItemsForList(listId)
    override suspend fun addItem(item: ShoppingItemEntity) = dao.insertItem(item)
    override suspend fun updateItem(item: ShoppingItemEntity) = dao.updateItem(item)
    override suspend fun deleteItem(item: ShoppingItemEntity) = dao.deleteItem(item)
}
