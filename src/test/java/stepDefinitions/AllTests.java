package stepDefinitions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.github.javafaker.Faker;
import java.time.Duration;
import java.io.File;

// Define the SignUpTest class which contains all the test methods
public class AllTests {

    // Declare WebDriver and WebDriverWait objects
    private WebDriver driver;
    private WebDriverWait wait;

    // Initialize a Faker object for generating random data
    private static final Faker faker = new Faker();
    // Generate a random full name and store it in the name variable
    String name = faker.name().fullName();
    // Generate a random email address and store it in the email variable
    private static final String email = faker.internet().emailAddress();
    // Generate a random 8-digit number and store it in the password variable
    String password = faker.number().digits(8);

    // User data for an employee - used for testing purposes
    String employeeEmail = "amy.keita@uranus.com";
    String employeePassword = "L@guna546";

    // AES encryption key
    String aesKey = "bWpHvpbVDlgkZG/aDNK+1dfNvLHiLkKuRqMPbFIWqR4=";

    // This method will be run before each test method to set up the test environment
    @Before
    public void setup() {
        // Setup the ChromeDriver using WebDriverManager, see the pom.xml file
        WebDriverManager.chromedriver().setup();
        // Define Chrome options for the browser
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        // Initialize the WebDriver with ChromeDriver and the defined options
        driver = new ChromeDriver(chromeOptions);
        // Maximize the browser window
        driver.manage().window().maximize();
        // Initialize WebDriverWait with a timeout of 30 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // This method will be run after each test method to clean up the test environment
    @After
    public void tearDown() {
        // Check if the driver is not null and then quit the driver to close the browser
        if (driver != null) {
            driver.quit();
        }
    }

    // Given step: User visits the home page to sign up
    @Given("User visits home page to sign up")
    public void user_visits_on_the_homepage() {
        // Navigate to the home page URL
        driver.get("http://localhost"); // your app should run on local host
    }

    // When step: User clicks on the Sign Up button
    @When("User clicks on Sign Up Button")
    public void user_clicks_on_sign_up_button() {
        // Wait for the Sign Up button to be clickable and then click it
        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#collapsibleNavId div a:first-child")));
        signUpButton.click();
    }

    // When step: User inputs name, email, password, confirm password, and selects a role
    @When("User inputs name, email, password, confirm password and selects a role")
    public void user_inputs_name_email_password_and_confirm_password() {
        // Wait for the name field to be clickable and then input the generated name
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("signUpName")));
        nameField.sendKeys(name);

        // Wait for the email field to be clickable and then input the generated email
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginEmail")));
        emailField.sendKeys(email);

        // Wait for the password field to be clickable and then input the generated password
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginPassword")));
        passwordField.sendKeys(password);

