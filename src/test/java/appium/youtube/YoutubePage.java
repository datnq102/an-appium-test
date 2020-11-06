package appium.youtube;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class YoutubePage {

    AppiumDriver driver;

    @FindBy(xpath = "//div/button[@aria-label='Search YouTube']")
    WebElement searchIconBefore;

    @FindBy(xpath = "//input[@name='search']")
    WebElement searchField;

    @FindBy(xpath = "//button[@aria-label='Search YouTube']")
    WebElement searchIconAfter;

    @FindBy(xpath = "(//a[@class='compact-media-item-image'])[1]")
    WebElement firstVideoThumbnail;

    @FindBy(xpath = "(//h4[@class=\"compact-media-item-headline\"])[1]")
    WebElement firstVideoTitle;

    @FindBy(xpath = "//h2[@class=\"slim-video-metadata-title\"]")
    WebElement runningVideoTitle;

    public YoutubePage(AppiumDriver<?> driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
        this.driver = driver;
    }

    public void searchForSearchTerm(String searchTerm) {

        waitElementVisible(searchIconBefore).click();
        waitElementVisible(searchField).sendKeys(searchTerm);
        waitElementVisible(searchIconAfter).click();
    }

    public String getFistVideoTitle() {
        return waitElementVisible(firstVideoTitle).getText();
    }

    public void playFirstVideoFound() {
        waitElementVisible(firstVideoThumbnail).click();
    }

    public String getRunningVideoTitle() {
        return waitElementVisible(runningVideoTitle).getText();
    }

    public void open(String url) {
        driver.get(url);
    }

    public WebElement waitElementVisible(WebElement element) {
        return new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(element));
    }
}
