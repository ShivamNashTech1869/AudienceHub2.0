package pages;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {
    WebDriver driver;
    Auth auth;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public Auth getAuth(){
        auth=new Auth(driver);
        return auth;
    }
}

