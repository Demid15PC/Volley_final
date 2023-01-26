package com.example.volley

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.volley.data.UserModel
import com.example.volley.screens.HomeScreen
import com.example.volley.ui.theme.VolleyTheme
import org.json.JSONArray
import org.json.JSONObject

private const val API_URL = "https://reqres.in/api/users?per_page=12"

class MainActivity : ComponentActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
              super.onCreate(savedInstanceState)
              setContent {
                     VolleyTheme {
                            val info = remember {
                                   mutableStateOf(listOf<UserModel>())
                            }

                            getData(this, info)
                            HomeScreen(info.value)
                     }
              }
       }
}

fun getData(context: Context, info: MutableState<List<UserModel>>) {

       val queue = Volley.newRequestQueue(context)
       val sRequest = StringRequest(
              Request.Method.GET,
              API_URL,
              { response ->
                     Log.d("Testdata", response.toString())
                     info.value = parsingData(response)
              },
              {
                     Log.d("Testdata", it.toString())
              }
       )
       queue.add(sRequest)
}

fun parsingData(response: String): List<UserModel> {
       if (response.isEmpty()) return listOf()
       val mainObgect = JSONObject(response)
       val list = ArrayList<UserModel>()
       val data = mainObgect.getJSONArray("data")

       for (i in 0 until data.length()) {
              val item = data[i] as JSONObject
              list.add(
                     UserModel(
                            first_name = item.getString("first_name"),
                            last_name = item.getString("last_name"),
                            email = item.getString("email"),
                            avatar = item.getString("avatar")
                     )
              )
       }
       return list
}