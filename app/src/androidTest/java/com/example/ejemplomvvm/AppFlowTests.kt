package com.example.ejemplomvvm

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppFlowTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigateToPromotionsScreen() {
        composeTestRule.onNodeWithText("Promociones").performClick()
        // Wait for the API call to complete and check for an item
        composeTestRule.waitUntil(60000) {
            composeTestRule
                .onAllNodesWithText("Ir a la oferta")[0]
                .isDisplayed()
        }
        composeTest-rule.onNodeWithText("Ir a la oferta").assertIsDisplayed()
    }

    @Test
    fun testNavigateToCatalogScreen() {
        composeTestRule.onNodeWithText("Explorar catálogo").performClick()
        composeTestRule.onNodeWithText("Buscar producto...").assertIsDisplayed()
    }

    @Test
    fun testNavigateToLoginScreen() {
        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("¿No tienes una cuenta?").assertIsDisplayed()
    }

    @Test
    fun testBackButtonInPromotions() {
        composeTestRule.onNodeWithText("Promociones").performClick()
        composeTestRule.onNodeWithText("Volver").performClick()
        composeTestRule.onNodeWithText("LevelUp").assertIsDisplayed()
    }

    @Test
    fun testHomeScreenTitleIsDisplayed() {
        composeTestRule.onNodeWithText("LevelUp").assertIsDisplayed()
    }
}