package com.example.aula8exercicio1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var btnChamarApi: Button
    private lateinit var txtResultado: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        btnChamarApi = findViewById(R.id.btnChamarApi)
        txtResultado = findViewById(R.id.txtResultado)
        progressBar = findViewById(R.id.progressBar)

        btnChamarApi.setOnClickListener {
            // Desativa o botão e mostre o ProgressBar durante a chamada da API
            btnChamarApi.isEnabled = false
            progressBar.visibility = View.VISIBLE

            service.getUser().enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    // Restaura o estado do botão e esconda o ProgressBar
                    btnChamarApi.isEnabled = true
                    progressBar.visibility = View.INVISIBLE

                    if (response.isSuccessful) {
                        val dadosRecebidos = response.body()
                        val nome = dadosRecebidos?.name

                        // Atualiza o TextView com o resultado
                        txtResultado.text = "Nome: $nome"
                    } else {
                        txtResultado.text = "Erro na chamada da API"
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    // Restaura o estado do botão e esconde o ProgressBar
                    btnChamarApi.isEnabled = true
                    progressBar.visibility = View.INVISIBLE

                    txtResultado.text = "Erro na chamada da API"
                }
            })
        }
    }
}
