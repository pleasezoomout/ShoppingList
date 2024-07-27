package com.example.shoppinglist

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
        ) {
            Text(text = "Add Item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sItems)
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
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
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
                            }
                        }
                    ) {
                        Text(text = "Add")
                    }

                    Button(onClick = { showDialog = false}) {
                        Text(text = "Cancel")
                    }
                }
            }
        )
    }
}

fun items(count: List<ShoopingItem>) {}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTheme {
        ShoopingListApp()
    }
}