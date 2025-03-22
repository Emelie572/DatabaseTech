package Enum;

public enum ProductMessage {
    CHOOSE_PRODUCT_TYPE("Välj produkttyp: "),
    CHOOSE_PRODUCT_CATEGORY("Välj kategori: "),
    CHOOSE_PRODUCT("Välj produkt: "),
    CHOOSE_COLOR("Välj färg: "),
    CHOOSE_SIZE("Välj storlek: "),
    CHOOSE_PRODUCT_OPTION("Välj färg och storlek"),
    CHOSEN_PRODUCT("Vald produkt: ");

    private final String message;

    ProductMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
