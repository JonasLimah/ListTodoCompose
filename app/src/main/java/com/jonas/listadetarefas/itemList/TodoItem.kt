package com.jonas.listadetarefas.itemList
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jonas.listadetarefas.R
import com.jonas.listadetarefas.model.Model
import com.jonas.listadetarefas.repository.TodoRepository
import com.jonas.listadetarefas.ui.theme.GREEN_RADIO_SELECTED
import com.jonas.listadetarefas.ui.theme.RED_RADIO_SELECTED
import com.jonas.listadetarefas.ui.theme.WHITE
import com.jonas.listadetarefas.ui.theme.YELLOW_RADIO_SELECTED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TodoItem(
    //recebendo parametros passado no lazyColum
    position: Int,
    listTodo : MutableList<Model>,
    context : Context, //contexto inserido para poder deletar tarefa
    navController: NavController //parametro para renderizar a lista pós exclusão
){
    // recuperando valores da lista pela posição
    val titleTodo = listTodo[position].title
    val descriptionTodo = listTodo[position].description
    val priorityTodo = listTodo[position].priority

    //criando scopo para deletar tarefa assyncrono
    val scope = rememberCoroutineScope()
    // iniciando repositoria para excluir tarefa
    val repositoryTodo = TodoRepository()
    //metodo para criar caixa de alerta sim ou nao
    fun dialogDelete(){
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Deletar tarefa").setMessage(
            "deseja excluir a tarefa?"
        ).setPositiveButton(
            "Sim"
        ){_,_ ->
            repositoryTodo.deleteTodo(titleTodo.toString())
            scope.launch(Dispatchers.Main){
                listTodo.removeAt(position)
                navController.navigate("FirstScreen")
                Toast.makeText(context,"Tarefa deletada ${titleTodo.toString()}",Toast.LENGTH_SHORT).show()
            }

        }.setNegativeButton("Não"){_,_ ->}.show()
    }

    //validações
    val levelPriority = when(priorityTodo){
        0 -> {
            "Sem prioridade"
        }
        1 ->{
            "Prioridade Baixa"
        }
        2 ->{
            "Prioridade Média"
        }
        else -> {
            "Priodidade Alta"
        }
    }
    // validando cores apartir da prioridade
    val color = when(priorityTodo){
        0 -> {
            Color.Black
        }
        1 ->{
           GREEN_RADIO_SELECTED
        }
        2 ->{
            YELLOW_RADIO_SELECTED
        }
        else -> {
           RED_RADIO_SELECTED
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = WHITE

        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ){
      ConstraintLayout(
          modifier = Modifier
              .padding(20.dp)
              .fillMaxWidth(),

      ) {
          //criando a referencia para o constraint layout tipo um id
            val (title,description,lvlPriority,txtPriority,btDel) = createRefs()

          Text(
              text = titleTodo.toString(),
              modifier = Modifier.constrainAs(title){
                  top.linkTo(parent.top, margin = 10.dp)
                  start.linkTo(parent.start, margin = 10.dp)
              }
          )
          Text(
              text = descriptionTodo.toString(),
              modifier = Modifier.constrainAs(description){
                  top.linkTo(title.bottom, margin = 10.dp)
                  start.linkTo(parent.start, margin = 10.dp)
              }

          )
          Text(
              text = levelPriority,
              modifier = Modifier.constrainAs(txtPriority){
                  top.linkTo(description.bottom, margin = 10.dp)
                  start.linkTo(parent.start, margin = 10.dp)
                  bottom.linkTo(parent.bottom, margin = 10.dp)
              }
          )
          Card(
            colors = CardDefaults.cardColors(
                containerColor = color

            ),
             modifier = Modifier
                 .size(20.dp)
                 .constrainAs(lvlPriority) {
                     top.linkTo(description.bottom, margin = 24.dp)
                     start.linkTo(txtPriority.end, margin = 20.dp)
                 }, shape = ShapeDefaults.ExtraLarge
          ){

          }
          IconButton(onClick = {
              dialogDelete()
          },
              modifier =
                  Modifier.constrainAs(btDel){
                      top.linkTo(description.bottom, margin = 10.dp)
                      bottom.linkTo(parent.bottom, margin = 10.dp)
                      start.linkTo(lvlPriority.end, margin = 40.dp)
                      end.linkTo(parent.end, margin = 10.dp)
                      

                  }
              ) {

              Image(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_24)
                  , contentDescription = "icone deletar" )
          }
          }

    }
}

