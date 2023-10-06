package com.jonas.listadetarefas.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jonas.listadetarefas.components.BoxText
import com.jonas.listadetarefas.components.CustomButton
import com.jonas.listadetarefas.contants.Constants
import com.jonas.listadetarefas.repository.TodoRepository
import com.jonas.listadetarefas.ui.theme.GREEN_RADIO_SELECTED
import com.jonas.listadetarefas.ui.theme.GREEN_RADIO_UNSELECTED

import com.jonas.listadetarefas.ui.theme.Purple40
import com.jonas.listadetarefas.ui.theme.RED_RADIO_SELECTED
import com.jonas.listadetarefas.ui.theme.RED_RADIO_UNSELECTED
import com.jonas.listadetarefas.ui.theme.WHITE
import com.jonas.listadetarefas.ui.theme.YELLOW_RADIO_SELECTED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveTodo(navController: NavController){

    //coroutine para distribuir renderização em paralelo
    val scope = rememberCoroutineScope()
    // contexto para menssagens
    val context = LocalContext.current
    // iniciando repositorio
    val todoRepository = TodoRepository()

    val (title, setTitle)= remember {
        mutableStateOf("")
    }
    val (description, setDescription)= remember {
        mutableStateOf("")
    }
    val (lowPriority, setLowPriority)= remember {
        mutableStateOf(false)
    }
    val (mediumPriority, setMediumPriority)= remember {
        mutableStateOf(false)
    }
    val (highPriority, setHighPriority)= remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Salvar tarefa",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = WHITE
                )},
                //alterando o background da top bar
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Purple40)
            )
        },
        containerColor = WHITE,
    ) {
        Column(

            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp, 80.dp, 20.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
          BoxText(
              value = title,
              onValueChange = setTitle ,
              modifier = Modifier.fillMaxWidth(),
              label = "Tarefa" ,
              maxLines = 1,
              placeholder = "Digite sua tarefa"
          )
            BoxText(
                value = description,
                onValueChange = setDescription ,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                   
                ,
                label = "Descrição" ,
                maxLines = 5,
                placeholder = "Digite sua descrição"
            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp)

            ){
                Text(text = "Nível de prioridade")
                RadioButton(
                    selected =lowPriority ,
                    onClick = {
                              if (!lowPriority) setLowPriority(true) else setLowPriority(false)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = GREEN_RADIO_SELECTED,
                        unselectedColor = GREEN_RADIO_UNSELECTED
                    )


                )
                RadioButton(
                    selected =mediumPriority ,
                    onClick = {
                        if (!mediumPriority) setMediumPriority(true) else setMediumPriority(false)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = YELLOW_RADIO_SELECTED,
                        unselectedColor = YELLOW_RADIO_SELECTED
                    )

                )
                RadioButton(
                    selected =highPriority ,
                    onClick = {
                        if (!highPriority) setHighPriority(true) else setHighPriority(false)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RED_RADIO_SELECTED,
                        unselectedColor = RED_RADIO_UNSELECTED
                    )

                )
            }
            Button(onClick = {
                var message : Boolean = true
                //abrindo thread em paralelo
                scope.launch(Dispatchers.IO){
                    if (title.isEmpty()){
                        message = false
                    }else if(title.isNotEmpty() && description.isNotEmpty() && lowPriority){
                        todoRepository.saveTodo(title,description, Constants.LOW_PRIORITY)
                        message = true
                        setTitle("")
                        setDescription("")
                        setLowPriority(false)

                    }else if(title.isNotEmpty() && description.isNotEmpty() && mediumPriority){
                        todoRepository.saveTodo(title,description, Constants.MEDIUM_PRIORITY)
                        message = true
                        setTitle("")
                        setDescription("")
                        setMediumPriority(false)

                    }else if(title.isNotEmpty() && description.isNotEmpty() && highPriority){
                        todoRepository.saveTodo(title,description, Constants.HIGH_PRIORITY)
                        message = true
                        setTitle("")
                        setDescription("")
                        setHighPriority(false)

                    }else if(title.isNotEmpty() && description.isNotEmpty() ){
                        todoRepository.saveTodo(title,description, Constants.NO_PRIORITY)
                        message = true
                        setTitle("")
                        setDescription("")

                    }
                }
                scope.launch(Dispatchers.Main){
                    if(message){
                        Toast.makeText(context,"sucesso ao salvar tarefa",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,"titulo obrigarório",Toast.LENGTH_SHORT).show()
                    }
                }
                

            }) {
                Text(text = "salvar")
            }

    }

    }
}
