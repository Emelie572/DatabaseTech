package Enum;

public enum OrderStatus {
    ACTIVE_ORDER("AKTIV"),
    PAYED_ORDER("BETALD");

    private final String message;

    OrderStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}