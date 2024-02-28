package com.example.measurementconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ConversionType {
    MILES, YARD, FOOT
}

@Composable
fun MainScreen() {


    val context = LocalContext.current

    var numberText by rememberSaveable {
        mutableStateOf("0")
    }

    var textResult by rememberSaveable {
        mutableStateOf("-")
    }

    var inputErrorState by rememberSaveable {
        mutableStateOf(false)
    }

    fun convert(input:String,conversion_type:ConversionType):String {
        val input_val: Double
        inputErrorState = false
        try {
            input_val = input.toDouble()
        } catch(e:NumberFormatException) {
            inputErrorState = true
            return context.getString(R.string.input_must_be_a_number)
        }
        return when (conversion_type) {
            ConversionType.MILES -> (input_val * 0.6214).toString()
            ConversionType.YARD -> (input_val * 1094).toString()
            ConversionType.FOOT -> (input_val * 3280.8399).toString()
        }
    }

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            label = { Text(text= stringResource(R.string.text_field_value)) },
            modifier = Modifier.fillMaxWidth(),
            value = numberText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number ) ,
            onValueChange = {
                numberText = it
                //validate(numberText)
            },
            isError = inputErrorState, trailingIcon = {
                if(inputErrorState) {
                    Icon(Icons.Filled.Warning,"error",tint = MaterialTheme.colorScheme.error)
                }
            })

        Row(
          horizontalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            Button(onClick = {
                textResult = convert(numberText,ConversionType.MILES)
            }) {
                Text(text = stringResource(R.string.km_miles))
            }

            Button(onClick = {
                textResult = convert(numberText,ConversionType.YARD)
            }) {
                Text(text = stringResource(R.string.km_yard))
            }

            Button(onClick = {
                textResult = convert(numberText,ConversionType.FOOT)
            }) {
                Text(text = stringResource(R.string.km_foot))
            }
        }

        Text(text=textResult,
            fontSize=28.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = if(inputErrorState) Color.Red else Color.Blue
        )
    }


}