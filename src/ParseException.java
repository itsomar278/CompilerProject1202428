public class ParseException extends Exception {
    public ParseException(String message) {
        super("\u001B[31m" + message + "\u001B[0m"); // Adding ANSI escape codes for red text
    }
}
