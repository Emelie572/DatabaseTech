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
            while (true) {
                final String userName = customerView.getUserInput(LoginMessage.LOGIN_USERNAME);
                final String userPassword = customerView.getUserInput(LoginMessage.LOGIN_PASSWORD);

                Customer customer = customerRepository.getLoginData(userName, userPassword);

                if (customer != null) {
                    customerView.showCustomerInfo(customer.getFirstName() + " " + customer.getLastName());
                    break;
                } else {
                    customerView.displayMessage(LoginMessage.LOGIN_WRONG);
                }
            }
        }
    }

