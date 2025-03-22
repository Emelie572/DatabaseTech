package View;

import Interface.MessageDisplay;
import Interface.Name;
import Model.ProductType;
import java.util.List;
import java.util.Scanner;
import Enum.ProductMessage;
import Enum.ErrorMessage;


public class ProductView implements MessageDisplay {
    private final Scanner scanner;

    public ProductView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getUserInput(ProductMessage prompt) {
        displayProductMessage(prompt);
        return scanner.nextLine().trim();
    }

    public String getColorInput() {
        return getUserInput(ProductMessage.CHOOSE_COLOR);
    }

    public String getSizeInput() {
        return getUserInput(ProductMessage.CHOOSE_SIZE);
    }

    public void showErrorMessage(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getMessage());
    }


    public void displayProductMessage(ProductMessage message) {
        System.out.println(message.getMessage());
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void showProductTypes(ProductType productTypes) {
        System.out.println(productTypes.getProductTypeName());
    }

    public void showItems(List<? extends Name> items) {
        items.forEach(item -> System.out.println("- " + item.toString()));
    }
}

