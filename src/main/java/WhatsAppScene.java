import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import javax.swing.*;
import java.awt.*;

public class WhatsAppScene extends JPanel {


    private ImageIcon backGround;
    private JTextField phoneNumberField;
    private JTextField textField;
    private JLabel phoneNumberLabel;
    private JLabel textLabel;
    private JLabel welcomeSign;
    private JButton openWhatsApp;
    private ChromeDriver driver;
    private JLabel messageStatus;
    private WebElement lastMessage;
    private MessageManagement management;
    private String comment;
    private JLabel loading;
    private JFrame window;


    public WhatsAppScene(JFrame frame,int x, int y, int width, int height) {
        this.window=frame;
        this.backGround = new ImageIcon(Constants.BACKGROUND_NAME);
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        this.management = new MessageManagement();
        mainView();
        logInListener();
        this.setDoubleBuffered(true);
        this.setVisible(true);
    }

    private void mainView() {
        this.openWhatsApp = Helper.addButton(this, Constants.OPEN_BUTTON_NAME, this.getWidth() / 2 - Constants.OPEN_BUTTON_WIDTH / 2, Constants.OPEN_BUTTON_Y, Constants.OPEN_BUTTON_WIDTH, Constants.OPEN_BUTTON_HEIGHT);
        this.phoneNumberField = Helper.addTextField(this, this.getWidth() / 3 - Constants.TEXT_FIELD_WIDTH / 2, this.getHeight() / 2 - Constants.TEXT_FIELD_HEIGHT / 2, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_HEIGHT);
        this.phoneNumberLabel = Helper.addLabel(this, Constants.ENTER_PHONE, this.phoneNumberField.getX(), this.phoneNumberField.getY() - Constants.TEXT_FIELD_HEIGHT * 5 / 6, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_HEIGHT);
        this.textField = Helper.addTextField(this, this.getWidth() * 2 / 3 - Constants.TEXT_FIELD_WIDTH / 2, this.getHeight() / 2 - Constants.TEXT_FIELD_HEIGHT / 2, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_HEIGHT);
        this.textLabel = Helper.addLabel(this, Constants.ENTER_TEXT, this.textField.getX(), this.phoneNumberField.getY() - Constants.TEXT_FIELD_HEIGHT * 5 / 6, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_HEIGHT);
        this.welcomeSign = Helper.addLabel(this, Constants.WELCOME_PRINTING, this.phoneNumberField.getX(), this.getHeight() / 4, Constants.WELCOME_MASSAGE_WIDTH, Constants.TEXT_FIELD_HEIGHT);

    }

    private void logInListener() {
        this.openWhatsApp.addActionListener((event) -> {
            checkValidation();
        });
    }

    private void checkValidation() {
        ValidDetails validDetails = new ValidDetails();
        if (validDetails.validText(this.phoneNumberField.getText()) || validDetails.validText(this.textField.getText())) {
            print(Constants.EMPTY_FIELD);
        } else if (!validDetails.validPhoneNumber(this.phoneNumberField.getText())) {
            print(Constants.INVALID_NUMBER);
        } else {
            removeMainView();
            connectWhatsApp();

        }
    }
    private void removeMainView() {
        new Thread(() -> {
            this.textLabel.setVisible(false);
            this.phoneNumberLabel.setVisible(false);
            this.textField.setVisible(false);
            this.phoneNumberField.setVisible(false);
            this.openWhatsApp.setVisible(false);
            this.loading=Helper.addLabel(this,Constants.PRINT_LOADING,this.getWidth()/2- Constants.PRINT_WIDTH/2,this.getHeight()- Constants.PRINT_HEIGHT- Constants.BELOW_MARGIN, Constants.PRINT_WIDTH, Constants.PRINT_HEIGHT);
            repaint();
        }).start();

    }

    private void connectWhatsApp() {
        ConnectWhatsapp connect = new ConnectWhatsapp();
        new Thread(() -> {
            this.driver = connect.initialDriver(this.driver);
            this.driver = connect.enterChat(this.phoneNumberField.getText(), this.driver);
            this.driver = connect.connection(this.driver);
            this.loading.setVisible(false);
            print(Constants.PRINT_CONNECTION);
            new Thread(()->{
                this.driver = this.management.sendMessage(this.driver, this.textField.getText());
                printMessageSent();
                this.lastMessage = this.management.getLastMessage(this.driver);
                this.management.messageStatus();
                messageStatus();
            }).start();
            new Thread(()->{
                this.comment = this.management.comment(this.driver);
                receivedMessage();
                createReport();
            }).start();
        }).start();
    }

