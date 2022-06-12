package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class DisappearingElements {
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
    public void navigateToDisappearingElements(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement contextMenuLink = driver.findElement(By.xpath("//a[@href='/disappearing_elements']"));
        contextMenuLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        WebElement overview = driver.findElement(By.xpath("//div[@class='example']//p"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test(priority = 2)
    public void disappearingElementsUponReload(){
        driver.get("https://the-internet.herokuapp.com/disappearing_elements");
        List<WebElement> buttons = driver.findElements(By.xpath("//div[@class='example']//ul/li"));

        int buttonsCount = buttons.size();

//        driver.get("https://the-internet.herokuapp.com/disappearing_elements");
//        buttons = driver.findElements(By.xpath("//div[@class='example']//ul/li"));
//
//        System.out.println(buttonsCount);
//        System.out.println(buttons.size());
//        Assert.assertFalse(buttonsCount == buttons.size());

        for (int i = 1; i < 5; i++) {
            try{
                driver.get("https://the-internet.herokuapp.com/disappearing_elements");
                buttons = driver.findElements(By.xpath("//div[@class='example']//ul/li[5]"));
            } catch (NoSuchElementException e){

            }
        }
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
