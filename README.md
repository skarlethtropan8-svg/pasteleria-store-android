# Pastelería Store — App Android

Aplicación móvil para una pastelería: catálogo de productos, carrito de compras, registro e inicio de sesión de usuarios y perfil editable. Proyecto académico de DuocUC (2025).

## Funcionalidades

- **Splash e inicio de sesión / registro** de usuarios
- **Catálogo de productos** y registro de nuevos productos
- **Carrito de compras** con detalle de pedido
- **Perfil de usuario** editable
- **Sección de publicaciones** consumida desde una API REST externa

## Tecnologías

| Área | Herramientas |
|------|--------------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose, Material 3, Navigation Compose |
| Arquitectura | MVVM (ViewModels por pantalla) |
| Persistencia local | Room (DAOs de Usuario, Producto y Carrito, con datos iniciales) |
| Red | Retrofit + Gson, Kotlin Coroutines |
| Pruebas | JUnit, Espresso, Compose UI Test |

## Estructura

```
app/src/main/java/com/example/pasteleriastore/
├── data/        # Base de datos Room y DAOs
├── model/       # Entidades: Usuario, Producto, Carrito, DetallePedido, Post
├── remote/      # Cliente Retrofit y ApiService
├── viewmodel/   # Login, Perfil, Producto, Carrito, Post
├── navigation/  # Grafo de navegación
└── ui/          # Pantallas, componentes reutilizables y tema
```

## Cómo ejecutarlo

1. Abrir el proyecto en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar en un emulador o dispositivo con Android 7.0 (API 24) o superior.

## Autora

Skarlett Tropan — [GitHub](https://github.com/sktropan)
