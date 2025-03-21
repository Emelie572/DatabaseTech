package Enum;

public enum LoginMessage {

    LOGIN_USERNAME("----- Skriv in användarnamn -----"),
    LOGIN_PASSWORD("----- Skriv in lösenord -----"),
    LOGIN_RIGHT("Inloggningen lyckades! Välkommen!"),
    LOGIN_WRONG("Fel användarnamn eller lösenord, försök igen."),
    FAILED_ATTEMPTS("För många misslyckade försök. Försök igen senare");

    private final String message;

    LoginMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
