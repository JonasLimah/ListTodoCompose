package com.jonas.listadetarefas.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.jonas.listadetarefas.ui.theme.LIGHT_BLUE
import com.jonas.listadetarefas.ui.theme.WHITE

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun BoxText(
    value:String,
    onValueChange: (String) -> Unit,
    modifier : Modifier,
    label: String,
    maxLines: Int,
    placeholder : String,
){
    OutlinedTextField(
        modifier = modifier,
        placeholder = {
                      Text(text = placeholder)
        },
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        maxLines = maxLines,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = LIGHT_BLUE,
            focusedLabelColor = LIGHT_BLUE,
            containerColor = WHITE,
            cursorColor = LIGHT_BLUE

            ),
        shape = ShapeDefaults.Large,
        keyboardOptions = KeyboardOptions(autoCorrect = true, keyboardType = KeyboardType.Text)
    )
}

