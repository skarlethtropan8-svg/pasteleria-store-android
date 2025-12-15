package com.example.pasteleriastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriastore.remote.Retrofitlnstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    //Lista de posts

    val posts = MutableStateFlow(emptyList<com.example.pasteleriastore.model.Post>())

    // Carga de post
    fun cargarPost(){
        viewModelScope.launch {
            val respuesta = Retrofitlnstance.api.getPosts()
            posts.value = respuesta
        }


    }
}