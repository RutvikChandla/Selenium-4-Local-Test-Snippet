using System;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;
using System.Collections.Generic;
using BrowserStack;
using OpenQA.Selenium.Chrome;

namespace SeleniumTest
{
    class Program1
    {
        static void Main(string[] args)
        {
            // Creates an instance of Local
            Local local = new Local();

            // You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
            List<KeyValuePair<string, string>> bsLocalArgs = new List<KeyValuePair<string, string>>();
            // Starts the Local instance with the required arguments
            bsLocalArgs.Add(new KeyValuePair<string, string>("key", ""));
            local.start(bsLocalArgs);

            // Check if BrowserStack local instance is running
            Console.WriteLine(local.isRunning());

            ChromeOptions capabilities = new ChromeOptions();
            capabilities.BrowserVersion = "latest";
            Dictionary<string, object> browserstackOptions = new Dictionary<string, object>();
            browserstackOptions.Add("os", "OS X");
            browserstackOptions.Add("osVersion", "Sierra");
            browserstackOptions.Add("buildName", "Selenium-4-local-snippets");
            browserstackOptions.Add("sessionName", "local c# test");
            browserstackOptions.Add("local", "true");
            browserstackOptions.Add("seleniumVersion", "4.0.0");
            browserstackOptions.Add("userName", "");
            browserstackOptions.Add("accessKey", "");
            capabilities.AddAdditionalOption("bstack:options", browserstackOptions);

            RemoteWebDriver driver = new RemoteWebDriver(new Uri("https://hub-cloud.browserstack.com/wd/hub/"), capabilities);
            driver.Navigate().GoToUrl("http:localhost:3000/sample.html");
            if(driver.Title == "Browserstack local demo")
            {
                ((IJavaScriptExecutor)driver).ExecuteScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"passed\", \"reason\": \" Locally tested C sharp\"}}");
            }
            driver.Quit();
            // Stop the Local instance
            local.stop();
        }
    }
}

