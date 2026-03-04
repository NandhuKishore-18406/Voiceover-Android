package com.example.voiceoverandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voiceoverandroid.ui.theme.VoiceoverandroidTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoiceoverandroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)

@Composable
fun Greeting(modifier: Modifier = Modifier) {

    var isServiceRunning by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(modifier = modifier.fillMaxSize()) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = if (isServiceRunning)
                    "Service: Running"
                else
                    "Service: Stopped"
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {

                    val intent = Intent(context, MediaService::class.java)

                    if (!isServiceRunning) {
                        context.startService(intent)
                    } else {
                        context.stopService(intent)
                    }

                    isServiceRunning = !isServiceRunning
                }
            ) {
                Text(
                    text = if (isServiceRunning)
                        "Stop Service"
                    else
                        "Start Service"
                )
            }
        }
    }
}
