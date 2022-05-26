import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class ConnectWhatsapp {
    private boolean connect;





    public boolean isConnect() {
        return connect;
    }

    public ChromeDriver initialDriver(ChromeDriver driver) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromeDriver\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-data-dir=C:\\Users\\DELL\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        return driver;
    }

    public ChromeDriver enterChat(String phoneNumber,ChromeDriver driver) {
        driver.get("https://api.whatsapp.com/send?phone=972" + phoneNumber.substring(1));
        WebElement enterChatButton = driver.findElement(By.id("action-button"));
        enterChatButton.click();
        WebElement mainBlock =driver.findElement(By.id("fallback_block"));
        List<WebElement> linkElement = mainBlock.findElements(By.tagName("a"));
        String linkToChat = linkElement.get(1).getAttribute("href");
        driver.get(linkToChat);
        return driver;


    }

    public ChromeDriver connection(ChromeDriver driver) {
        new Thread(() -> {
            WebElement chatScreen = null;
            try {
                chatScreen = driver.findElement(By.id("side"));
                this.connect = true;
            } catch (Exception e) {
                connection(driver);
            }
        }).start();
        return driver;
    }
}
