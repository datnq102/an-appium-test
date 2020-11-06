package appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Driver {

    public static io.appium.java_client.AppiumDriver<?> driver;
    public static String appiumServerUrl = "http://127.0.0.1:4723/wd/hub";
    public static Map<String, io.appium.java_client.AppiumDriver<?>> driverHashMap = new ConcurrentHashMap<>();

    public enum PLATFORM {
        ANDROID,
        IOS
    }

    public enum Device {
        IPHONEX("IPHONEX", PLATFORM.IOS, "14.0.1"),
        HUAWEI("HUAWEI", PLATFORM.ANDROID, "10");

        public String deviceName, platformVersion;
        public PLATFORM platform;
        Device(String name, PLATFORM platform, String version) {
            this.deviceName = name;
            this.platform = platform;
            this.platformVersion = version;
        }
    }

    public io.appium.java_client.AppiumDriver<?> initDriver(Device device) throws MalformedURLException {
        DesiredCapabilities capabilities;
        if (device.platform.equals(PLATFORM.ANDROID)) {
            capabilities = setupAndroidCapabilities(device);
            driver = new AndroidDriver<>(new URL(appiumServerUrl), capabilities);
        } else {
            capabilities = setupIOSCapabilities(device);
            driver = new IOSDriver<>(new URL(appiumServerUrl), capabilities);
        }
        return driver;
    }

    public io.appium.java_client.AppiumDriver<?> createNStoreDriver(Driver.Device device) throws MalformedURLException {
        io.appium.java_client.AppiumDriver<?> driver = new Driver().initDriver(device);
        driverHashMap.put(device.deviceName, driver);
        return driver;
    }

    private DesiredCapabilities setupAndroidCapabilities(Device device) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", device.platformVersion);
        capabilities.setCapability("automationName", "UIAutomator2");
        capabilities.setCapability("browserName", "Chrome");
        return capabilities;
    }

    private DesiredCapabilities setupIOSCapabilities(Device device) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("startIWDP", true);
        capabilities.setCapability("platformVersion", device.platformVersion);
        capabilities.setCapability("deviceName", device.deviceName); // could be any, but must be filled
        capabilities.setCapability("udid", "auto");
        return capabilities;
    }
}
