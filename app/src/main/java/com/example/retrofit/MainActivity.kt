package com.example.retrofit

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val TAG: String = "CHECK_RESPONSE"

    private lateinit var commentsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        commentsTextView = findViewById(R.id.commentsTextView)

        getAllComments()
    }

    private fun getAllComments() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "Response body: ${it}")
                        val commentsString = it.joinToString(separator = "\n\n") { comment ->
                            "ID: ${comment.id} \n" + "Body: ${comment.body} \n"
                        }
                        commentsTextView.text = commentsString
                    } ?: run {
                        Log.d(TAG, "Response body is null")
                    }
                } else {
                    Log.e(TAG, "Response Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
