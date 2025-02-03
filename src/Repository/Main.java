package Repository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        Repository repository = new Repository();


       Scanner scanner = new Scanner(System.in);
       String input = scanner.nextLine();
       List<ProductCategory> productCategories = repository.getCategories(input);
       productCategories.forEach(productCategory -> System.out.println(productCategory.getProductCategoryName()));

        System.out.println("----- Välj en kategori -----");
        String inputCategory = scanner.nextLine();

        List<Product> products = repository.getProductByCategory(inputCategory);
        products.forEach(product -> System.out.println(product.getProductName() + " Pris: " + product.getProductPrice()));



    }
}
