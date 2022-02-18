const browserstack = require('browserstack-local');
const webdriver = require('selenium-webdriver');
// Creates an instance of Local
const bs_local = new browserstack.Local();

// You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
const bs_local_args = { key: '' };

// Starts the Local instance with the required arguments
bs_local.start(bs_local_args, function() {
  console.log('Started BrowserStackLocal');

  // Check if BrowserStack local instance is running
  console.log('BrowserStackLocal running:', bs_local.isRunning());

  runSessionWithLocal()
 
});

async function runSessionWithLocal() {
    const capabilities = {
        'bstack:options' : {
            "os" : "OS X",
            "osVersion" : "Sierra",
            "buildName" : "Selenium-4-local-snippets",
            "sessionName" : "nodejs",
            "local" : "true", // ** IMPORTANT **
            "seleniumVersion" : "4.0.0",
            "userName" : "", // ** Fill YOUR_USER_NAME
            "accessKey" : "", // ** Fill YOUR_ACCESS_KEY
        },
        "browserName" : "Chrome",
        "browserVersion" : "latest",
    }

    let driver = new webdriver.Builder().usingServer(`https://hub-cloud.browserstack.com/wd/hub`).withCapabilities(capabilities).build();
    try {
        await driver.get("http://localhost:3000/sample.html");
        const title = await driver.getTitle();
        console.log(title)
        if(title == "Browserstack local demo"){
            await driver.executeScript(
                'browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed","reason": "Localhost tested"}}'
            );
        }else{
            await driver.executeScript(
                'browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "Endpoint is not accessible"}}'
              );
        }
    } catch (e) {
        console.log(e)
    }
    await driver.quit()
     // Stop the Local instance after your test run is completed, i.e after driver.quit
    bs_local.stop(function() {
        console.log('Stopped BrowserStackLocal');
    });
}

