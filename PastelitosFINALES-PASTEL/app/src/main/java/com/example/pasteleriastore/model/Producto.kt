package com.example.pasteleriastore.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto (
    @PrimaryKey(autoGenerate = true)
    val productoId: Int = 0,

    val nombreProducto: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int = 0,
    val categoria: String,
    val activo: Boolean = true,
    val imagen: String?
)




