public class Token {
    TokenTypes tokenType;
    String tokenValue;
    int lineNumber;
    public Token(TokenTypes tokenType, String tokenValue, int lineNumber) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
        this.lineNumber = lineNumber;
    }
    public String toString() {
        return "Token type : " + tokenType + "  " + tokenValue + " " + lineNumber ;
    }

}
