package com.applitools.android.ufg.espresso;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.applitools.android.ufg.espresso.ui.login.LoginActivity;
import com.applitools.eyes.android.common.AndroidDeviceInfo;
import com.applitools.eyes.android.common.AndroidDeviceName;
import com.applitools.eyes.android.common.DeviceAndroidVersion;
import com.applitools.eyes.android.common.ScreenOrientation;
import com.applitools.eyes.android.common.logger.Logger;
import com.applitools.eyes.android.common.logger.StdoutLogHandler;
import com.applitools.eyes.android.components.androidx.AndroidXComponentsProvider;
import com.applitools.eyes.android.espresso.Eyes;
import com.applitools.eyes.android.espresso.fluent.Target;
import com.applitools.eyes.android.espresso.visualgrid.RunnerOptions;
import com.applitools.eyes.android.espresso.visualgrid.VisualGridRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Before
    public void beforeTest() {
        ActivityScenario.launch(LoginActivity.class);
    }

    @Test
    public void testLogin() {
        Logger logger = new Logger();
        logger.setLogHandler(new StdoutLogHandler(true));

        RunnerOptions options = new RunnerOptions();
        options.apiKey("API_KEY_HERE");

        VisualGridRunner runner = new VisualGridRunner(options);

        Eyes eyes = new Eyes(runner);
        eyes.setComponentsProvider(new AndroidXComponentsProvider());
        eyes.setConfiguration(eyes.getConfiguration()
                .addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Pixel_4))
                .addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Pixel_4_XL, ScreenOrientation.Landscape))
                .addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Pixel_3_XL, ScreenOrientation.Landscape, DeviceAndroidVersion.LATEST)));
        eyes.setLogHandler(logger.getLogHandler());

        try {
            eyes.open("AndroidX UFG test app", "Test Login page UFG");

            eyes.check(Target.window().withName("Check"));

            eyes.closeAsync();
        } finally {
            eyes.abortIfNotClosed();
            runner.getAllTestResultsImpl();
        }
    }
}
