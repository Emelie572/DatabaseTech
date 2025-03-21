package Controller;

import Repository.CustomerRepository;
import Model.Customer;
import Enum.LoginMessage;
import View.CustomerView;


public class CustomerController {
    private final CustomerRepository customerRepository;
    private final CustomerView customerView;

    public CustomerController(CustomerRepository customerRepository, CustomerView customerView) {
        this.customerRepository = customerRepository;
        this.customerView = customerView;
    }

    public void processLogin() {
        final int MAX_ATTEMPTS = 3;
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            String userName = customerView.getUserInput(LoginMessage.LOGIN_USERNAME);
            String userPassword = customerView.getUserInput(LoginMessage.LOGIN_PASSWORD);

            if (authenticateUser(userName, userPassword)) {
                return;
            }
            attempts++;
            customerView.displayMessage(LoginMessage.LOGIN_WRONG);
        }
        customerView.displayMessage(LoginMessage.FAILED_ATTEMPTS);
    }


    private boolean authenticateUser(String userName, String userPassword) {
        Customer customer = customerRepository.getLoginData(userName, userPassword);

        if (customer == null) {
            return false;
        }

        customerView.showCustomerInfo(customer.getFirstName() + " " + customer.getLastName());
        return true;
    }
}


