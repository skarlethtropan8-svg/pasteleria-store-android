package com.example.pasteleriastore.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val contrasena: String,
    val correo: String = "", //se le dara valor por defecto
    val direccion: String?= null,//esto es optional
    val telefono: String? = null // esto igual puede ser opcional
)


