package com.example.shoppinglist

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme


data class ShoopingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    var isEditing: Boolean = false
)


@Composable
fun ShoopingListApp(modifier: Modifier = Modifier){
    var sItems by remember { mutableStateOf(listOf<ShoopingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center) {

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) { Text(text = "Add Item") }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)){
            items(sItems){ item ->
                if(item.isEditing){
                    ShoppingItemEditor(
                        item = item,
                        onEditComplete = {
                                         editedName,
                                         editedQuantity -> sItems = sItems.map {
                                             item.copy(isEditing = false)
                                         }

                            val editedItem = sItems.find { it.id == item.id }
                            editedItem?.let {
                                it.name = editedName
                                it.quantity = editedQuantity
                            }
                        }
                    )
                }else{
                    ShoppingListItem(
                        item = item,
                        onEditClick = {sItems = sItems.map {item.copy(isEditing = it.id == item.id)}},
                        onDeleteClick = {sItems = sItems - item}
                    )
                }
            }
        }
    }

    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false},
            title = {Text(text = "Add Shopping Item")},
            text = {
                Column() {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it},
                        singleLine = true,
                        placeholder = { Text("Item Name")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words)
                    )
                    OutlinedTextField(
                        value = itemQuantity,
                        onValueChange = { itemQuantity = it},
                        singleLine = true,
                        placeholder = { Text("Item Quantity")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                    )
                }
            },
            confirmButton = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Button(
                        onClick = {
                            if(itemName.isNotEmpty()){
                                val newItem = ShoopingItem(
                                    id = sItems.size + 1,
                                    name = itemName,
                                    quantity = itemQuantity.toInt()
                                )
                                sItems = sItems + newItem
                                showDialog = false
                                itemName = ""
                                itemQuantity = ""
                            }
                        }
                    ) { Text(text = "Add") }

                    Button(onClick = { showDialog = false}) { Text(text = "Cancel")}
                }
            }
        )
    }
}

fun items(count: List<ShoopingItem>) {}

@Composable
fun ShoppingListItem(item: ShoopingItem,
                     onEditClick: () -> Unit,
                     onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(20)
            ),

        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Text(text = "Qty: ${item.quantity}", modifier = Modifier.padding(8.dp))

        Row(modifier = Modifier.padding(8.dp)) {

            IconButton(onClick = onEditClick){
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }

            IconButton(onClick = onDeleteClick){
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }

        }
    }
}


@Composable
fun ShoppingItemEditor(item: ShoopingItem, onEditComplete: (String, Int) -> Unit){
    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(
        modifier = Modifier.fillMaxWidth().background(Color.White).padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            BasicTextField(
                value = editedName,
                onValueChange = { editedName = it },
                singleLine = true,
                modifier = Modifier.wrapContentSize().padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words)
            )

            BasicTextField(
                value = editedQuantity,
                onValueChange = { editedQuantity = it },
                singleLine = true,
                modifier = Modifier.wrapContentSize().padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words)

            )
        }

        Button(onClick = {
            onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
            isEditing = false
        }) { Text(text = "Save") }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun ShoppingListItem() {
//    ShoppingListTheme {
//        ShoopingListApp()
//    }
//}