import javax.swing.*;
import java.awt.*;

public class Helper {
    public static final Color WHITE = Color.white;
    private static final String KIND_OF_FONT = "Ariel";
    private static final int FONT_SIZE_BUTTON = 18, SIZE_FONT_LABEL = 20;


    public static JButton addButton(JPanel panel,String buttonText, int x, int y, int width, int height) {
        JButton button = new JButton(buttonText);
        Font font = new Font(KIND_OF_FONT, Font.BOLD, FONT_SIZE_BUTTON);
        button.setBounds(x, y, width, height);
        button.setVisible(true);
        button.setFont(font);
        panel.add(button);
        return button;
    }

    public static JLabel addLabel(JPanel panel,String labelText, int x, int y, int width, int height) {
        JLabel label = new JLabel(labelText);
        label.setForeground(WHITE);
        Font font = new Font(KIND_OF_FONT, Font.BOLD, SIZE_FONT_LABEL);
        label.setFont(font);
        label.setBounds(x, y, width, height);
        panel.add(label);
        return label;
    }

    public static JTextField addTextField(JPanel panel,int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        Font font = new Font(KIND_OF_FONT, Font.BOLD, SIZE_FONT_LABEL);
        textField.setFont(font);
        panel.add(textField);
        return textField;
    }



}
