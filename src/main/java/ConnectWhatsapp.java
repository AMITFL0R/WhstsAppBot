import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class ConnectWhatsapp {



    public ChromeDriver initialDriver(ChromeDriver driver) {
        System.setProperty(Constants.INITIAL_DRIVER, Constants.DRIVER_PATH);
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("user-data-dir=C:\\Users\\DELL\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public ChromeDriver enterChat(String phoneNumber,ChromeDriver driver) {
        driver.get(Constants.CHAT_LINK_BY_PHONE + phoneNumber.substring(Constants.WITHOUT_ZERO));
        WebElement enterChatButton = driver.findElement(By.id(Constants.ENTER_CHAT_BUTTON));
        enterChatButton.click();
        WebElement mainBlock =driver.findElement(By.id(Constants.MAIN_BLOCK));
        List<WebElement> linkElement = mainBlock.findElements(By.tagName(Constants.GET_CHAT_LINK));
        String linkToChat = linkElement.get(1).getAttribute(Constants.CHAT_LINK_ATTR);
        driver.get(linkToChat);
        return driver;
    }

    public ChromeDriver connection(ChromeDriver driver) {
            WebElement chatScreen = null;
            try {
                chatScreen = driver.findElement(By.id(Constants.CHAT_LIST));
            } catch (Exception e) {
                connection(driver);
            }
        return driver;
    }
}
