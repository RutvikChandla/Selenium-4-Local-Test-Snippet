<?php
require_once("vendor/autoload.php");
use BrowserStack\Local;
use Facebook\WebDriver\Remote\RemoteWebDriver;
use Facebook\WebDriver\WebDriverBy;
use Facebook\WebDriver\WebDriverExpectedCondition;
# Creates an instance of Local
$bs_local = new Local();

# You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
$bs_local_args = array("key" => "");

# Starts the Local instance with the required arguments
$bs_local->start($bs_local_args);

# Check if BrowserStack local instance is running
echo $bs_local->isRunning();
$caps = array(
	'bstack:options' => array(
		"os" => "OS X",
		"osVersion" => "Sierra",
		"buildName" => "Selenium-4-local-snippets",
		"sessionName" => "PHP local test",
		"local" => "true",
		"seleniumVersion" => "4.0.0",
	),
	"browserName" => "Chrome",
	"browserVersion" => "latest",
);

$web_driver = RemoteWebDriver::create("https://USER_NAME:ACCESS_KEY@hub-cloud.browserstack.com/wd/hub",$caps);

$web_driver->get("http:localhost:3000/sample.html");
$title = $web_driver->getTitle();

if ($title == "Browserstack local demo"){
    $web_driver->executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed", "reason": "PHP local tested successfully"}}' );
}

$web_driver->quit();
# Stop the Local instance
$bs_local->stop();
?>
