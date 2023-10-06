package com.jonas.listadetarefas.datasorce

import com.google.firebase.firestore.FirebaseFirestore

class DataSorce {
    private val db = FirebaseFirestore.getInstance()
    // primeira função para salvar tarefa
    fun saveTodo(title:String,description:String,priority:Int){
        val todoMap = hashMapOf(
            "title" to title,
            "description" to description,
            "priority" to priority

        )

        db.collection("todo").document(title).set(todoMap).addOnCompleteListener {  }
            .addOnFailureListener {  }
    }
}