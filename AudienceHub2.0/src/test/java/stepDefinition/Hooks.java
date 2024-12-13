package stepDefinition;

import ddt_config.DataReader;
import ddt_config.ExcelDataReader;
import ddt_config.ReaderConfig;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.TestContextSetup;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class Hooks {
    TestContextSetup tcs;
    WebDriverWait wait;

    public Hooks(TestContextSetup tcs) {
        this.tcs = tcs;
        wait = new WebDriverWait(tcs.base.driver, Duration.ofSeconds(10));
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
        tcs.pom.getAuth().signIn("Account 1");
    }

    @After
    public void tearDown() throws IOException {
       // tcs.base.initializeDriver().quit();
    }

    @AfterStep
    public void takeScreenshot(Scenario scenario) throws IOException {
        WebDriver driver = tcs.base.driver;
        if (scenario.isFailed()) {
            File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            byte[] fileContent = FileUtils.readFileToByteArray(sourcePath);
            scenario.attach(fileContent, "image/png", "image");
        }
    }

    @DataTableType
    public DataReader excelToDataTable(Map<String, String> entry) {
        ReaderConfig config = new ReaderConfig.ReaderConfigBuilder()
                .setFileName(entry.get("Excel"))
                .setFileLocation(entry.get("Location"))
                .setSheetName(entry.get("Sheet"))
                .setColumnName(Integer.parseInt(entry.getOrDefault("ColumnHeader", "0")))
                .setIndex(Integer.valueOf(entry.getOrDefault("Index", "0")))
                .build();
        return new ExcelDataReader(config);
    }

}
