package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddEmployeePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public AddEmployeePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By firstNameInput = By.xpath("//input[@name='firstName']");
    private By middleNameInput = By.xpath("//input[@name='middleName']");
    private By lastNameInput = By.xpath("//input[@name='lastName']");
    private By addButton = By.xpath("//html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[3]/button[2]");

    public void enterFirstName(String firstName) {
        WebElement firstNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        firstNameElement.sendKeys(firstName);
    }

    public void enterMiddleName(String middleName) {
        WebElement middleNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(middleNameInput));
        middleNameElement.sendKeys(middleName);
    }

    public void enterLastName(String lastName) {
        WebElement lastNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput));
        lastNameElement.sendKeys(lastName);
    }

    public void clickAddButton() {
        WebElement addButtonElement = wait.until(ExpectedConditions.elementToBeClickable(addButton));
        addButtonElement.click();
    }
}
