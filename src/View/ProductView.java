package View;

import Interface.MessageDisplay;
import Model.ProductCategory;
import Model.ProductOption;
import Model.ProductType;
import Model.Product;
import java.util.List;
import java.util.Scanner;
import Enum.ProductMessage;


public class ProductView implements MessageDisplay {
    private final Scanner scanner;

    public ProductView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getUserInput(ProductMessage prompt) {
        System.out.println(prompt.getMessage());
        return scanner.nextLine().trim();
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void showProductTypes(ProductType productTypes) {
        System.out.println(productTypes.getProductTypeName());
    }

    public void showProductCategories(List<ProductCategory> productCategories) {
        productCategories.forEach(category -> System.out.println("- " + category.getProductCategoryName()));
    }

    public void showProducts(List<Product> products) {
        products.forEach(product -> System.out.println("- " + product.getProductName()));
    }

    public void showProductOptions(List<ProductOption> productOptions) {
        productOptions.forEach(option ->
                System.out.println("- Färg: " + option.getColor() + ", Storlek: " + option.getSize()));
    }
}

