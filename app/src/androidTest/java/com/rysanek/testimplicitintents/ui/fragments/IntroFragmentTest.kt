package com.rysanek.testimplicitintents.ui.fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.rysanek.testimplicitintents.R
import com.rysanek.testimplicitintents.adapters.IntroAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class IntroFragmentTest {
    
    private var scenario: FragmentScenario<IntroFragment>? = null
    private var mockNavController: TestNavHostController? = null
    private var resId: Int? = null
    
    @Before
    fun setup(){
        scenario = launchFragmentInContainer()
        mockNavController =  TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario!!.onFragment{
            mockNavController!!.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), mockNavController)
        }
    }
    
    @Test
    fun validate_GoToGalleryIntentFragment(){
        resId = R.id.galleryIntentFragment
        onView(withId(R.id.containerFragmentIntro)).check(matches(isDisplayed()))
        onView(withId(R.id.rvIntro)).check(matches(isDisplayed()))
        onView(withId(R.id.rvIntro)).perform(RecyclerViewActions.actionOnItemAtPosition<IntroAdapter.IntroVieHolder>(0, click()))
    }
    
    @After
    fun tearDown(){
        scenario = null
        mockNavController = null
    }
}
