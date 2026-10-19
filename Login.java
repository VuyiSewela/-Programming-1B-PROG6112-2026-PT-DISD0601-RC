package quickchat;

/** Registration state and independently testable credential validation. */
public class Login {
    public static final String USER_OK = "Username successfully captured.";
    public static final String USER_BAD = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
    public static final String PASS_OK = "Password successfully captured.";
    public static final String PASS_BAD = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
    public static final String PHONE_OK = "Cell phone number successfully added.";
    public static final String PHONE_BAD = "Cell phone number incorrectly formatted or does not contain international code.";
    private final String firstName, lastName, username, password, phone;
    private String enteredUsername, enteredPassword;
    private boolean registered;

    public Login(String firstName, String lastName, String username, String password, String phone) {
        this.firstName = firstName; this.lastName = lastName;
        this.username = username; this.password = password; this.phone = phone;
    }
    public boolean checkUserName() {
        return username != null && username.length() <= 5 && username.contains("_");
    }
    public boolean checkPasswordComplexity() {
        return password != null && password.length() >= 8
            && password.matches("(?s).*[A-Z].*") && password.matches("(?s).*[0-9].*")
            && password.matches("(?s).*[^a-zA-Z0-9\\s].*");
    }
    /** Original pattern based on Oracle's regex syntax, not a copied phone validator.
     * Oracle (2026), Pattern, https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html
     * Accessed 19 October 2026. +27 followed by nine national digits; no local leading zero.
     */
    public static boolean validPhone(String value) {
        return value != null && value.matches("\\+27[1-9][0-9]{8}");
    }
    public boolean checkCellPhoneNumber() { return validPhone(phone); }
    public String usernameStatus() { return checkUserName() ? USER_OK : USER_BAD; }
    public String passwordStatus() { return checkPasswordComplexity() ? PASS_OK : PASS_BAD; }
    public String phoneStatus() { return checkCellPhoneNumber() ? PHONE_OK : PHONE_BAD; }
    public String registerUser() {
        if (!checkUserName()) return USER_BAD;
        if (!checkPasswordComplexity()) return PASS_BAD;
        if (!checkCellPhoneNumber()) return PHONE_BAD;
        registered = true;
        return "User registered successfully.";
    }
    public void enterCredentials(String user, String pass) { enteredUsername = user; enteredPassword = pass; }
    public boolean loginUser() {
        return registered && username.equals(enteredUsername) && password.equals(enteredPassword);
    }
    public String returnLoginStatus() {
        return loginUser() ? "Welcome " + firstName + ", " + lastName + " it is great to see you again."
            : "Username or password incorrect, please try again.";
    }
    public String phone() { return phone; }
}
