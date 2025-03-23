package Controller;

import Repository.CustomerRepository;
import Model.Customer;
import Enum.LoginMessage;
import View.CustomerView;
import Enum.ErrorMessage;


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
            String userName = handleUserInput(LoginMessage.LOGIN_USERNAME);
            String userPassword = handleUserInput(LoginMessage.LOGIN_PASSWORD);

            if (authenticateUser(userName, userPassword)) {
                return;
            }

            attempts++;
            if (attempts < MAX_ATTEMPTS) {
                handleLoginMessage(LoginMessage.LOGIN_WRONG);
            }
        }

        handleLoginMessage(LoginMessage.FAILED_ATTEMPTS);
    }

    private boolean authenticateUser(String userName, String userPassword) {
        if (userName == null || userPassword == null || userName.trim().isEmpty() || userPassword.trim().isEmpty()) {
            handleLoginMessage(LoginMessage.LOGIN_WRONG);
            return false;
        }

        Customer customer = customerRepository.getLoginData(userName, userPassword);
        if (customer == null) {
            return false;
        }

        this.loggedInCustomer = customer;
        handleCustomerInfo();
        return true;
    }

    private void handleErrorMessage(ErrorMessage message) {
        customerView.showErrorMessage(message);
    }

    public void handleCustomerInfo() {
        customerView.showCustomerInfo();
    }

    public String handleUserInput(LoginMessage message) {
        return customerView.getUserInput(message);
    }

    private void handleLoginMessage(LoginMessage message) {
        customerView.displayMessage(message);
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }
}
