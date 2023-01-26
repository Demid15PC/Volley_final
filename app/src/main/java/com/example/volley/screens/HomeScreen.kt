package com.example.volley.screens

import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.volley.data.UserModel
import java.util.logging.Handler


@Composable
fun HomeScreen(info: List<UserModel>) {

       LazyColumn{
              items(info) { user ->
                     UserCard(user.first_name, user.last_name, user.email, user.avatar)
              }
       }
}

@Composable
fun UserCard(
       first_name: String,
       last_name: String,
       email: String,
       avatar: String,

) {
       val context = LocalContext.current
       Card(
              modifier = Modifier
                     .padding(horizontal = 5.dp)
                     .fillMaxWidth()
                     .padding(vertical = 10.dp)
                     .clickable {
                            notify(context , first_name, 1000)
                     }
       ) {
              Row() {
                     AsyncImage(
                            model = avatar,
                            contentDescription = "avatar",
                            modifier = Modifier.size(60.dp)
                     )
                     Spacer(modifier = Modifier.width(16.dp))
                     Column() {
                            Text(
                                   text = "$first_name $last_name",
                                   fontSize = MaterialTheme.typography.h5.fontSize,
                                   fontWeight = FontWeight.Bold
                            )
                            Text(text = email)
                     }
              }
       }
}

private fun notify(context: Context, text: String, time: Long){
       val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
       toast.show()
       android.os.Handler(Looper.getMainLooper()).postDelayed({
              toast.cancel()
       }, time)
}

