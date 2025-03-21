package Program;

import Controller.CustomerController;
import Controller.OrderController;
import Controller.ProductController;
import Model.*;
import Repository.CustomerRepository;
import Repository.OrderRepository;
import Repository.ProductRepository;
import View.CustomerView;
import View.OrderView;
import View.ProductView;
import java.util.Scanner;

public class RunProgram {

    public void startApp() {
        Scanner scanner = new Scanner(System.in);

        CustomerRepository customerRepository = new CustomerRepository();
        OrderRepository orderRepository = new OrderRepository();
        ProductRepository productRepository = new ProductRepository();

        CustomerView customerView = new CustomerView(scanner);
        OrderView orderView = new OrderView(scanner);
        ProductView productView = new ProductView(scanner);

        ProductController productController = new ProductController(productRepository, productView);
        OrderController orderController = new OrderController(productController, orderRepository, orderView);
        CustomerController customerController = new CustomerController(customerRepository, customerView);

        customerController.processLogin();
        productController.productFlow();
        orderController.orderFlow();
    }

    public static void main(String[] args) {
        RunProgram runProgram = new RunProgram();
        runProgram.startApp();
    }
}
