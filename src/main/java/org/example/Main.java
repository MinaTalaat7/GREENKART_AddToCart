package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Main {

    protected WebDriver driver;

    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
    }

    public void addProductToCart(String productName) {
        List<WebElement> products = driver.findElements(By.cssSelector("h4.product-name"));

        for (int i = 0; i < products.size(); i++) {
            String name = products.get(i).getText();

            if (name.contains(productName)) {
                driver.findElements(By.xpath("//div[@class='product-action']/button")).get(i).click();
                break;
            }
        }
    }

    public void proceedToCheckoutAndApplyPromo(String promoCode) {
        // Click cart icon
        driver.findElement(By.cssSelector("img[alt='Cart']")).click();

        // Click "Proceed to Checkout"
        driver.findElement(By.xpath("//button[text()='PROCEED TO CHECKOUT']")).click();

        // Enter promo code
        driver.findElement(By.cssSelector("input.promoCode")).sendKeys(promoCode);

        // Click Apply
        driver.findElement(By.cssSelector("button.promoBtn")).click();

        // Wait for message
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement promoInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.promoInfo")));

        // Print the promo response
        System.out.println("Promo Message: " + promoInfo.getText());
    }

    public static void main(String[] args) {
        Main test = new Main();
        test.setup();

        // Add any 3 products
        String[] productsToAdd = {"Cucumber", "Brocolli", "Beans"};

        for (String product : productsToAdd) {
            test.addProductToCart(product);
        }

        // Apply random invalid promo code
        test.proceedToCheckoutAndApplyPromo("RANDOM123");

       
    }
}
