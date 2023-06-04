package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebClient {

    private static WebDriver driver;
    private static WebDriverWait wait;

    public String navigateToUrl(String url, String username, String password) {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().to(url);
        login(username, password);
        wait.until(ExpectedConditions.urlContains("code="));
        String callbackUrl = driver.getCurrentUrl();

        driver.close();
        driver.quit();
        return callbackUrl;
    }

    public void login(String username, String password) {

        driver.findElement(By.id("login-username")).sendKeys(username);
        driver.findElement(By.id("login-password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

    }
}