package View;

import Interface.MessageDisplay;
import Enum.LoginMessage;
import java.util.Scanner;
import Enum.ErrorMessage;


public class CustomerView implements MessageDisplay {
    private final Scanner scanner;

    public CustomerView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getUserInput(LoginMessage prompt) {
        showLoginMessage(prompt);
        return scanner.nextLine().trim();
    }

    public void displayMessage(LoginMessage message) {
        System.out.println(message.getMessage());
    }

    public void showCustomerInfo() {
        displayMessage(LoginMessage.LOGIN_RIGHT);
    }

    public void showErrorMessage(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getMessage());
    }

    public void showLoginMessage(LoginMessage message) {
        System.out.println(message.getMessage());
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
