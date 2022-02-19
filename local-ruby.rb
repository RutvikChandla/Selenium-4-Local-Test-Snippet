require 'rubygems'
require 'selenium-webdriver'
require "browserstack/local"

# Creates an instance of Local
bs_local = BrowserStack::Local.new

# You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
bs_local_args = { "key" => "" }

# Starts the Local instance with the required arguments
bs_local.start(bs_local_args)

options = Selenium::WebDriver::Options.chrome
options.browser_version = 'latest'
options.platform_name = 'MAC'
bstack_options = {
    "os" => "OS X",
    "osVersion" => "Sierra",
    "buildName" => "Selenium-4-local-snippets",
    "sessionName" => "ruby local",
    "local" => "true",
    "seleniumVersion" => "4.0.0",
}
options.add_option('bstack:options', bstack_options)

driver = Selenium::WebDriver.for(:remote,
  :url => "https://USER_NAME:ACCESS_KEY@hub-cloud.browserstack.com/wd/hub",
  :capabilities => options)
begin
    # opening the bstackdemo.com website
    driver.navigate.to "http://localhost:3000/sample.html"
    if driver.title.eql? "Browserstack local demo"
        # marking test as 'passed' if the product is successfully added to the cart
        driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed", "reason": "Local test sucessful"}}')
    else
        # marking test as 'failed' if the product is not added to the cart
        driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed", "reason": "could not connect to local host"}}')
    end
# marking test as 'failed' if test script is unable to open the bstackdemo.com website
rescue
    driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed", "reason": "Some elements failed to load"}}')
end
driver.quit 
# Stop the Local instance
bs_local.stop