    private void createReport(){
        JButton reportButton= Helper.addButton(this,Constants.REPORT_BUTTON_NAME,this.getWidth()- Constants.REPORT_BUTTON_WIDTH- Constants.RIGHT_MARGIN,this.getHeight()- Constants.REPORT_BUTTON_HEIGHT- Constants.BELOW_MARGIN, Constants.REPORT_BUTTON_WIDTH, Constants.REPORT_BUTTON_HEIGHT);
        reportButton.addActionListener((e)->{
            CreateReport report=new CreateReport(this.phoneNumberField.getText(),this.textField.getText(),this.comment);
            print(Constants.PRINT_REPORT);
            new Thread(()->{
                try {
                    Thread.sleep(Constants.WAITING);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.window.dispose();
                this.driver.close();
            }).start();


        });
    }



    private void receivedMessage() {
        new Thread(() -> {
            while (!this.management.isReceived()) {
                try {
                    Thread.sleep(Constants.SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            printMessageContent();
        }).start();
    }



    private void messageStatus() {
        new Thread(() -> {
            while (!this.management.isSent()) {
                try {
                    Thread.sleep(Constants.WAITING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (!this.management.isRead()) {
                switch (this.management.getMessageStatus()) {
                    case Constants.SENT:
                        printMessageStatus(Constants.PRINT_SENT, Color.black);
                        break;
                    case Constants.DELIVER:
                        printMessageStatus(Constants.PRINT_DELIVER, Color.black);
                        break;
                }
            }
            printMessageStatus(Constants.PRINT_DELIVER, Color.blue);
        }).start();
    }

    private void printMessageContent(){
        JLabel titleMessageContent=Helper.addLabel(this,Constants.MESSAGE_CONTENT_TITLE,0,this.getHeight()*4/7, Constants.PRINT_WIDTH, Constants.PRINT_HEIGHT);
        JLabel messageContent=Helper.addLabel(this,this.management.getComment(),0,titleMessageContent.getY()+titleMessageContent.getHeight(), Constants.COMMENT_WIDTH, Constants.PRINT_HEIGHT);
        messageContent.setForeground(Color.black);
        repaint();

    }

    private void printMessageSent() {
        new Thread(() -> {
            JLabel messageSent = Helper.addLabel(this, Constants.PRINT_MESSAGE_SENT, this.getWidth() / 2 - Constants.SEND_PRINT_WIDTH / 2, this.getHeight() * 4 / 5 - Constants.SEND_PRINT_HEIGHT / 2, Constants.SEND_PRINT_WIDTH, Constants.SEND_PRINT_HEIGHT);
            JLabel messageStatus = Helper.addLabel(this, Constants.MESSAGE_STATUS_TITLE, 0, this.getHeight() - Constants.MESSAGE_STATUS_HEIGHT * 4, Constants.MESSAGE_STATUS_WIDTH, Constants.MESSAGE_STATUS_HEIGHT);
            this.messageStatus = Helper.addLabel(this, "", 0, this.getHeight() - Constants.MESSAGE_STATUS_HEIGHT * 2, Constants.MESSAGE_STATUS_WIDTH, Constants.MESSAGE_STATUS_HEIGHT);

            repaint();
            try {
                Thread.sleep(Constants.PRINT_SHOW_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageSent.setVisible(false);
        }).start();

    }

    private void printMessageStatus(String status, Color color) {
        Font font = new Font(Constants.FONT_TYPE, Font.BOLD, Constants.MESSAGE_FONT_SIZE);
        this.messageStatus.setText(status);
        this.messageStatus.setFont(font);
        this.messageStatus.setForeground(color);
        repaint();

    }

    private void print(String printOnBoard) {
        new Thread(() -> {
            JLabel print = Helper.addLabel(this, printOnBoard, this.getWidth() / 2 - Constants.PRINT_WIDTH / 2, this.openWhatsApp.getY() - Constants.PRINT_HEIGHT, Constants.PRINT_WIDTH, Constants.PRINT_HEIGHT);
            repaint();
            try {
                Thread.sleep(Constants.PRINT_SHOW_TIME);
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
