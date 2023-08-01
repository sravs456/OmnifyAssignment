//package PageObjects;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class EmployeeListPage {
//    private WebDriver driver;
//
//    public EmployeeListPage(WebDriver driver) {
//        this.driver = driver;
//    }
//
//    public By employeeNameInput = By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input");
//
//    public void searchEmployeeByName(String employeeName) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement empNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(employeeNameInput));
//        empNameInput.clear();
//        empNameInput.sendKeys(employeeName);
//    }
//
//    public boolean isEmployeeNamePresent(String employeeName) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.attributeToBe(employeeNameInput, "value", employeeName));
//
//        Actions actions = new Actions(driver);
//        actions.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE).perform();
//
//        String inputText = empNameInput.getAttribute("value");
//        return inputText.toLowerCase().contains(employeeName.toLowerCase());
//    }
//}
