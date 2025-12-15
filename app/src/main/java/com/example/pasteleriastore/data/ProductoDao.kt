package com.example.pasteleriastore.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pasteleriastore.model.Producto

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    suspend fun  obtenerProducto(): List<Producto>

    @Query("SELECT * FROM productos WHERE productoId = :productoId")
    suspend fun obtenerPorId(productoId: Int): Producto?

    @Query("SELECT * FROM productos WHERE categoria = :categoria")
    suspend fun obtenerPorCategoria(categoria: String): List<Producto>

    @Query("SELECT * FROM productos WHERE activo = 1 ")
    suspend fun  obtenerProductosActivos(): List<Producto>

    //Insert para poder agregar productos

    @Insert
    suspend fun insertarProducto(productos: Producto)

    @Query("UPDATE productos SET stock = :nuevoStock WHERE productoId = :productoId ")
    suspend fun actualizarStock(productoId: Int, nuevoStock: Int)

    @Query ("DELETE FROM productos WHERE productoId = :productoId ")
    suspend fun  eliminarProducto(productoId: Int)
}