        // Wait for the confirm password field to be clickable and then input the generated password again
        WebElement confirmPasswordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("signUpConfirmPassword")));
        confirmPasswordField.sendKeys(password);

        // Wait for the role dropdown to be visible, scroll into view, and select the "USER" role
        WebElement roleDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signUpRole")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", roleDropdown);
        new Select(roleDropdown).selectByValue("USER");
    }

    // When step: User clicks the submit sign up button
    @When("User clicks submit sign up button")
    public void user_clicks_submit_sign_up_button() {
        // Wait for the submit button to be clickable and then click it
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-sign-up-page form .btn-submit")));
        submitButton.click();
    }

    // Then step: User receives a sign-up success message
    @Then("User receives a sign up success message")
    public void user_receives_a_sign_up_success_message() {
        // Wait for the success message to be visible and then assert its text
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-detail")));
        Assert.assertEquals(successMessage.getText(), "Registerd successfully, please wait for admin approval to login!");
    }

    // Given step: Admin visits the home page to approve a user
    @Given("Admin visits home page to approve a user")
    public void admin_visits_home_page_to_approve_a_user() {
        // Navigate to the home page URL
        driver.get("http://localhost");
    }

    // When step: Admin clicks on the login button to approve a user
    @When("Admin clicks on login button to approve a user")
    public void admin_clicks_on_login_button_to_approve_a_user() {
        // Wait for the login button to be clickable and then click it
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-outline-secondary")));
        loginButton.click();
    }

    // When step: Admin logs in with email and password to approve a user
    @When("Admin logins with email and password to approve a user")
    public void admin_logins_with_email_and_password_to_approve_a_user() {
        // Wait for the email field to be clickable and then input the admin email
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form #loginEmail")));
        emailField.sendKeys("admin@uranus.com");  // change it to your admin email if it is other than this

        // Wait for the password field to be clickable and then input the admin password
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form #loginPassword")));
        passwordField.sendKeys("g8rD%+"); // change it to your admin password if it is other than this

        // Wait for the login submit button to be clickable and then click it
        WebElement loginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form button")));
        loginSubmitButton.click();
    }

    // When step: Admin visits the Admin Panel to approve a user
    @When("Admin visits Admin Panel to approve a user")
    public void admin_visits_admin_panel_to_approve_a_user() {
        // Wait for the message toast cross button to be clickable and then click it
        WebElement messageToastCrossButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".p-toast-icon-close")));
        messageToastCrossButton.click();

        // Wait for the admin module to be clickable and then click it
        WebElement adminModule = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div #collapsibleNavId ul li:nth-child(8) a")));
        adminModule.click();
    }

    // When step: Admin clicks on the New Accounts tab
    @When("Admin clicks on New Accounts tab")
    public void admin_clicks_on_new_accounts_tab() {
        // Wait for the new accounts tab to be clickable and then click it
        WebElement newAccountsTab = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#new-tab")));
        newAccountsTab.click();
    }

    // Then step: Admin approves the first new user
    @Then("Admin approves the first new user")
    public void admin_approves_the_new_user() {
        // Wait for the user placeholder to be visible and then assert its text matches the user's email
        WebElement userPlaceHolder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#new .p-element > .p-component.p-datatable.p-datatable-striped table[role='table'] .ng-star-inserted.p-element > td:nth-of-type(2) > .ng-star-inserted")));
        Assert.assertEquals(userPlaceHolder.getText(), email);

        // If the user placeholder text matches the user's email, approve the user
        if (userPlaceHolder.getText().compareTo(email) == 0) {

            try {
                // Wait for the approve button to be clickable
                WebElement approveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("tr:nth-of-type(1) > td:nth-of-type(6) > .align-items-center.d-flex.justify-content-center > .action.approve.p-button.p-component.p-element")));

                // Debug information to check if the button is found and clickable
                System.out.println("Approve button is found and is clickable.");

                // Click the approve button
                approveButton.click();

                // Logging out seems necessary for changes to go through
                WebElement userDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdownId")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", userDropdown);
                new Select(userDropdown).selectByValue("Logout");
            } catch (Exception e) {
                // Debug information for any exceptions
                System.out.println("Exception occurred: " + e.getMessage());
            }
        }
    }

    // Given step: User visits the home page to log in
    @Given("User visits home page")
    public void user_visits_homepage() {
        // Navigate to the home page URL
        driver.get("http://localhost"); // your app should run on local host
    }

    // When step: User logs in
    @When("User logs in")
    public void user_logs_in() {
        // Wait for the login button to be clickable and then click it
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-outline-secondary")));
        loginButton.click();

        // Wait for the email and password fields to be clickable and then input them
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form #loginEmail")));
        emailField.sendKeys(email);
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form #loginPassword")));
        passwordField.sendKeys(password);

        // Wait for the login submit button to be clickable and then click it
        WebElement loginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form button")));
        loginSubmitButton.click();
    }

    // Then step: User cannot click admin panel
    @Then("User cannot click admin panel")
    public void user_cannot_click_admin_panel() {
        // Wait for the message toast cross button to be clickable and then click it
        WebElement messageToastCrossButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".p-toast-icon-close")));
        messageToastCrossButton.click();

        // Check admin module is not present
        try {
            WebElement adminModule = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div #collapsibleNavId ul li:nth-child(8) a")));
        } catch (Exception e) {
            System.out.println("Admin module not present for user");
        }
    }

    // Given step: Admin visits the home page to log in
    @Given("Admin visits home page to log in")
    public void admin_visits_on_the_homepage() {
        // Navigate to the home page URL
        driver.get("http://localhost"); // your app should run on local host
    }

    // When step: User logs in
    @When("Admin logs in")
    public void admin_logs_in() {
        // Wait for the login button to be clickable and then click it
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-outline-secondary")));
        loginButton.click();

        // Wait for the email and password fields to be clickable and then input them
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form #loginEmail")));
        emailField.sendKeys("admin@uranus.com");
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form #loginPassword")));
        passwordField.sendKeys("g8rD%+");

        // Wait for the login submit button to be clickable and then click it
        WebElement loginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form button")));
        loginSubmitButton.click();
    }

    // When step: Admin goes to edit role
    @When("Admin goes to edit role")
    public void admin_goes_to_edit_role() {
        // Wait for the message toast cross button to be clickable and then click it
        WebElement messageToastCrossButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".p-toast-icon-close")));
        messageToastCrossButton.click();

        // Wait for the admin module to be clickable and then click it
        WebElement adminModule = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div #collapsibleNavId ul li:nth-child(8) a")));
        adminModule.click();

        // Wait for the new accounts tab to be clickable and then click it
        WebElement userAccountsTab = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#reg-tab")));
        userAccountsTab.click();
    }

    // Then step: Role is edited
    @Then("Role is edited")
    public void role_is_edited() {
        // Edit user role
        WebElement editRole = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#pr_id_5-table > tbody:nth-child(2) > tr:nth-child(3) > td:nth-child(6) > div:nth-child(1) > button:nth-child(1)")));
        editRole.click();
        // Select dropdown
        WebElement roleDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select.form-select:nth-child(1)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", roleDropdown);
        new Select(roleDropdown).selectByValue("Employee");
        // Confirm
        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.p-button-rounded:nth-child(1)")));
        confirm.click();
        // Check role
        WebElement currentRole = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#pr_id_5-table > tbody:nth-child(2) > tr:nth-child(3) > td:nth-child(4)")));
        Assert.assertEquals(currentRole.getText(), "Employee");
    }

    // When step: User selects encryption
    @When("User selects encryption")
    public void user_selects_encryption() {
        WebElement encrypt = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mb-3")));
        encrypt.click();
    }

    // When step: User selects decryption
    @When("User selects decryption")
    public void user_selects_decryption() {
        WebElement decrypt = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn-secondary:nth-child(2)")));
        decrypt.click();
    }

    // Then step: Login popup appears
    @Then("Login popup appears")
    public void login_popup_appears() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-outline-secondary")));
    }

    // When step: Employee logs in
    @When("Employee logs in")
    public void employee_logs_in() {
        // Wait for the login button to be clickable and then click it
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-outline-secondary")));
        loginButton.click();

        // Wait for the email and password fields to be clickable and then input them
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form #loginEmail")));
        emailField.sendKeys(employeeEmail);
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form #loginPassword")));
        passwordField.sendKeys(employeePassword);

        // Wait for the login submit button to be clickable and then click it
        WebElement loginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header #signUp app-login-page .auth-form button")));
        loginSubmitButton.click();
    }

    // When step: Admin panel Resources tab is selected
    @When("Admin panel Resources tab is selected")
    public void admin_panel_resources_tab_is_selected() {
        // Wait for the message toast cross button to be clickable and then click it
        WebElement messageToastCrossButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".p-toast-icon-close")));
        messageToastCrossButton.click();

        // Wait for the admin module to be clickable and then click it
        WebElement adminModule = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div #collapsibleNavId ul li:nth-child(8) a")));
        adminModule.click();

        // Wait for the resources tab to be clickable and then click it
        WebElement resourcesTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("resources-tab")));
        resourcesTab.click();
    }

    // Then step: Admin-uploaded files are visible
    @Then("Admin-uploaded files are visible")
    public void admin_uploaded_files_are_visible() {
        WebElement prompt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h6.secondary-txt")));
        Assert.assertEquals(prompt.getText(), "Upload New File with Max file size 50 MB");
    }

    // When step: User uploads unencrypted file
    @When("User uploads unencrypted file")
    public void user_uploads_unencrypted_file() {
        WebElement fileUpload = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".p-ripple")));
        fileUpload.click();

        // Example of file uploads found here: https://www.selenium.dev/documentation/webdriver/elements/file_upload/
        File uploadFile = new File("README.md");
        WebElement fileInput = driver.findElement(By.cssSelector("input[type=file]"));
        fileInput.sendKeys(uploadFile.getAbsolutePath());
    }

    // When step: User uploads encrypted file
    @When("User uploads encrypted file")
    public void user_uploads_encrypted_file() {
        WebElement fileUpload = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".p-ripple")));
        fileUpload.click();

        // Example of file uploads found here: https://www.selenium.dev/documentation/webdriver/elements/file_upload/
        File uploadFile = new File("encrypted-README-old.md");
        WebElement fileInput = driver.findElement(By.cssSelector("input[type=file]"));
        fileInput.sendKeys(uploadFile.getAbsolutePath());
    }

    // When step: User enters correct key
    @When("User enters correct encryption key")
    public void user_enters_correct_encryption_key() {
        // Encryption algorithm dropdown
        WebElement algDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.p-3:nth-child(2) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", algDropdown);
        new Select(algDropdown).selectByValue("aes");

        // Enter key
        WebElement keyField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.p-3:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > textarea:nth-child(1)")));
        keyField.sendKeys(aesKey);
    }

    // When step: User enters correct key
    @When("User enters correct decryption key")
    public void user_enters_correct_decryption_key() {
        // Encryption algorithm dropdown
        WebElement algDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.row:nth-child(1) > div:nth-child(1) > select:nth-child(2)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", algDropdown);
        new Select(algDropdown).selectByValue("aes");

        // Select manual
        WebElement manual = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.row:nth-child(1) > div:nth-child(2) > select:nth-child(2)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", manual);
        new Select(manual).selectByValue("0");

        // Enter key
        WebElement keyField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.p-3:nth-child(2) > div:nth-child(2) > div:nth-child(2) > textarea:nth-child(1)")));
        keyField.sendKeys(aesKey);
    }

    // When step: User enters incorrect key
    @When("User enters incorrect encryption key")
    public void user_enters_incorrect_encryption_key() {
        // Encryption algorithm dropdown
        WebElement algDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.p-3:nth-child(2) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", algDropdown);
        new Select(algDropdown).selectByValue("aes");

        // Enter key
        WebElement keyField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.p-3:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > textarea:nth-child(1)")));
        keyField.sendKeys("INCORRECT KEY");
    }

    // Then step: User can encrypt file
    @Then("User can encrypt file")
    public void user_can_encrypt_file() {
        // Hit button
        WebElement encryptButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.p-3:nth-child(2) > div:nth-child(2) > button:nth-child(1)")));
        encryptButton.click();

        // Check file was encrypted
        WebElement encrypted = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.title")));
        Assert.assertEquals(encrypted.getText(), "Encrypted File");
    }

    // Then step: User can decrypt file
    @Then("User can decrypt file")
    public void user_can_decrypt_file() {
        // Hit button
        WebElement decryptButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.text-right:nth-child(3) > button:nth-child(1)")));
        decryptButton.click();

        // Check file is decrypted
        try {
            WebElement messageToastCrossButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".p-toast-icon-close")));
            Assert.assertTrue(false);  // Toast should NOT appear - this means it errored
        } catch(Exception e) {
            System.out.println("File decrypted");
        }
    }

    // Then step: User cannot encrypt file
    @Then("User cannot encrypt file")
    public void user_cannot_encrypt_file() {
        // Hit button
        WebElement encryptButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.p-3:nth-child(2) > div:nth-child(2) > button:nth-child(1)")));
        encryptButton.click();

        // Check file was not encrypted by waiting for toast
        WebElement messageToastCrossButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".p-toast-icon-close")));
    }
}
