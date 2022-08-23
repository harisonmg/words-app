package com.example.wordsapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTests {

    @Test
    fun navigate_to_words_nav_component() {
        // create a test instance of nav controller
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // launch LetterListFragment
        // we must pass the theme
        val letterListScenario = launchFragmentInContainer<LetterListFragment>(
            themeResId = R.style.Theme_Words
        )

        // declare the navigation graph to use
        letterListScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // trigger the event that prompts the navigation
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )

        // test that the nav controller's destination has the id of the fragment we expect to be in
        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)
    }
}