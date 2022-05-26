import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WhatsAppScene extends JPanel {

    public static final int WELCOME_MASSAGE_WIDTH = 350,
            ERROR_SHOW_TIME = 3000,FONT_SIZE_BUTTON = 18,
            OPEN_BUTTON_WIDTH = 180, OPEN_BUTTON_HEIGHT = 50, OPEN_BUTTON_Y = 450,
            TEXT_FIELD_WIDTH = 200, TEXT_FIELD_HEIGHT = 60,
            ERROR_PRINT_WIDTH = 220, ERROR_PRINT_HEIGHT = 70,
            SEND_PRINT_WIDTH = 300, SEND_PRINT_HEIGHT = 70,
            MESSAGE_STATUS_WIDTH = 200, MESSAGE_STATUS_HEIGHT = 40;


    private ImageIcon backGround;
    private JTextField phoneNumberField;
    private JTextField textField;
    private JLabel welcomeSign;
    private JButton openWhatsApp;
    private ChromeDriver driver;
    private boolean connect;
    private JLabel messageStatus;
    private boolean isSent;
    private WebElement lastMessage;
    private boolean isRead;



    public WhatsAppScene(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        mainView();
        logInListener();
        this.backGround = new ImageIcon("C:\\Users\\DELL\\IdeaProjects\\NewWhatsApp\\src\\main\\resources\\whatsapp web.png");
        this.setDoubleBuffered(true);
        this.setVisible(true);
    }

    private void mainView() {
        this.openWhatsApp = Helper.addButton(this, "Open WhatsApp", this.getWidth() / 2 - OPEN_BUTTON_WIDTH / 2, OPEN_BUTTON_Y, OPEN_BUTTON_WIDTH, OPEN_BUTTON_HEIGHT);
        this.phoneNumberField = Helper.addTextField(this, this.getWidth() / 3 - TEXT_FIELD_WIDTH / 2, this.getHeight() / 2 - TEXT_FIELD_HEIGHT / 2, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        JLabel phoneNumber = Helper.addLabel(this, "Enter Phone Number", this.phoneNumberField.getX(), this.phoneNumberField.getY() - TEXT_FIELD_HEIGHT * 5 / 6, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        this.textField = Helper.addTextField(this, this.getWidth() * 2 / 3 - TEXT_FIELD_WIDTH / 2, this.getHeight() / 2 - TEXT_FIELD_HEIGHT / 2, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        JLabel textField = Helper.addLabel(this, "Enter Your Text", this.textField.getX(), this.phoneNumberField.getY() - TEXT_FIELD_HEIGHT * 5 / 6, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        this.welcomeSign = Helper.addLabel(this, "Hey! Welcome to our whatsappBot", this.phoneNumberField.getX(), this.getHeight() / 4, WELCOME_MASSAGE_WIDTH, TEXT_FIELD_HEIGHT);

    }

    private void logInListener() {
        this.openWhatsApp.addActionListener((event) -> {
            checkValidation();

        });
    }

    private void checkValidation() {
        ValidDetails validDetails = new ValidDetails();
        if (validDetails.validText(this.phoneNumberField.getText()) || validDetails.validText(this.textField.getText())) {
            print("Fill in the empty field");
        } else if (!validDetails.validPhoneNumber(this.phoneNumberField.getText())) {
            print("Invalid phone number");
        } else {
            connectWhatsApp();

        }
    }

    private void connectWhatsApp() {
        ConnectWhatsapp connect=new ConnectWhatsapp();
        this.driver=connect.initialDriver(this.driver);
        this.driver=connect.connection(this.driver);
        this.driver=connect.enterChat(this.phoneNumberField.getText(),this.driver);
        while (!connect.isConnect()) {
            new Thread(() -> {
            }).start();
        }
        sendMessage();
        print("connection succeeded");
        new Thread(()->{
            getLastMessage();
            messageStatus();
        }).start();

    }
    private void getLastMessage() {
        new Thread(()->{
            try {
                while (!this.isSent) {
                    Thread.sleep(1000);

                }
                WebElement chat = this.driver.findElement(By.className("_33LGR"));
                WebElement chatBody = chat.findElement(By.cssSelector("div[tabindex='-1'][class='_3K4-L']"));
                List<WebElement> allMessage = chatBody.findElements(By.className("_22Msk"));
                this.lastMessage = allMessage.get(allMessage.size() - 1);
            } catch (Exception e) {
                getLastMessage();
            }
        }).start();
    }


    private void messageStatus() {
        new Thread(() -> {
            WebElement messageStatus = null;
            try {
                String status;
                do {
                    messageStatus = this.lastMessage.findElement(By.cssSelector("span[data-testid='msg-dblcheck']"));
                    status = messageStatus.getAttribute("aria-label");
                    if (status.equals(" נמסרה ")) {
                        printMessageStatus("VV", Color.black);
                    }
                } while (!status.equals(" נקראה "));
                this.isRead = true;
                printMessageStatus("VV", Color.blue);
            } catch (Exception e) {
                messageStatus();
            }
        }).start();

    }






    private void sendMessage() {
        new Thread(() -> {
            WebElement footerTextBox = null;
            try {
                footerTextBox = this.driver.findElement(By.tagName("footer"));
                WebElement textBox = footerTextBox.findElement(By.cssSelector("div[role='textbox']"));
                textBox.sendKeys(this.textField.getText());
                footerTextBox.findElement(By.cssSelector("button[class='tvf2evcx oq44ahr5 lb5m6g5c svlsagor p2rjqpw5 epia9gcq']")).click();
                JLabel messageSent = Helper.addLabel(this, "Message Sent successfully", this.getWidth() / 2 - SEND_PRINT_WIDTH / 2, this.getHeight() * 4 / 5 - SEND_PRINT_HEIGHT / 2, SEND_PRINT_WIDTH, SEND_PRINT_HEIGHT);
                JLabel messageStatus = Helper.addLabel(this, "Message Status: ", 0, this.getHeight() - MESSAGE_STATUS_HEIGHT * 4, MESSAGE_STATUS_WIDTH, MESSAGE_STATUS_HEIGHT);
               this.messageStatus = Helper.addLabel(this,"", 0, this.getHeight() - MESSAGE_STATUS_HEIGHT * 2, MESSAGE_STATUS_WIDTH, MESSAGE_STATUS_HEIGHT);
                printMessageStatus("V", Color.black);
               this.isSent = true;
                repaint();
                Thread.sleep(3000);
                messageSent.setVisible(false);
            } catch (Exception e) {
                sendMessage();
            }
        }).start();
    }
    private void printMessageStatus(String status, Color color) {
        Font font = new Font("Ariel", Font.BOLD, FONT_SIZE_BUTTON * 2);
        this.messageStatus.setText(status);
        this.messageStatus.setFont(font);
        this.messageStatus.setForeground(color);
        repaint();

    }

    private void print(String printOnBoard) {
        new Thread(() -> {
            JLabel print = Helper.addLabel(this, printOnBoard, this.getWidth() / 2 - ERROR_PRINT_WIDTH / 2, this.openWhatsApp.getY() - ERROR_PRINT_HEIGHT, ERROR_PRINT_WIDTH, ERROR_PRINT_HEIGHT);
            repaint();
            try {
                Thread.sleep(ERROR_SHOW_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print.setVisible(false);
        }).start();
    }


    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(this.backGround.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }
}
