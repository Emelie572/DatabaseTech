package View;

import Interface.MessageDisplay;
import Enum.OrderMessage;
import Enum.ErrorMessage;


import java.util.Scanner;

public class OrderView implements MessageDisplay {
    private final Scanner scanner;

    public OrderView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getUserInput(OrderMessage prompt) {
        displayOrderMessage(prompt);
        return scanner.nextLine().trim();
    }

    public String askToContinueShoppingInput() {
        return getUserInput(OrderMessage.ORDER_CONTINUE_SHOPPING);
    }

    public String handlePaymentInput() {
        return getUserInput(OrderMessage.ORDER_PAY);
    }

    public void showErrorMessage(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getMessage());
    }

    public void showOrderMessage(OrderMessage orderMessage) {
        System.out.println(orderMessage.getMessage());
    }

    public void displayOrderMessage(OrderMessage message) {
        System.out.println(message.getMessage());
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
