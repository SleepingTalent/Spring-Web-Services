package com.fs.common;

import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public abstract class BaseSeleniumTest {

    private WebDriver driver;

    protected APIHelper apiHelper;

    protected BaseSeleniumTest() {
        driver = initialiseDriver();
        apiHelper = new APIHelper();
    }

    @After
    public void tearDown() {
        driver.close();
    }

    private WebDriver initialiseDriver() {
        return new HtmlUnitDriver();
    }

    protected WebDriver getDriver() {
        return driver;
    }


}
