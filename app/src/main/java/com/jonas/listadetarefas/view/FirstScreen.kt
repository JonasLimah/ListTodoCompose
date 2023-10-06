package com.jonas.listadetarefas.view


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.jonas.listadetarefas.R
import com.jonas.listadetarefas.itemList.TodoItem
import com.jonas.listadetarefas.model.Model
import com.jonas.listadetarefas.ui.theme.BLACK
import com.jonas.listadetarefas.ui.theme.Purple40
import com.jonas.listadetarefas.ui.theme.WHITE

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun FirstSceen(navController: NavController){



    //layout
    Scaffold(
        topBar = {
          TopAppBar(title = {
              Text(
                  text = "Lista de Tarefa",
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Bold,
                  color = WHITE
              )},
              //alterando o background da top bar
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Purple40)
          )
        },
        containerColor = BLACK,
        floatingActionButton = {
            FloatingActionButton(
                //usando o navegate para navegar entre telas atraves do id
                onClick = { navController.navigate("SaveTodo")  },
                containerColor = Purple40

            ) {
                Image(imageVector = ImageVector.vectorResource(
                    id = R.drawable.baseline_add_24)
                    , contentDescription ="icone adicionar" )
            }
        }
    ) {
        val listTodo : MutableList<Model> = mutableListOf(
            Model(
                 "jogar bola",
                "jogar bola pela tarde!",
                0
            ),
            Model(
                "estudar",
                "estudar  pela tarde!",
                1
            ),
            Model(
                "programar ",
                "jprogramar pela tarde!",
                2
        ),
            Model(
                "comer",
                "comer pela tarde!",
                3
            )
        )
        // criando listagem em coluna
        LazyColumn{
            //indexando lista que criamos acima pela posição e componente que criamos
            itemsIndexed(listTodo){position,_ ->
                //componente TODOITEM recebendo a posição e a lista
                TodoItem(position,listTodo)
            }
        }
    }
}