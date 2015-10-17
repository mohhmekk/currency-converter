package org.sample.currency.app;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.concurrent.TimeUnit;

/**
 * Created by mohamedmekkawy.
 */
public class BrowserDriver {
    private static WebDriver mDriver;
    private final static Logger logger = Logger.getLogger(BrowserDriver.class);

    static {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
    }

    public synchronized static WebDriver getCurrentDriver() {
        if (mDriver==null) {
            try {
                mDriver = new ChromeDriver();
                mDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            } finally{
                Runtime.getRuntime().addShutdownHook(
                        new Thread(new BrowserCleanup()));
            }
        }
        return mDriver;
    }

    public static void loadPage(String url){;
        logger.info("Directing browser to:" + url);
        getCurrentDriver().get(url);
    }

    private static class BrowserCleanup implements Runnable {
        public void run() {
            BrowserDriver.logger.info("Closing the browser");
            close();
        }
    }

    public static void close() {
        try {
            getCurrentDriver().quit();
            mDriver = null;
            BrowserDriver.logger.info("closing the browser");
        } catch (UnreachableBrowserException e) {
            BrowserDriver.logger.info("cannot close browser: unreachable browser");
        }
    }
}
