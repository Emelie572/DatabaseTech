package Controller;

import Model.*;
import Repository.OrderRepository;
import View.OrderView;
import Enum.ErrorMessage;
import Enum.OrderMessage;


public class OrderController {

    private final ProductController productController;
    private final OrderRepository orderRepository;
    private final OrderView orderView;
    private final CustomerController customerController;

    public OrderController(ProductController productController, OrderRepository orderRepository, OrderView orderView, CustomerController customerController) {
        this.productController = productController;
        this.orderRepository = orderRepository;
        this.orderView = orderView;
        this.customerController = customerController;
    }

    public Integer getOrderId(int customerId) {
        return orderRepository.getCustomerOrderData().stream()
                .filter(order -> order.getCustomerId() == customerId && "AKTIV".equals(order.getOrderStatus()))
                .map(CustomerOrder::getOrderId)
                .findFirst()
                .orElse(null);
    }

    public void addToCart(int customerId, Integer orderId, int productOptionId) {
        orderRepository.addToCart(customerId, orderId, productOptionId);
        handleOrderMessage(OrderMessage.ORDER_ADD_UPDATE);
    }


    public void prepareOrder() {
        final Customer customer = customerController.getLoggedInCustomer();

        if (customer == null) {
            handleErrorMessage(ErrorMessage.NOT_LOGGED_IN);
            return;
        }

        boolean continueShopping;
        do {
            final ProductOption selectedProductOption = productController.finalProduct();
            final Integer orderId = getOrderId(customer.getCustomerId());

            addToCart(customer.getCustomerId(), orderId, selectedProductOption.getProductOptionId());

            continueShopping = askToContinueShopping();
        } while (continueShopping);

        handlePayment();
    }

    private void handleErrorMessage(ErrorMessage message) {
        orderView.showErrorMessage(message);
    }

    public String handleUserInput(OrderMessage message) {
        return orderView.getUserInput(message);
    }

    private boolean askToContinueShopping() {
        return orderView.askToContinueShoppingInput()
                .equalsIgnoreCase(OrderMessage.ANSWER_YES.getMessage());
    }

    public String handlePaymentInput() {
        return orderView.getUserInput(OrderMessage.ORDER_PAY);
    }

    private void handleOrderMessage(OrderMessage message) {
        orderView.showOrderMessage(message);
    }

    public void handlePayment() {
        while (true) {
            String inputPay = handlePaymentInput();

            if (inputPay.equalsIgnoreCase(OrderMessage.ANSWER_YES.getMessage())) {
                handleOrderMessage(OrderMessage.ORDER_PAY_UPDATE);
                return;
            } else if (inputPay.equalsIgnoreCase(OrderMessage.ANSWER_NO.getMessage())) {
                handleOrderMessage(OrderMessage.ORDER_PAYMENT_DENIED);
                return;
            } else {
                handleErrorMessage(ErrorMessage.INVALID_INPUT);
            }
        }
    }

}