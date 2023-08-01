package StepDefinitions;

import PageObjects.AddEmployeePage;
import PageObjects.LoginPage;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HRMSteps {
    public WebDriver driver;
    public LoginPage loginPage;
    public AddEmployeePage addEmployeePage;

    public List<String> addedEmployeeNames = new ArrayList<>();

    @Given("User is on the login page")
    public void userIsOnLoginPage() {
        // Set up WebDriver and navigate to the login page
        System.setProperty("web-driver.chrome.driver", "C://Users//Sravani//Downloads//chromedriver-win64//chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
    }

    @When("User enters valid username {string} and password {string}")
    public void userEntersValidCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("User clicks on the login button")
    public void userClicksOnLoginButton() {
        loginPage.clickLoginButton();
    }

    @When("User clicks on PIM")
    public void userClicksOnPIM() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        WebElement pimMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body/div[@id='app']/div[1]/div[1]/aside[1]/nav[1]/div[2]/ul[1]/li[2]/a[1]/span[1]")));
        pimMenu.click();
    }

    @Then("User adds employees from the data in the CSV file")
    public void userAddsEmployeesFromCSV() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        String csvFilePath = "C://Users//Sravani//OneDrive//Desktop//Important//OrangeHRM.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> employeeDataList = reader.readAll();

            // Skip the header row (if present)
            if (!employeeDataList.isEmpty()) {
                employeeDataList.remove(0);
            }
            for (String[] employeeData : employeeDataList) {
                String firstName = employeeData[0];
                String middleName = employeeData[1];
                String lastName = employeeData[2];
                addEmployee(firstName, middleName, lastName);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(String firstName, String middleName, String lastName) {
        addEmployeePage = new AddEmployeePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        WebElement addEmp = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//html/body/div/div[1]/div[1]/header/div[2]/nav/ul/li[3]")));
        addEmp.click();
        addEmployeePage.enterFirstName(firstName);
        addEmployeePage.enterMiddleName(middleName);
        addEmployeePage.enterLastName(lastName);
        addEmployeePage.clickAddButton();
        addedEmployeeNames.add(firstName);
        driver.navigate().back();
    }

    @And("User verifies the presence of added employee on employee list page")
    public void userVerifiesPresenceOfAddedEmployee() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement employeeListLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[1]/header/div[2]/nav/ul/li[2]/a")));
        employeeListLink.click();

        Actions actions = new Actions(driver);

        for (String employeeName : addedEmployeeNames) {
            // Clear the input field
            WebElement empNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input")));
            empNameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);

            // Type the employee name
            empNameInput.sendKeys(employeeName);

            // Use Actions class to press down arrow and then press Enter to select the first autosuggestion
            actions.sendKeys(empNameInput, Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

            // Wait for the brief moment for the suggestion to become stable before verifying
            wait.until(ExpectedConditions.attributeToBe(empNameInput, "value", employeeName));

            // Verify if the employee name is present in the input field
            if (empNameInput.getAttribute("value").toLowerCase().contains(employeeName.toLowerCase())) {
                System.out.println("Verified: " + employeeName);
            } else {
                System.out.println("No records found for: " + employeeName);
            }
        }
    }

    @Then("User logs out from the dashboard")
    public void userLogsOut() {
        // Implement the code to log out from the dashboard
        WebElement logoutLink = driver.findElement(By.xpath("//header/div[1]/div[2]/ul[1]/li[1]/span[1]/i[1]"));
        logoutLink.click();
        WebElement logoutButton = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
        logoutButton.click();
    }

    @After
    public void tearDown() {
        // Close the WebDriver after the test execution
        driver.quit();
    }
}
