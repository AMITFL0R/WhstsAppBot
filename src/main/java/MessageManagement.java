import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class MessageManagement {


    private boolean isSent;
    private WebElement lastMessage;
    private int messageStatus;
    private boolean isRead;
    private String comment;
    private boolean received;

    public boolean isReceived() {
        return received;
    }

    public boolean isRead() {
        return isRead;
    }

    public int getMessageStatus() {
        return messageStatus;
    }


    public boolean isSent() {
        return this.isSent;
    }
    public String getComment() {
        return comment;
    }



    public ChromeDriver sendMessage(ChromeDriver driver, String text) {
        WebElement footerTextBox = null;
        try {
            footerTextBox = driver.findElement(By.tagName(Constants.FOOTER));
            WebElement textBox = footerTextBox.findElement(By.cssSelector(Constants.TEXT_BOX));
            textBox.sendKeys(text);
            footerTextBox.findElement(By.cssSelector(Constants.SEND_BUTTON)).click();
            this.isSent=true;
        } catch (Exception e) {
            sendMessage(driver, text);
        }
        return driver;
    }

    public WebElement getLastMessage(ChromeDriver driver) {
            try {
                while (!this.isSent) {
                    Thread.sleep(Constants.SLEEP);
                }
                WebElement chat = driver.findElement(By.className(Constants.CHAT));
                WebElement chatBody = chat.findElement(By.cssSelector(Constants.CHAT_BODY));
                List<WebElement> allMessage = chatBody.findElements(By.cssSelector(Constants.ALL_MESSAGE));
                this.lastMessage = allMessage.get(allMessage.size() - 1);

            } catch (Exception e) {
                getLastMessage(driver);
            }
        return this.lastMessage;
    }

    public void messageStatus() {
        new Thread(() -> {
            WebElement messageStatus = null;
            try {
                String status;
                while (!this.isSent) {

                    Thread.sleep(Constants.SLEEP);
                }
                do {
                    messageStatus = this.lastMessage.findElement(By.cssSelector(Constants.MESSAGE_STATUS));
                    status = messageStatus.getAttribute(Constants.STATUS);

                    if (status.equals(Constants.DELIVERED)) {
                        this.messageStatus = Constants.DELIVER;
                    }
                } while (!status.equals(Constants.READ));
                this.isRead=true;
            } catch (Exception e) {
                messageStatus();
            }
        }).start();

    }

    public String comment(ChromeDriver driver){
            while (!this.isSent){
                try {
                    Thread.sleep(Constants.WAITING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (true){
                try {
                    Thread.sleep(Constants.SLEEP_TIME);
                this.lastMessage=getLastMessage(driver);
                String messageClass=this.lastMessage.getAttribute(Constants.MESSAGE_CLASS_ATTR);
                if (messageClass.contains(Constants.ONLY_MESSAGE_IN)){
                    WebElement comment=this.lastMessage.findElement(By.cssSelector(Constants.COMMENT_TEXT));
                    this.comment=comment.getText();
                    break;
                }
                } catch (Exception e) {
                    this.comment=comment(driver);
                }
            }
            this.received=true;
        return this.comment;
    }

}
