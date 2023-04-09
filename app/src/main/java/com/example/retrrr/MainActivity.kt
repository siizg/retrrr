package com.example.retrrr

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsProperties.EditableText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrrr.ui.theme.RetrrrTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    val apiService = Retrofit.Builder().baseUrl("")
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(ApiService :: class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrrrTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var username by remember {
                        mutableStateOf("")
                    }
                    var password by remember {
                        mutableStateOf("")
                    }
                    val context = LocalContext.current

                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                        TextField(value = username, onValueChange = {username = it})
                        Spacer(modifier = Modifier.size(16.dp))
                        TextField(value = password, onValueChange = {password = it})

                        Spacer(modifier = Modifier.size(16.dp))
                        Row (modifier = Modifier.fillMaxWidth()){
                            Button(onClick = { registerUser(username, password, apiService, context) }) {
                                Text(text = "register")
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Button(onClick = { logInUser(username, password, apiService, context) }) {
                                Text(text = "log in")
                            }
                        }
                    }
                }
            }
        }
    }
}
fun registerUser(username : String, password : String, apiService: ApiService, context: Context) {
    val user = User(username, password)
    GlobalScope.launch {
        try {
            val response = apiService.registerUser(user)
            if (response.isSuccessful) {
                Toast.makeText(context, "Register successfully", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT).show()
            }
        }
        catch (e : Exception) {
            Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
        }
    }

}

fun logInUser(username: String, password: String, apiService: ApiService, context: Context) {
    val user = User(username, password)
    GlobalScope.launch {
        try {
            val response = apiService.logInUser(user)
            if(response.isSuccessful) {
                Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
        catch(e : Exception) {
            Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrrrTheme {
        Greeting("Android")
    }
}