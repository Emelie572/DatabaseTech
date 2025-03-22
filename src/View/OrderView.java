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
        displayMessage(prompt);
        return scanner.nextLine().trim();
    }

    public String askToContinueShoppingInput() {
        return getUserInput(OrderMessage.ORDER_CONTINUE_SHOPPING);
    }

    public String handlePaymentInput() {
        return getUserInput(OrderMessage.ORDER_PAY);
    }

    public void displayMessage(OrderMessage message) {
        System.out.println(message.getMessage());
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
