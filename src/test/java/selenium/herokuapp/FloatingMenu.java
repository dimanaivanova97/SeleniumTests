package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class FloatingMenu {
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
    public void navigateToFloatingMenuPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement floatingMenuLink = driver.findElement(By.xpath("//a[@href='/floating_menu']"));
        floatingMenuLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        WebElement overview = driver.findElement(By.xpath("//div[@class='example']//p"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test(priority = 2)
    public void staticContent(){
        driver.get("https://the-internet.herokuapp.com/floating_menu");
        WebElement homeButton = driver.findElement(By.xpath("//a[@href='#home']"));
        WebElement newsButton = driver.findElement(By.xpath("//a[@href='#news']"));
        WebElement contactButton = driver.findElement(By.xpath("//a[@href='#contact']"));
        WebElement aboutButton = driver.findElement(By.xpath("//a[@href='#about']"));

        Assert.assertTrue(homeButton.isDisplayed());
        Assert.assertTrue(newsButton.isDisplayed());
        Assert.assertTrue(contactButton.isDisplayed());
        Assert.assertTrue(aboutButton.isDisplayed());

        // JavaScript
//        JavascriptExecutor jse = driver;
//        jse.executeScript("window.scrollBy(0,250)");

        Actions at = new Actions(driver);
        // scroll down
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();

        Assert.assertTrue(homeButton.isDisplayed());
        Assert.assertTrue(newsButton.isDisplayed());
        Assert.assertTrue(contactButton.isDisplayed());
        Assert.assertTrue(aboutButton.isDisplayed());

    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
