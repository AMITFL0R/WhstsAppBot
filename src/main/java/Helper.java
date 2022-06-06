import javax.swing.*;
import java.awt.*;

import static java.awt.Color.WHITE;

public class Helper {



    public static JButton addButton(JPanel panel,String buttonText, int x, int y, int width, int height) {
        JButton button = new JButton(buttonText);
        Font font = new Font(Constants.FONT_TYPE, Font.BOLD, Constants.FONT_SIZE_BUTTON);
        button.setBounds(x, y, width, height);
        button.setVisible(true);
        button.setFont(font);
        panel.add(button);
        return button;
    }

    public static JLabel addLabel(JPanel panel,String labelText, int x, int y, int width, int height) {
        JLabel label = new JLabel(labelText);
        label.setForeground(WHITE);
        Font font = new Font(Constants.FONT_TYPE, Font.BOLD, Constants.SIZE_FONT_LABEL);
        label.setFont(font);
        label.setBounds(x, y, width, height);
        panel.add(label);
        return label;
    }

    public static JTextField addTextField(JPanel panel,int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        Font font = new Font(Constants.FONT_TYPE, Font.BOLD, Constants.SIZE_FONT_LABEL);
        textField.setFont(font);
        panel.add(textField);
        return textField;
    }

}
