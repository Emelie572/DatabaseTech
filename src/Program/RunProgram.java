package Program;

import Controller.*;
import Model.ProductType;
import Repository.CustomerRepository;
import Repository.OrderRepository;
import Repository.ProductRepository;
import View.*;

import java.util.Scanner;

import Controller.OrderController;
import Controller.ProductController;
import Model.ProductType;
import Repository.ProductRepository;
import View.OrderView;
import View.ProductView;
import java.util.Scanner;

public class RunProgram {
    public static void main(String[] args) {

        // Skapa en Scanner för användarinput
        Scanner scanner = new Scanner(System.in);

        ProductRepository productRepository = new ProductRepository();
        ProductView productView = new ProductView(scanner);
        OrderView orderView = new OrderView(scanner);
        CustomerView customerView = new CustomerView(scanner);

        OrderRepository orderRepository = new OrderRepository();
        CustomerRepository customerRepository = new CustomerRepository();


        CustomerController customerController = new CustomerController(customerRepository,customerView);

        ProductController productController = new ProductController(productRepository, productView);
        OrderController orderController = new OrderController(productController, orderRepository,orderView,customerController);


        customerController.processLogin();
        productController.chooseProductType();
        orderController.prepareOrder();

    }
}

