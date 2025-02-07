package Model;

public enum OrderStatus {
    AKTIV("AKTIV"),
    BETALD("BETALD");

    private final String statusText;

    OrderStatus(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusText() {
        return statusText;
    }
}
