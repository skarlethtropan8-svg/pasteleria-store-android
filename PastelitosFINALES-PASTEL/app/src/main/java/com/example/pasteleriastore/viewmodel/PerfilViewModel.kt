package com.example.pasteleriastore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pasteleriastore.data.PasteleriaStoreDatabase
import com.example.pasteleriastore.model.Usuario

class PerfilViewModel(application: Application) : AndroidViewModel(application) {
    private val database = PasteleriaStoreDatabase.getDatabase(application)
    private val usuarioDao = database.usuarioDao()

    // Función para actualizar usuario por ID - SOLO campos modificados
    suspend fun actualizarUsuarioPorId(
        usuarioId: Int,
        nuevoNombre: String? = null,
        nuevaContrasena: String? = null,
        nuevoEmail: String? = null,
        nuevoTelefono: String? = null,
        nuevaDireccion: String? = null
    ): Boolean {
        return try {
            val usuario = usuarioDao.obtenerPorId(usuarioId)
            if (usuario != null) {
                // Solo actualizar los campos que no sean nulos
                val usuarioActualizado = usuario.copy(
                    nombre = nuevoNombre ?: usuario.nombre,
                    contrasena = nuevaContrasena ?: usuario.contrasena,
                    correo = nuevoEmail ?: usuario.correo,
                    telefono = nuevoTelefono ?: usuario.telefono,
                    direccion = nuevaDireccion ?: usuario.direccion
                )
                usuarioDao.actualizar(usuarioActualizado)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Función para cargar usuario por ID
    suspend fun cargarUsuarioPorId(id: Int): Usuario? {
        return try {
            usuarioDao.obtenerPorId(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Mantener función por nombre para compatibilidad
    suspend fun cargarUsuario(nombre: String): Usuario? {
        return try {
            usuarioDao.obtenerUsuarioPorNombre(nombre)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}



