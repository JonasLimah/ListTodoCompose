package com.jonas.listadetarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jonas.listadetarefas.ui.theme.ListaDeTarefasTheme
import com.jonas.listadetarefas.view.FirstSceen
import com.jonas.listadetarefas.view.SaveTodo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaDeTarefasTheme {
                val navController  = rememberNavController() // criando navController
                //configurando o navHost passando navController e a primeira tela
                NavHost(navController = navController , startDestination = "FirstScreen"){
                    composable(
                        route = "FirstScreen"//declarando primeira rota
                    ){
                        FirstSceen(navController)// passando o navcontroller por parametro
                    }
                    //declarando rota para segunda tela
                    composable(
                        route = "SaveTodo"
                    ){
                        SaveTodo(navController)
                    }

                }
            }
        }
    }
}

