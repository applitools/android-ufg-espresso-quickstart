package com.applitools.android.ufg.espresso

import androidx.test.core.app.ActivityScenario
import com.applitools.android.ufg.espresso.ui.login.LoginActivity
import com.applitools.eyes.android.common.AndroidDeviceInfo
import com.applitools.eyes.android.common.AndroidDeviceName
import com.applitools.eyes.android.common.logger.Logger
import com.applitools.eyes.android.common.logger.StdoutLogHandler
import com.applitools.eyes.android.components.androidx.AndroidXComponentsProvider
import com.applitools.eyes.android.espresso.Eyes
import com.applitools.eyes.android.espresso.fluent.Target
import com.applitools.eyes.android.espresso.visualgrid.RunnerOptions
import com.applitools.eyes.android.espresso.visualgrid.VisualGridRunner
import org.junit.Before
import org.junit.Test

class KotlinTest {
    @Before
    fun beforeTest() {
        ActivityScenario.launch(LoginActivity::class.java)
    }

    @Test
    fun testLogin() {
        val logger = Logger()
        logger.logHandler = StdoutLogHandler(true)
        val options = RunnerOptions()
        options.apiKey("<API_KEY_HERE>")
        val runner = VisualGridRunner(options)
        val eyes = Eyes(runner)
        eyes.componentsProvider = AndroidXComponentsProvider()
        eyes.configuration = eyes.configuration
            .addMobileDevice(AndroidDeviceInfo(AndroidDeviceName.Pixel_4))
            .addMobileDevice(
                AndroidDeviceInfo(
                    AndroidDeviceName.Pixel_4_XL
                )
            )
            .addMobileDevice(
                AndroidDeviceInfo(
                    AndroidDeviceName.Pixel_3_XL
                )
            )
        eyes.logHandler = logger.logHandler
        try {
            eyes.open("AndroidX UFG test app", "Test Login page UFG Kotlin")
            eyes.check(Target.window().withName("Check"))
            eyes.closeAsync()
        } finally {
            runner.allTestResults
            eyes.abortIfNotClosed()
        }
    }
}
