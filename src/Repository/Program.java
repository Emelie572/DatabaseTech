
package Repository;
import Model.*;

import java.util.List;
import java.util.Scanner;

public class Program {


private final Repository repository = new Repository();
private final Scanner scanner = new Scanner(System.in);
private Customer customer = new Customer();


public void run() {
    userInputLogin();
}

public void userInputLogin() {

    while (true) {
        System.out.println("----- Skriv in användarnamn -----");
        String userName = scanner.nextLine();

        System.out.println("----- Skriv in lösenord -----");
        String userPassword = scanner.nextLine();

        customer = repository.getLoginData(userName, userPassword);

        if (customer != null) {
            System.out.println(customer);
            getProductType();
            break;
        } else {
            System.out.println("Fel användarnamn eller lösenord");
        }
    }
}

public void getProductType() {
    ProductType productType = repository.getProductTypeData();
    System.out.println("Produkttyper: " + productType);
    chooseProductType();
}


public void chooseProductType() {

    while (true) {
        System.out.println("---- Välj produkttyp ----");
        String productType = scanner.nextLine();

        List<ProductCategory> productCategories = repository.getCategoryData(productType);
        if (productCategories == null || productCategories.isEmpty()) {
            System.out.println("Inga sådan skotyp hittades, försök igen");
        } else {
            productCategories.forEach(productCategory -> System.out.println(productCategory.getProductCategoryName()));
            chooseCategory();
            break;
        }
    }
}

public void chooseCategory() {

    while (true) {
        System.out.println("---- Välj en kategori -----");
        String inputCategory = scanner.nextLine();

        if (inputCategory == null || inputCategory.isEmpty()) {
            System.out.println("Inga sådan kategori hittades, försök igen");
        } else {
            List<Product> products = repository.getProductByCategoryData(inputCategory);
            products.forEach(product -> System.out.println(product.getProductName()));
            chooseProductByCategory();
            break;

        }
    }

}

public void chooseProductByCategory() {

    while (true) {
        System.out.println("----- Välj önskad produkt -----");
        String inputProductByCategory = scanner.nextLine();

        if (inputProductByCategory == null || inputProductByCategory.isEmpty()) {
            System.out.println("Inga sådan produkt hittades, försök igen");
        } else {
            List<ProductOption> productOptions = repository.getProductOptionData(inputProductByCategory);
            if (productOptions.isEmpty()) {
                System.out.println("Inga alternativ hittades för denna produkt.");
            } else {
                productOptions.forEach(productOption -> System.out.println("Färg: " + productOption.getColor() + " Storlek: " + productOption.getSize()));
                chooseProductOption(inputProductByCategory, productOptions);
                break;

            }
        }
    }
}


public void chooseProductOption(String selectedProduct, List<ProductOption> productOptions) {
    while (true) {
        System.out.println("----- Välj önskad färg -----");
        String inputColor = scanner.nextLine();

        System.out.println("----- Välj önskad storlek -----");
        String inputSize = scanner.nextLine();

        try {
            int inputSized = Integer.parseInt(inputSize);

            ProductOption selectedProductOption = null;
            for (ProductOption productOption : productOptions) {
                if (productOption.getColor().equalsIgnoreCase(inputColor) && productOption.getSize() == inputSized) {
                    selectedProductOption = productOption;
                    break;
                }
            }

            if (selectedProductOption != null) {
                System.out.println("Vald produkt: " + selectedProduct + " Färg: " + inputColor + " Storlek: " + inputSized);

                Integer orderId = getOrderId(customer.getCustomerId());
                addToCart(customer.getCustomerId(), orderId, selectedProductOption.getProductOptionId());

                break;
            } else {
                System.out.println("Ogiltigt val, vänligen välj en giltig färg och storlek.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ogiltig storlek, vänligen ange ett nummer.");
        }
    }
}

public void addToCart(int customerId, Integer orderId, int productOptionId) {
    repository.addToCart(customerId, orderId, productOptionId);
    System.out.println("Produkten är tillagd i varukorgen.");

    while (true) {
        System.out.println("Vill du lägga till fler produkter i varukorgen? (ja/nej)");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("ja")) {
            chooseCategory();
            break;
        } else if (input.equals("nej")) {
                System.out.println("Tack för ditt köp!");
            break;
            }
        }
    }


public Integer getOrderId(int customerId) {
    List<CustomerOrder> orders = repository.getCustomerOrderData();
    for (CustomerOrder order : orders) {
        if (order.getCustomerId() == customerId && "AKTIV".equals(order.getOrderStatus())) {
            return order.getOrderId();
        }
    }
    return null;
}



public static void main(String[] args) {

    Program program = new Program();
    program.run();
}

     }