package myStore;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class myStore {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
    String orderPrice;


    @Given("the user is on the Amazon.com homepage")
    public void navigateToAmazonHomePage() {
        driver.get("https://www.amazon.com");
    }

    @When("the user clicks the \"Sign in\" button")
    public void clickSignInButton() {
        driver.findElement(By.id("nav-link-accountList")).click();
    }

    @When("the user enters the email {string} and password {string}")
    public void enterEmailAndPassword(String email, String password) {
        // Zaimplementuj kod do wprowadzenia adresu e-mail i hasła
        driver.findElement(By.id("ap_email")).sendKeys(email);
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("ap_password")).sendKeys(password);
        driver.findElement(By.id("signInSubmit")).click();

}

    @Then("the user is logged in")
    public void checkUserIsLoggedIn() {
        WebElement userIcon = driver.findElement(By.id("nav-link-accountList-nav-line-1"));
        Assert.assertTrue(userIcon.isDisplayed());
    }



    @When("the user enters {string} into the search bar")
    public void enterProductNameInSearchBar(String product) {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Water");

    }


    @When("the user clicks the \"Search\" button")
    public void clickSearchButton() {
        driver.findElement(By.id("nav-search-submit-button")).click();
    }
    @When("the user selects the first available product")
    public void selectFirstAvailableProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        List<WebElement> products = driver.findElements(By.cssSelector(".s-result-list .s-search-results .s-result-item"));

        for (WebElement product : products) {
            WebElement productNameElement = product.findElement(By.cssSelector("h2 a"));
            String productName = productNameElement.getText();

            if (productName.contains("a-autoid-1-announce")) {
                productNameElement.click();
                break;
            }
        }
    }

    @When("the user clicks the \"Add to Cart\" button")
    public void clickAddToCartButton() {
        driver.findElement(By.id("a-autoid-1-announce")).click();
    }

        @When("the user clicks the \"The Cart\" icon button")
        public void the_user_clicks_the_cart_icon_button() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.findElement(By.xpath("//*[@id=\"nav-cart-count-container\"]/span[2]")).click();

    }

    @Then("a screenshot of the order confirmation is saved")
    public void screenshotConfirmationIsSaved() throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = generateUniqueFileName();

        try {
            Files.move(screenshot.toPath(), Path.of(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error while saving screenshot: " + e.getMessage());
        }
    }

    private String generateUniqueFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);
        String fileName = "confirmation_screenshot_" + timestamp + ".png";
        return fileName;
    }


}
