package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.security.MessageDigest
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    // Definición de la clase de datos Usuario
    data class Usuario(val nombreUsuario: String, val contrasenia: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Si estás utilizando una interfaz de usuario que cubre toda la pantalla.
        setContentView(R.layout.activity_main) // Cargar el layout de la actividad.

        // Configuración de márgenes y desplazamientos (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de las vistas (EditText y Button)
        val usuarioInput = findViewById<EditText>(R.id.usuario_input)
        val contraseniaInput = findViewById<EditText>(R.id.contrasenia_input)
        val botonLogin = findViewById<Button>(R.id.login_btn)

        // Lista de usuarios predefinidos (simulando una base de datos simple)
        val usuarios = listOf(
            Usuario("usuario1", "d48b165d1e5a63b56c7601e4269642e6a71fa90b2178a0212a1da5f7ee54255f"),
            Usuario("usuario2", "contrasena2"),
            Usuario("usuario3", "contrasena3")
        )
        fun hashString(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        // Configurar el evento click del botón para validar el login
        botonLogin.setOnClickListener {
            // Obtener los valores de usuario y contraseña desde los campos de texto
            val usuario = usuarioInput.text.toString()
            val contrase = contraseniaInput.text.toString()
            val contraseHash = hashString(contrase)
            Log.i("Login", "contaseña en hash= ${hashString(contrase)}")
            // Validar si el usuario y la contraseña coinciden con los datos predefinidos
            val usuarioValido = usuarios.find { it.nombreUsuario == usuario && it.contrasenia == contraseHash }

            // Mostrar resultados según la validación
            if (usuarioValido != null) {
                // Si el usuario es válido, mostramos un mensaje de éxito
                Log.i("Login", "Login exitoso: Usuario: $usuario y Contraseña: $contrase")
                setContentView(R.layout.activity_dos)

                // Configurar el RecyclerView para mostrar la lista de carros
               // val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                //recyclerView.layoutManager = LinearLayoutManager(this)

                val cars = listOf(
                    Car("Toyota", "Corolla"),
                    Car("Honda", "Civic"),
                    Car("Ford", "Mustang")
                )

               // recyclerView.adapter = CarAdapter(cars)
            } else {
                // Si el usuario no es válido, mostramos un mensaje de error
                Log.i("Login", "Login fallido: Usuario o contraseña incorrectos")
            }
        }

    }
}
