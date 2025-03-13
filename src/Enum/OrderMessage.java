package Enum;

public enum OrderMessage {

    ORDER_ADD("Vill du lägga till produkten i varukorgen?"),
    ORDER_DELETE("Vill du ta bort produkten från varukorgen?"),
    ORDER_ADD_UPDATE("Produkten är tillagd!"),
    ORDER_DELETE_UPDATE("Produkten är borttagen!"),
    ORDER_CONTINUE_SHOPPING("Vill du fortsätta att shoppa eller betala? (shoppa) ange ja (betala) ange betala"),
    ORDER_PAY_UPDATE("Ordern är betald"),
    ORDER_PAY("Vill du betala?"),
    ORDER_PAYMENT_DENIED("Betalningen gick ej igenom, ordern avbryts"),
    ORDER_READY_TO_PAY("Betala"),
    ANSWER_YES("Ja"),
    ANSWER_NO("Nej");


    private final String message;

    OrderMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

