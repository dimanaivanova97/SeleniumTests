package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.NoSuchElementException;

import static org.testng.AssertJUnit.fail;

public class DynamicControls {
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

    @Test
    public void navigateToDynamicControlsPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement dynamicControlsLink = driver.findElement(By.xpath("//a[@href='/dynamic_controls']"));
        dynamicControlsLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h4"));
        WebElement overview = driver.findElement(By.xpath("//div[@class='example']//p"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertEquals(header.getText(), "Dynamic Controls");
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test
    public void removeAdd(){
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        WebElement checkbox = driver.findElement(By.id("checkbox"));
        Assert.assertTrue(checkbox.isDisplayed());
        WebElement addRemoveButton = driver.findElement(By.xpath("//form[@id='checkbox-example']//button"));
        addRemoveButton.click();

        WebElement messageAfterAction = driver.findElement(By.id("message"));
        wait.until(ExpectedConditions.visibilityOf(messageAfterAction));
        Assert.assertEquals(messageAfterAction.getText(), "It's gone!");
        try{
            driver.findElement(By.id("checkbox"));
        } catch (org.openqa.selenium.NoSuchElementException e){}

        addRemoveButton.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("message"))));
        Assert.assertTrue(driver.findElement(By.id("checkbox")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "It's back!");
    }

    @Test
    public void EnableDisable(){
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        WebElement field = driver.findElement(By.xpath("//form[@id='input-example']//input"));
        WebElement enableDisableButton = driver.findElement(By.xpath("//form[@id='input-example']//button"));

        Assert.assertFalse(field.isEnabled());

        enableDisableButton.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("message"))));
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "It's enabled!");
        Assert.assertTrue(field.isEnabled());

        enableDisableButton.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("message"))));
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "It's disabled!");
        Assert.assertFalse(field.isEnabled());
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
