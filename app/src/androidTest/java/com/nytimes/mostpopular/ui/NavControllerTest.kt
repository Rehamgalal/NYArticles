package com.nytimes.mostpopular.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nytimes.mostpopular.R
import com.nytimes.mostpopular.ui.fragments.DetailsFragment
import com.nytimes.mostpopular.ui.fragments.ListFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class NavControllerTest {

    @Test
    fun testNavigationToInGameScreen() {
        // Create a mock NavController
        val mockNavController = mock(NavController::class.java)

        // Create a graphical FragmentScenario for the TitleScreen
        val titleScenario = launchFragmentInContainer<DetailsFragment>()

        // Set the NavController property on the fragment
        titleScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        // Verify that performing a click prompts the correct Navigation action
        onView(ViewMatchers.withId(R.id.toolbar)).perform(ViewActions.click())
        verify(mockNavController.navigateUp())
    }
}