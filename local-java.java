// Sample test in Java to run Automate session.
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.browserstack.local.Local;

public class JavaSample {
  public static final String AUTOMATE_USERNAME = "YOUR_USER_NAME";
  public static final String AUTOMATE_ACCESS_KEY = "YOUR_ACCESS_KEY";
  public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
  public static void main(String[] args) throws Exception {
    // Creates an instance of Local
    Local bsLocal = new Local();

    // You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
    HashMap<String, String> bsLocalArgs = new HashMap<String, String>();
    bsLocalArgs.put("key", "AXHzyg34Qr81Nep231pu");
    // Starts the Local instance with the required arguments
    bsLocal.start(bsLocalArgs);
    // Check if BrowserStack local instance is running
    System.out.println(bsLocal.isRunning());

    ChromeOptions browserOptions = new ChromeOptions();
    browserOptions.setPlatformName("MAC");
    browserOptions.setBrowserVersion("98");
    HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
    bstackOptions.put("os", "OS X");
    bstackOptions.put("osVersion", "Sierra");
    bstackOptions.put("buildName", "Final-Snippet-Test");
    bstackOptions.put("sessionName", "Selenium-4 PHP snippet test");
    bstackOptions.put("local", "true");
    bstackOptions.put("seleniumVersion", "4.0.0");
    browserOptions.setCapability("bstack:options", bstackOptions);
    final WebDriver driver = new RemoteWebDriver(new URL(URL), browserOptions);
    try {
      driver.get("http://localhost:3000/sample.html");
      if (driver.getTitle().equals("Browserstack local demo")) {
        markTestStatus("passed", "Java locally tested success", driver);
        }
      } catch (Exception e) {
          markTestStatus("failed", "Some elements failed to load", driver);
        }
      driver.quit();
    }
  // This method accepts the status, reason and WebDriver instance and marks the test on BrowserStack
  public static void markTestStatus(String status, String reason, WebDriver driver) {
    final JavascriptExecutor jse = (JavascriptExecutor) driver;
    jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""+ status + "\", \"reason\": \"" + reason + "\"}}");
  }
} 
