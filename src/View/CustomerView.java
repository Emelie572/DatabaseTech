package View;

import Interface.MessageDisplay;
import Enum.LoginMessage;
import java.util.Scanner;
import Enum.ProductMessage;

public class CustomerView implements MessageDisplay {
    private final Scanner scanner;

    public CustomerView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getUserInput(LoginMessage prompt) {
        displayMessage(prompt);
        return scanner.nextLine().trim();
    }

    public void displayMessage(LoginMessage message) {
        System.out.println(message.getMessage());
    }

    public void showCustomerInfo(String customerName) {
        displayMessage(LoginMessage.LOGIN_RIGHT);
        System.out.println(customerName);
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
