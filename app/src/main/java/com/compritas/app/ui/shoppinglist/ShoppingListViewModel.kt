package com.compritas.app.ui.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compritas.app.data.local.entity.ShoppingListEntity
import com.compritas.app.data.repository.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de listas de compras.
 * Gestiona el estado de la UI y la comunicación con el repositorio.
 *
 * @property repository Repositorio de listas de compras.
 */
@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel() {

    /**
     * Flujo de estado que contiene la lista actual de compras.
     * Se actualiza automáticamente cuando hay cambios en la base de datos.
     */
    val shoppingLists: StateFlow<List<ShoppingListEntity>> = repository.getAllLists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Crea una nueva lista de compras.
     * @param name Nombre de la lista.
     * @param storeName Nombre opcional de la tienda.
     */
    fun createList(name: String, storeName: String?) {
        viewModelScope.launch {
            repository.createList(
                ShoppingListEntity(
                    name = name,
                    storeName = storeName,
                    latitude = null,
                    longitude = null
                )
            )
        }
    }

    /**
     * Elimina una lista de compras.
     * @param list La entidad de la lista a eliminar.
     */
    fun deleteList(list: ShoppingListEntity) {
        viewModelScope.launch {
            repository.deleteList(list)
        }
    }
}
