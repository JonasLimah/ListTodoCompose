package com.jonas.listadetarefas.repository

import com.jonas.listadetarefas.datasorce.DataSorce
import com.jonas.listadetarefas.model.Model
import kotlinx.coroutines.flow.Flow

class TodoRepository() {
    private val dataSorce = DataSorce()
    fun saveTodo(
        title:String,
        description:String,
        priority:Int

    ){
       dataSorce.saveTodo(title,description,priority)
    }
    fun getTodo():Flow<MutableList<Model>>{
        //pegando valores la do DataSorce
        return dataSorce.getTodo()
    }
    fun deleteTodo(title:String){
        dataSorce.deletTodo(title)
    }
}