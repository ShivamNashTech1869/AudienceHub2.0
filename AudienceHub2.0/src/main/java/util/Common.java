package util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageObjectManager;
import resources.Base;
import ddt_config.DataReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class Common extends Base{
    PageObjectManager pom;
    public WebDriver driver;
    public Properties properties;

    public Common(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pom=new PageObjectManager(driver);
    }

    public void ExcelPageExtractor(DataReader dataReader, String pageName) {
        List<Map<String, String>> excelRows = dataReader.getAllRows();
        for (Map<String, String> excelRow : excelRows) {
            String productPage = excelRow.get(pageName);
            driver.get(driver.getCurrentUrl()+productPage);
        }
    }
    public void setImplicitWait(int waitTimeInSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTimeInSeconds));
    }


}
