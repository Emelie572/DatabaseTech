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

    public void handleInputLogin() {
        boolean loginSuccess = false;

        while (!loginSuccess) {
            String userName = customerView.getUserInput(LoginMessage.LOGIN_USERNAME);
            String userPassword = customerView.getUserInput(LoginMessage.LOGIN_PASSWORD);

            loginSuccess = attemptLogin(userName, userPassword);
        }
    }

    private boolean attemptLogin(String userName, String userPassword) {
        Customer customer = customerRepository.getLoginData(userName, userPassword);

        if (customer != null) {
            customerView.showCustomerInfo(customer.getFirstName() + " " + customer.getLastName());
            return true;
        } else {
            customerView.displayMessage(LoginMessage.LOGIN_WRONG);
            return false;
        }
    }
}

