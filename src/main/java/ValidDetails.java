

public class ValidDetails {


    public boolean validText(String s) {
        return s.equals("");

    }
    public boolean validPhoneNumber(String phoneNumber) {
        boolean valid = true;
        if (phoneNumber.length() != Constants.PHONE_NUMBER_LENGTH) {
            valid = false;
        } else if (phoneNumber.charAt(Constants.FIRST_CHAR_INDEX) != Constants.FIRST_CHAR || phoneNumber.charAt(Constants.SECOND_CHAR_INDEX) != Constants.SECOND_CHAR) {
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
