package com.jonas.listadetarefas.repository

import com.jonas.listadetarefas.datasorce.DataSorce

class TodoRepository() {
    private val dataSorce = DataSorce()
    fun saveTodo(
        title:String,
        description:String,
        priority:Int

    ){
       dataSorce.saveTodo(title,description,priority)
    }
}