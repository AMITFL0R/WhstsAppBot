import javax.swing.*;

public class ValidDetails {


    public boolean validText(String s) {
        return s.equals("");

    }

    public boolean validPhoneNumber(String phoneNumber) {
        boolean valid = true;
        if (phoneNumber.length() != 10) {
            valid = false;
        } else if (phoneNumber.charAt(0) != '0' || phoneNumber.charAt(1) != '5') {
            valid = false;
        } else {
            for (int i = 0; i < phoneNumber.length(); i++) {
                if (!Character.isDigit(phoneNumber.charAt(i))) {
                    valid = false;
                }
            }
        }
        return valid;
    }

}
