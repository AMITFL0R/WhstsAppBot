import javax.swing.*;

public class Window extends JFrame {

    public static void main(String[] args) {
        Window window = new Window();
    }

    public Window() {
        this.setSize(Constants.WIN_WID, Constants.WIN_HEI);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle(Constants.MAIN_TITLE);
        WhatsAppScene whatsAppScene=new WhatsAppScene
                (this,Constants.START_X,Constants.START_Y,Constants.WIN_WID,Constants.WIN_HEI);
        this.add(whatsAppScene);
        this.setVisible(true);

    }
}
