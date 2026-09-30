package util;


public class Validation {


    // Name must contain alphabets only
    public static boolean validNameWord(String name) {

        return name != null
                && name.matches("[A-Za-z]+");
    }


    // Nepal phone number
    // Exactly 10 digits and starts with 9
    public static boolean validPhone(String phone) {

        return phone != null
                && phone.matches("9[0-9]{9}");
    }


    // Email validation
    public static boolean validEmail(String email) {

        return email != null
                && email.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                );
    }


    // Password validation
    public static boolean validPassword(
            String password) {

        if (password == null) {
            return false;
        }

        if (password.length() < 8
                || password.length() > 72) {

            return false;
        }


        boolean uppercase =
                password.matches(".*[A-Z].*");


        boolean lowercase =
                password.matches(".*[a-z].*");


        boolean number =
                password.matches(".*[0-9].*");


        boolean special =
                password.matches(
                        ".*[^A-Za-z0-9].*"
                );


        return uppercase
                && lowercase
                && number
                && special;
    }
}
