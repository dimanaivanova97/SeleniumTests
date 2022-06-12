package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class Checkboxes {
    ChromeDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void navigateToChechboxesPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement checkboxesLink = driver.findElement(By.xpath("//a[@href='/checkboxes']"));
        checkboxesLink.click();

        WebElement heading = driver.findElement(By.xpath("//div[@class='example']//h3"));
        WebElement footerText = driver.findElement(By.id("page-footer"));
        Assert.assertTrue(heading.isDisplayed());
        Assert.assertEquals(footerText.getText(), "Powered by Elemental Selenium");
    }

    @Test(priority = 2)
    public void checkboxesValidation(){
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement firstCheckbox = driver.findElement(By.xpath("//form[@id='checkboxes']//input[@type='checkbox'][1]"));
        WebElement secondCheckbox = driver.findElement(By.xpath("//form[@id='checkboxes']//input[@type='checkbox'][2]"));
        Assert.assertTrue(firstCheckbox.isDisplayed());
        Assert.assertTrue(secondCheckbox.isDisplayed());
    }

    @Test(priority = 3)
    public void selectAndUnselectCheckboxes(){
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement firstCheckbox = driver.findElement(By.xpath("//form[@id='checkboxes']//input[@type='checkbox'][1]"));
        WebElement secondCheckbox = driver.findElement(By.xpath("//form[@id='checkboxes']//input[@type='checkbox'][2]"));

        for (int i = 0; i < 2; i++) {
            if (firstCheckbox.isSelected()){
                firstCheckbox.click();
                Assert.assertFalse(firstCheckbox.isSelected());
            } else {
                firstCheckbox.click();
                Assert.assertTrue(firstCheckbox.isSelected());
            }

            if (secondCheckbox.isSelected()){
                secondCheckbox.click();
                Assert.assertFalse(secondCheckbox.isSelected());
            } else {
                secondCheckbox.click();
                Assert.assertTrue(secondCheckbox.isSelected());
            }
        }
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
