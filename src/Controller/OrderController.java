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
        orderView.displayMessage(OrderMessage.ORDER_ADD_UPDATE);
    }

    public void prepareOrder() {
        final Customer customer = customerController.getLoggedInCustomer();

        if (customer == null) {
            orderView.displayMessage(ErrorMessage.NOT_LOGGED_IN.getMessage());
            return;
        }

        final ProductOption selectedProductOption = productController.finalProduct();
        final Integer orderId = getOrderId(customer.getCustomerId());

        addToCart(customer.getCustomerId(), orderId, selectedProductOption.getProductOptionId());

        if (!askToContinueShopping()) {
            handlePayment();
        }
    }

    public boolean askToContinueShopping() {
        String input = orderView.askToContinueShoppingInput();

        if (input.equalsIgnoreCase(OrderMessage.ANSWER_YES.getMessage())) {
            productController.chooseProductType();
            return true;
        } else if (input.equalsIgnoreCase(OrderMessage.ANSWER_NO.getMessage())) {
            handlePayment();
            return false;
        } else {
            orderView.displayMessage(ErrorMessage.INVALID_INPUT.getMessage());
            return askToContinueShopping();
        }
    }

    public void handlePayment() {
        while (true) {
            String inputPay = orderView.handlePaymentInput();

            if (inputPay.equalsIgnoreCase(OrderMessage.ANSWER_YES.getMessage())) {
                orderView.displayMessage(OrderMessage.ORDER_PAY_UPDATE.getMessage());
                return;
            } else if (inputPay.equalsIgnoreCase(OrderMessage.ANSWER_NO.getMessage())) {
                orderView.displayMessage(OrderMessage.ORDER_PAYMENT_DENIED.getMessage());
                return;
            } else {
                orderView.displayMessage(ErrorMessage.INVALID_INPUT.getMessage());
            }
        }
    }
}