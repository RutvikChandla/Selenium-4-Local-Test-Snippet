from browserstack.local import Local
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException

# Creates an instance of Local
bs_local = Local()

# You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
bs_local_args = { "key": "" }

# Starts the Local instance with the required arguments
bs_local.start(**bs_local_args)

# Check if BrowserStack local instance is running
print(bs_local.isRunning())

desired_cap = {
    "os" : "OS X",
    "osVersion" : "Sierra",
    "buildName" : "Selenium-4-local-snippets",
    "sessionName" : "Python local test",
    "local" : "true",
    "seleniumVersion" : "4.0.0",
}

options = ChromeOptions()
options.set_capability('bstack:options', desired_cap)

driver = webdriver.Remote(
    command_executor='https://USER_NAME:ACCESS_KEY@hub-cloud.browserstack.com/wd/hub',
    options=options)

try:
    driver.get("http://localhost:3000/sample.html")
    
    if driver.title == "Browserstack local demo":
        # Set the status of test as 'passed' or 'failed' based on the condition; if item is added to cart
        driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed", "reason": "python locally tested"}}')
except NoSuchElementException:
    driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed", "reason": "Some elements failed to load"}}')
# Stop the driver
driver.quit() 
# Stop the Local instance
bs_local.stop()

