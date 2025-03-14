package View;

import Interface.MessageDisplay;
import Enum.OrderMessage;

import java.util.Scanner;

public class OrderView implements MessageDisplay {
    private final Scanner scanner;

    public OrderView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getUserInput(OrderMessage prompt) {
        System.out.println(prompt.getMessage());
        return scanner.nextLine().trim();
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }


}
