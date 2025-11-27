package com.compritas.app.ui.shoppinglist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compritas.app.data.local.entity.ShoppingListEntity

/**
 * Pantalla principal que muestra las listas de compras.
 *
 * @param viewModel ViewModel que gestiona los datos de la pantalla.
 * @param onNavigateToMap Callback para navegar a la pantalla del mapa.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel = hiltViewModel(),
    onNavigateToMap: () -> Unit
) {
    val shoppingLists by viewModel.shoppingLists.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showReminderDialog by remember { mutableStateOf<ShoppingListEntity?>(null) }
    val context = androidx.compose.ui.platform.LocalContext.current
    val reminderManager = remember { com.compritas.app.util.ReminderManager(context) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Compritas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    Button(onClick = onNavigateToMap) {
                        Text("Mapa")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Lista")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shoppingLists) { list ->
                ShoppingListItem(
                    list = list,
                    onDelete = { viewModel.deleteList(list) },
                    onSetReminder = { showReminderDialog = list },
                    onClick = { /* TODO: Navigate to details */ }
                )
            }
        }

        if (showDialog) {
            AddListDialog(
                onDismiss = { showDialog = false },
                onConfirm = { name, store ->
                    viewModel.createList(name, store)
                    showDialog = false
                }
            )
        }

        showReminderDialog?.let { list ->
            ReminderDialog(
                onDismiss = { showReminderDialog = null },
                onConfirm = { timeInMillis ->
                    reminderManager.setReminder(
                        timeInMillis,
                        "Recordatorio: ${list.name}",
                        "No olvides comprar tus cosas en ${list.storeName ?: "la tienda"}"
                    )
                    showReminderDialog = null
                }
            )
        }
    }
}

@Composable
fun ShoppingListItem(
    list: ShoppingListEntity,
    onDelete: () -> Unit,
    onSetReminder: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = list.name, style = MaterialTheme.typography.titleMedium)
                if (!list.storeName.isNullOrBlank()) {
                    Text(text = "Tienda: ${list.storeName}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Row {
                IconButton(onClick = onSetReminder) {
                    Icon(androidx.compose.material.icons.filled.Notifications, contentDescription = "Programar Recordatorio")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}

@Composable
fun ReminderDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit
) {
    // Simplified dialog for demo purposes. In a real app, use a TimePicker.
    // Here we just set a reminder for 1 minute later.
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Programar Recordatorio") },
        text = { Text("¿Programar recordatorio para dentro de 1 minuto?") },
        confirmButton = {
            Button(onClick = {
                onConfirm(System.currentTimeMillis() + 60000)
            }) {
                Text("Sí")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun AddListDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String?) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var storeName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Lista") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre de la lista") }
                )
                TextField(
                    value = storeName,
                    onValueChange = { storeName = it },
                    label = { Text("Tienda (Opcional)") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, storeName.takeIf { it.isNotBlank() }) },
                enabled = name.isNotBlank()
            ) {
                Text("Crear")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
