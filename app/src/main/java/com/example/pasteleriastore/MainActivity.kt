 package com.example.pasteleriastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pasteleriastore.navigation.AppNavigation
import com.example.pasteleriastore.ui.theme.PasteleriaStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasteleriaStoreTheme {
                AppNavigation()
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    PasteleriaStoreTheme {
        //AppNavigation()

    }
}