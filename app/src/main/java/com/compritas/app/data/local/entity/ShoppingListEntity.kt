package com.compritas.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa una lista de compras en la base de datos.
 *
 * @property id Identificador único de la lista (autogenerado).
 * @property name Nombre de la lista de compras.
 * @property storeName Nombre opcional de la tienda asociada.
 * @property latitude Latitud de la tienda (opcional).
 * @property longitude Longitud de la tienda (opcional).
 * @property isArchived Indica si la lista ha sido archivada.
 * @property createdAt Fecha de creación en milisegundos.
 */
@Entity(tableName = "shopping_lists")
data class ShoppingListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val storeName: String?,
    val latitude: Double?,
    val longitude: Double?,
    val isArchived: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Entidad que representa un artículo dentro de una lista de compras.
 *
 * @property id Identificador único del artículo (autogenerado).
 * @property listId ID de la lista a la que pertenece este artículo.
 * @property name Nombre del artículo.
 * @property isChecked Indica si el artículo ha sido marcado como comprado.
 */
@Entity(tableName = "shopping_items")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val listId: Int,
    val name: String,
    val isChecked: Boolean = false
)
