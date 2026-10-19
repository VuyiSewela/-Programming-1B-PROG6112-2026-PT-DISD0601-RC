package quickchat;



import java.util.Scanner;

/** Console-only entry point: registration, authentication, menus and input loops. */
public class App {
    private static final Scanner INPUT=new Scanner(System.in);
    private static String read(String prompt){System.out.print(prompt);if(!INPUT.hasNextLine()) throw new EndOfInput();return INPUT.nextLine();}
    private static int number(String prompt,int min,int max){
        while(true) try {
            int value=Integer.parseInt(read(prompt));
            if(value>=min && value<=max) return value;
            System.out.println("Enter a number from "+min+" to "+max+".");
        } catch(NumberFormatException ex){System.out.println("Please enter a whole number.");}
    }
    private static class EndOfInput extends RuntimeException { private static final long serialVersionUID=1L; }
    public static void main(String[] args){
        try {run();} catch(EndOfInput ex){System.out.println("\nInput closed. Goodbye.");}
    }
    private static void run() {
        System.out.println("QuickChat registration");
        String first=read("First name: "), last=read("Last name: ");
        Login login;
        do {
            login=new Login(first,last,read("Username: "),read("Password: "),read("Cell phone (+27...): "));
            System.out.println(login.usernameStatus()); System.out.println(login.passwordStatus());
            System.out.println(login.phoneStatus()); System.out.println(login.registerUser());
        } while(!login.checkUserName() || !login.checkPasswordComplexity() || !login.checkCellPhoneNumber());
        do {
            login.enterCredentials(read("Login username: "),read("Login password: "));
            System.out.println(login.returnLoginStatus());
        } while(!login.loginUser());
    }
}
