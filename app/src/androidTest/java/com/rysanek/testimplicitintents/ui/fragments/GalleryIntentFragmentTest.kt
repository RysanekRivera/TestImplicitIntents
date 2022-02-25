package com.rysanek.testimplicitintents.ui.fragments

import android.app.Activity
import android.app.Instrumentation
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.rysanek.testimplicitintents.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class GalleryIntentFragmentTest{
    
    private var scenario: FragmentScenario<GalleryIntentFragment>? = null
   
    @Before
    fun setup(){
        scenario = launchFragmentInContainer()
        Intents.init()
    }
    
    @Test
    fun validate_GoToGalleryIntent() {
        val expectedIntent: Matcher<Intent> = Matchers.allOf(
            hasAction(Intent.ACTION_PICK),
            hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        )
        
        val activityResult = mock_GalleryPickActivityResult()
        intending(expectedIntent).respondWith(activityResult)
        
        onView(ViewMatchers.withId(R.id.btGoToGallery)).perform(ViewActions.click())
        intended(expectedIntent)
    }
    
    private fun mock_GalleryPickActivityResult(): Instrumentation.ActivityResult {
        val resources = InstrumentationRegistry.getInstrumentation().context.resources
        val imagerUri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                    resources.getResourcePackageName(R.drawable.ic_launcher_background) + "/" +
                    resources.getResourceTypeName(R.drawable.ic_launcher_background) + "/" +
                    resources.getResourceEntryName(R.drawable.ic_launcher_background)
        )
        
        val resultIntent = Intent()
        resultIntent.data = imagerUri
        
        return Instrumentation.ActivityResult(Activity.RESULT_OK, resultIntent)
    }
    
    @After
    fun tearDown(){
        scenario = null
        Intents.release()
    }
 
}