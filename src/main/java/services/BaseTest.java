package services;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import lombok.extern.java.Log;
import org.testng.annotations.*;

import java.net.MalformedURLException;

import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

@Log
public class BaseTest {

    @Parameters({"browser","baseUrl", "server","port"})
    @BeforeSuite
    public void startTestSuite(@Optional("chrome") String browser,
                               @Optional("http://storefront.templatemonsterdev.com/") String baseUrl,
                               @Optional("localhost") String gridValue,
                               @Optional("4444") String port) throws MalformedURLException {

        Configuration.browser = browser;
        Configuration.baseUrl = baseUrl;
        Configuration.timeout = 10000;
        Configuration.collectionsTimeout = 10000;
        Configuration.remote = getGrid(gridValue, port);
    }

    @AfterTest
    public void finishTest(){
        log.info("Test finished");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        clearBrowserCache();
    }

    private String getGrid(String gridValue, String port) {
        return "http://" + gridValue + ":" + port + "/wd/hub";
    }

}
