package com.jonas.listadetarefas.datasorce

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.jonas.listadetarefas.model.Model
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DataSorce {
    private val db = FirebaseFirestore.getInstance()
    // primeira função para salvar tarefa

    //estado de fluxo que recebe todas lista de tarefa
    private val _allTodo = MutableStateFlow<MutableList<Model>>(mutableListOf())

    private val allTodo : StateFlow<MutableList<Model>> = _allTodo
    fun saveTodo(title:String,description:String,priority:Int){
        val todoMap = hashMapOf(
            "title" to title,
            "description" to description,
            "priority" to priority

        )
        //collection vai ser o nome da coleção no firebase
        db.collection("todo").document(title).set(todoMap).addOnCompleteListener {  }
            .addOnFailureListener {  }
    }
    //metodo para pegar collection no db
    fun getTodo() : Flow<MutableList<Model>>{
        val listTodo : MutableList<Model> = mutableListOf()
        db.collection("todo").get().addOnCompleteListener { querySnapshot ->
            //recuperando cada documento e passando para variavel snapshot
            if (querySnapshot.isSuccessful){
                for (documento in querySnapshot.result){
                    //passando cada tarefa do tipo model para todo
                    val todo = documento.toObject(Model::class.java)
                    listTodo.add(todo)
                    _allTodo.value = listTodo
                }
            }
        }
        //retornando allTodo para termos acesso no repository
        return allTodo
    }
    fun deletTodo(title: String){
        db.collection("todo").document(title).delete().addOnCompleteListener {  }
            .addOnFailureListener {  }
    }

}