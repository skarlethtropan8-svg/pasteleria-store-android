package com.example.pasteleriastore.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get: Rule
    val composeGetRule = createComposeRule()

    @Test
    fun HomeScreen_MuestraTitulo(){
        composeGetRule.setContent {
            HomeScreen(navController = rememberNavController())
        }
        composeGetRule.onNodeWithText("Qué gusto verte").assertIsDisplayed()
    }

}