package Controller;

import Repository.CustomerRepository;
import Model.Customer;
import Enum.LoginMessage;
import View.CustomerView;

public class CustomerController {
    private static final int MAX_ATTEMPTS = 3;
    private final CustomerRepository customerRepository;
    private final CustomerView customerView;
    private Customer loggedInCustomer;

    public CustomerController(CustomerRepository customerRepository, CustomerView customerView) {
        this.customerRepository = customerRepository;
        this.customerView = customerView;
    }

    public void processLogin() {
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            String userName = customerView.getUserInput(LoginMessage.LOGIN_USERNAME);
            String userPassword = customerView.getUserInput(LoginMessage.LOGIN_PASSWORD);

            if (authenticateUser(userName, userPassword)) {
                return;
            }

            attempts++;
            if (attempts < MAX_ATTEMPTS) {
                customerView.displayMessage(LoginMessage.LOGIN_WRONG);
            }
        }

        customerView.displayMessage(LoginMessage.FAILED_ATTEMPTS);
    }

    private boolean authenticateUser(String userName, String userPassword) {
        if (userName == null || userPassword == null || userName.trim().isEmpty() || userPassword.trim().isEmpty()) {
            customerView.displayMessage(LoginMessage.LOGIN_WRONG);
            return false;
        }

        Customer customer = customerRepository.getLoginData(userName, userPassword);

        if (customer == null) {
            return false;
        }

        this.loggedInCustomer = customer;
        customerView.showCustomerInfo(customer.getFirstName() + " " + customer.getLastName());
        return true;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }
}
