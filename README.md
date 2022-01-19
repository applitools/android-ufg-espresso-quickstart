# android-ufg-espresso-quickstart
A Basic Android Espresso UFG project


## Installation

Add following urls to repositories in the main `build.gradle` or `settings.gradle` file
```
// This is needed to get Espresso Eyes SDK 
maven {
    url uri("https://applitools.jfrog.io/artifactory/Android")
}
// This is needed to get UFG libs
maven {
    url "https://applitools.jfrog.io/artifactory/ufg-android"
    credentials {
        username = System.getenv("ANDROID_UFG_BETA_USERNAME").toString()
        password = System.getenv("ANDROID_UFG_BETA_PASSWORD").toString()
    }
}
```

Then add the following dependencies to your application `build.gradle` file
```
    // UFG libs - start
    implementation("com.applitools:vhs-androidx:1.0.31"){
        exclude group: 'com.applitools', module: 'eyes-android-common'
        exclude group: 'com.applitools', module: 'eyes-android-components'
        exclude group: 'com.applitools', module: 'eyes-android-components-androidx'
    }
    implementation ("com.applitools:eyes-common-java3:3.208.2") {
        exclude group: 'com.applitools', module: 'eyes-connectivity-java3-jersey2x'
        exclude module: 'commons-logging'
    }
    implementation ("com.applitools:eyes-connectivity-java3-net:3.208.2" ){
        exclude module: 'commons-logging'
    }
    // UFG libs - end

    // Eyes Espresso - start
    androidTestImplementation 'com.applitools:eyes-android-espresso:4.10.5-beta@aar'
    androidTestImplementation 'com.applitools:eyes-android-common:4.10.5-beta'
    androidTestImplementation 'com.applitools:eyes-android-core:4.10.5-beta'
    androidTestImplementation 'com.applitools:eyes-android-components:4.10.5-beta@aar'
    androidTestImplementation 'com.applitools:eyes-android-components-androidx:4.10.5-beta@aar'
    // Eyes Espresso - end
```

### Write and run first test

Before running the test, make sure to set the API key that identifies your account in the environment variable
**APPLITOOLS_API_KEY** or directly assign it to the ***eyes.setApiKey()***.

You can find your API key under the user menu located at the right hand side of the test manager toolbar.
If you don't yet have an account [create it now](https://applitools.com/users/register) to obtain your key.

### Java Example

```
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
            runner.getAllTestResults();
        }
    }
}
```

### Kotlin Example
See KotlinTest.kt


## Possible issues

We can have duplicated META files from or libraries. To exclude them next lines should be added into the application `build.gradle` file, `android` section. 
```
// This is needed to exclude duplicated META-INF
packagingOptions {
    exclude 'META-INF/**'
    exclude 'changelog.xml'
}
```
