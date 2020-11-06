package appium.youtube;

import appium.Driver;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.util.stream.Stream;

@Tag("Appium")
public class YoutubeTest extends Driver {

    String URL = "https://youtube.com";
    String searchTerm = "ingenico vietnam";

    @ParameterizedTest(name = "Should be able to search and play a video on Youtube")
    @MethodSource("deviceProvider")
    public void searchAndPlayAVideo(Driver.Device device) throws MalformedURLException {
        AppiumDriver<?> driver = createNStoreDriver(device);
        YoutubePage youtubePage = new YoutubePage(driver);

        youtubePage.open(URL);
        youtubePage.searchForSearchTerm(searchTerm);
        String fistVideoTitle = youtubePage.getFistVideoTitle();
        youtubePage.playFirstVideoFound();
        String currentlyRunningVideoTitle = youtubePage.getRunningVideoTitle();

        Assertions.assertEquals(fistVideoTitle, currentlyRunningVideoTitle);
    }

    static Stream<Device> deviceProvider() {
        return Stream.of(
                Device.IPHONEX,
                Device.HUAWEI
        );
    }


}
