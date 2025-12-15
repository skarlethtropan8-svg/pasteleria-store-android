package com.example.pasteleriastore.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pasteleriastore.model.DetallePedido

@Dao
interface CarritoDao {
    @Insert
    suspend fun insertar(item: DetallePedido): Long

    @Update
    suspend fun actualizar(item: DetallePedido)

    @Delete
    suspend fun eliminar(item: DetallePedido)
    @Query("DELETE FROM detalledulce WHERE detalleCarritoId = :detalleCarritoId")
    suspend fun eliminarPorId(detalleCarritoId: Int)

    @Query("DELETE FROM detalledulce WHERE usuarioId = :usuarioId")
    suspend fun limpiarCarrito(usuarioId: Int)

    @Query("SELECT * FROM detalledulce WHERE usuarioId = :usuarioId")
    suspend fun obtenerCarritoPorUsuario(usuarioId: Int): List<DetallePedido>

    @Query("SELECT * FROM detalledulce WHERE usuarioId = :usuarioId AND productoId = :productoId")
    suspend fun obtenerItem(usuarioId: Int, productoId: Int): DetallePedido?

    @Query("UPDATE detalledulce SET cantidad = :nuevaCantidad WHERE detalleCarritoId = :detalleCarritoId")
    suspend fun actualizarCantidad(detalleCarritoId: Int, nuevaCantidad: Int)
}
