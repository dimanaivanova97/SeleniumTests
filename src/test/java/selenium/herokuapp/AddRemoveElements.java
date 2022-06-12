package selenium.herokuapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.NoSuchElementException;

public class AddRemoveElements {
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

    @Test (priority = 1)
    public void navigateToAddRemoveElementsPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement addRemoveElementsLink = driver.findElement(By.xpath("//a[@href='/add_remove_elements/']"));
        addRemoveElementsLink.click();

        WebElement addElementButton = driver.findElement(By.xpath("//button[@onclick='addElement()']"));
        WebElement addRemoveElementsHeader = driver.findElement(By.xpath("//div[@id='content']/h3"));
        WebElement footerText = driver.findElement(By.id("page-footer"));

        Assert.assertTrue(addElementButton.isDisplayed());
        Assert.assertEquals(addRemoveElementsHeader.getText(), "Add/Remove Elements");
        Assert.assertEquals(footerText.getText(), "Powered by Elemental Selenium");
    }

    @Test (priority = 2)
    public void elementalSeleniumLink(){
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement footerLink = driver.findElement(By.xpath("//a[@href='http://elementalselenium.com/']"));
        footerLink.click();

//        WebElement elementalSeleniumHeading = driver.findElement(By.xpath("//div[@class='row main heading']//h1"));
//        Assert.assertTrue(elementalSeleniumHeading.isDisplayed());
    }

    @Test (priority = 3)
    public void addElementButton(){
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addElementButton = driver.findElement(By.xpath("//button[@onclick='addElement()']"));

        for (int i = 1; i < 5; i++) {
            addElementButton.click();
            WebElement deleteButton = driver.findElement(By.xpath("//button[@class='added-manually'][" + i + "]"));
            Assert.assertTrue(deleteButton.isDisplayed());
        }
    }

    @Test (priority = 4)
    public void deleteButton(){
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addElementButton = driver.findElement(By.xpath("//button[@onclick='addElement()']"));

        for (int i = 1; i < 5; i++) {
            addElementButton.click();
        }

        for (int i = 1; i < 5; i++) {
            try{
                WebElement deleteButton = driver.findElement(By.xpath("//button[@class='added-manually']"));
                deleteButton.click();
            } catch (NoSuchElementException e){
            }
        }
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
