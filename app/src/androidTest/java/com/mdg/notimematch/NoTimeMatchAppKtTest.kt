package com.mdg.notimematch

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mdg.notimematch.navigation.Routes
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoTimeMatchAppKtTest{

    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var context: Context

    @Before
    fun setupMoodTrackerAppNavHost() {
        composeTestRule.setContent {
            context = LocalContext.current
            navController = TestNavHostController(context)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NoTimeMatchApp(navController = navController)
        }
    }

    @Test
    fun clickClosetButtonInHome_shouldNavigateToClosetScreen(){
        composeTestRule.onNodeWithText(
            text = context.getString(R.string.closet_navigation_button_text)
        ).performClick()

        navController.assertCurrentRoute(Routes.CLOSET.value)
    }
}

private fun NavController.assertCurrentRoute(routeName: String){
    Assert.assertEquals(
        routeName,
        currentBackStackEntry?.destination?.route
    )
}