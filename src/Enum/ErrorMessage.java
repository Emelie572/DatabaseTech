package Enum;

public enum ErrorMessage {
    PRODUCT_TYPE_NOT_FOUND("Produkttypen finns ej, försök igen"),
    PRODUCT_NOT_FOUND("Produkten finns ej, försök igen"),
    CATEGORY_NOT_FOUND("Kategorin finns ej, försök igen"),
    PRODUCT_OPTION_NOT_FOUND("Produktvalet finns ej"),
    COLOR_NOT_FOUND("Färgen finns ej, försök igen"),
    TRY_AGAIN("Försök igen"),
    ADD_PRODUCT_FAILED("Det gick inte att lägga till varan i varukorgen.Försök igen"),
    SIZE_NOT_FOUND("Ogiltig storlek, vänligen ange ett nummer"),
    NOT_LOGGED_IN("Du måste vara inloggad för att skapa en beställning."),
    INVALID_INPUT("Ogiltig inmatning, försök igen");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}