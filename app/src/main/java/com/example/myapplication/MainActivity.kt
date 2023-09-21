package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnVentanaPrincipal = findViewById<Button>(R.id.btnAction)
        val text1 = findViewById<TextView>(R.id.txtInfo)

        btnVentanaPrincipal.setOnClickListener {
            text1.text = "Lista de usuarios en la base de datos"
            callApi()
            Log.d("D", "Hello")
        }


    }
    private fun callApi(){
        // Obtén una referencia al ListView desde el layout XML
        val listView: ListView = findViewById(R.id.listView)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.239.209.214:8081/") // Coloca la URL base correcta aquí
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.obtenerLista()

        call.enqueue(object : Callback<List<Elemento>> {
            override fun onResponse(call: Call<List<Elemento>>, response: Response<List<Elemento>>) {
                if (response.isSuccessful) {
                    val dataList = response.body() ?: emptyList()

                    Log.d("d","no sé")

                    // Crea una lista de cadenas de texto que contiene nombre y apellido
                    val items = dataList.map { "rut: ${it.rut}, ${it.nombre} ${it.apellido}" }


                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, items)
                    listView.adapter = adapter
                } else {
                    Log.e("MainActivity", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Elemento>>, t: Throwable) {
                val dataList = arrayListOf("Error al recibir los datos")
                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, dataList)
                listView.adapter = adapter
                Log.d("Di",call.toString())
                Log.e("MainActivity", "Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun listaEl(){
        // Define una lista de elementos que deseas mostrar en el ListView
        val dataList = arrayListOf(
            "com.example.myapplication.Elemento 1",
            "com.example.myapplication.Elemento 2",
            "com.example.myapplication.Elemento 3",
            "com.example.myapplication.Elemento 4",
            "com.example.myapplication.Elemento 5"
        )

        // Crea un adaptador para enlazar la lista con el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)

        // Establece el adaptador en el ListView
//        listView.adapter = adapter

    }
}


