package selenium.skillo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.Random;

public class Register {


        public static void main(String[] args) {
                WebDriverManager.chromedriver().setup();
                Random random = new Random();

                ChromeDriver driver = new ChromeDriver();

                driver.get("http://training.skillo-bg.com/posts/all");
                driver.manage().window().maximize();

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                //Register
                WebElement loginButton = driver.findElement(By.id("nav-link-login"));
                loginButton.click();

                WebElement signUpLink = driver.findElement(By.xpath("//a[@href='/users/register']"));
                signUpLink.click();

                WebElement userName = driver.findElement(By.xpath("//input[@formcontrolname='username']"));
                userName.click();
                userName.sendKeys("d" + random.ints());

                WebElement email = driver.findElement(By.xpath("//input[@formcontrolname='email']"));
                email.click();
                email.sendKeys("d" + random.ints() + "@a.v");

                WebElement password = driver.findElement(By.id("defaultRegisterFormPassword"));
                password.click();
                password.sendKeys("d12345");

                WebElement confirmPassword = driver.findElement(By.id("defaultRegisterPhonePassword"));
                confirmPassword.click();
                confirmPassword.sendKeys("d12345");
        }
}
