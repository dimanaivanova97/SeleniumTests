package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class ContextMenu {
    ChromeDriver driver;
    WebDriverWait wait;
    Actions actions;
    Alert alert;
    String alertText;

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
    public void navigateToContextMenu(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement contextMenuLink = driver.findElement(By.xpath("//a[@href='/context_menu']"));
        contextMenuLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        WebElement overview = driver.findElement(By.xpath("//div[@class='example']//p"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test(priority = 2)
    public void rightClick(){
        driver.get("https://the-internet.herokuapp.com/context_menu");

        WebElement contextBox = driver.findElement(By.id("hot-spot"));
        actions.contextClick(contextBox).perform();
        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();
        Assert.assertEquals(alertText, "You selected a context menu");
        alert.dismiss();
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
