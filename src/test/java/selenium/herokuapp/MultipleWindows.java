package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class MultipleWindows {
    ChromeDriver driver;
    WebDriverWait wait;
    Actions actions;

    @BeforeTest
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void navigateToMultipleWindowsPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement multipleWindowsLink = driver.findElement(By.xpath("//a[@href='/windows']"));
        multipleWindowsLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertEquals(header.getText(), "Opening a new window");
    }

    @Test(priority = 2)
    public void openNewWindow(){
        driver.get("https://the-internet.herokuapp.com/windows");
        WebElement clickHereButton = driver.findElement(By.xpath("//a[@href='/windows/new']"));

        Assert.assertTrue(clickHereButton.isDisplayed());

        clickHereButton.click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle
        }

        WebElement newWindowContent = driver.findElement(By.xpath("//div[@class='example']//h3"));
        Assert.assertTrue(newWindowContent.isDisplayed());
        Assert.assertEquals(newWindowContent.getText(), "New Window");
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
