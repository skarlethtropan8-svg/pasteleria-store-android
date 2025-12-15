package com.example.pasteleriastore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pasteleriastore.data.PasteleriaStoreDatabase
import com.example.pasteleriastore.model.Usuario
import kotlin.printStackTrace

class LoginViewModel (application: Application): AndroidViewModel(application) {
    private val database = PasteleriaStoreDatabase.getDatabase(application)
    private val usuarioDao = database.usuarioDao()

    suspend fun login(nombre: String, contrasena: String) : Boolean{
        val usuarioEncontrado = usuarioDao.login(nombre, contrasena)
        return usuarioEncontrado != null
    }
    suspend fun registroUsuario(usuario: Usuario): Boolean {
        return try {
            //verificaremos si el usuario existe
            val usuarioExistente = usuarioDao.obtenerUsuarioPorNombre(usuario.nombre)
            if (usuarioExistente != null) {
                // si el usuario ya existe
                return false
            }
            //insertar nuevo usuario
            usuarioDao.insertarUsuario(usuario)
            true
        }catch (e:Exception) {
            e.printStackTrace()
            false
        }
    }
}
