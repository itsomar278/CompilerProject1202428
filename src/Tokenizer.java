import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Tokenizer {
    private ArrayList<Token> tokens;
    private String codePath;
    private HashSet<String> reservedWords;
    int lineNumber = 0;

    public Tokenizer(String codePath)
    {
        tokens = new ArrayList<Token>();
        this.codePath = codePath;
    }

    public ArrayList<Token> Tokenize()
    {
        reservedWords = new HashSet<String>();
        for (ReservedWords word : ReservedWords.values()) {
            reservedWords.add(word.toString().toLowerCase());
        }

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(codePath)));
            String[] lines = sourceCode.split("\n");
             lineNumber = 1;

            for (String line : lines) {
                tokenizeLine(line, lineNumber);
                lineNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Tokens : " + tokens.size());
       /* for (Token token : tokens) {
            System.out.println(token.toString());

        }

        */

        return tokens;
    }

    private void tokenizeLine(String line, int lineNumber) {
        line = line.replaceAll("\\s+", " ");
        String[] tokensInLine = line.split("(?<=\\W)|(?=\\W)");
        for (String tokenValue : tokensInLine) {
            try {
                determineTokenType(tokenValue);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    private void determineTokenType(String tokenValue) {
        if (tokenValue.equals("")) {
            return;
        }

        String identifierPattern = "[a-zA-Z_][a-zA-Z0-9_]*";
        String operatorPattern = ":=|\\+|\\-|\\*|\\/|mod|div|=|<=|<|>=|>|\\|=";
        String specialCharPattern = "\\;|\\,|\\(|\\)|\\[|\\]|:|\\.";
        String realPattern = "[0-9]*\\.[0-9]";

        if (tokenValue.matches("\\d+\\.\\d+")) {
            tokens.add(new Token(TokenTypes.Realliteral, tokenValue, lineNumber));
        } else if (tokenValue.matches("\\d+")) {
            tokens.add(new Token(TokenTypes.Integerliteral, tokenValue, lineNumber));
        } else if (reservedWords.contains(tokenValue)) {
            tokens.add(new Token(TokenTypes.KeyWord, tokenValue, lineNumber));
        } else if (tokenValue.matches(operatorPattern)) {
            tokens.add(new Token(TokenTypes.Operator, tokenValue, lineNumber));
        } else if (tokenValue.matches(specialCharPattern)) {
            tokens.add(new Token(TokenTypes.SpecialCharacter, tokenValue, lineNumber));
        }

        else if (Character.isLetter(tokenValue.charAt(0))) {
            if (tokenValue.equals("mod") || tokenValue.equals("div")) {
                tokens.add(new Token(TokenTypes.Operator, tokenValue, lineNumber));
            } else {
                char[] charArray = tokenValue.toCharArray();
                int indexofspecial = checkspecialchars(charArray);
                if (indexofspecial == -1) {
                    tokens.add(new Token(TokenTypes.Identifier, tokenValue, lineNumber));
                } else {
                    while (indexofspecial != -1) {
                        char[] part1 = Arrays.copyOfRange(charArray, 0, indexofspecial);
                        char[] part2 = Arrays.copyOfRange(charArray, indexofspecial, charArray.length);

                        if (part1.length > 0) {
                            determineTokenType(String.valueOf(part1));
                        }

                        if (part2.length > 0) {
                            determineTokenType(String.valueOf(part2));
                        }

                        break;
                            }
                        }
                    }
                }
            }



    public int checkspecialchars(char[] chars)
    {
        for (int i = 0 ; i<chars.length ; i++)
        {
            if(Character.isLetterOrDigit(chars[i] )|| chars[i] == '.')
            {
                continue;
            }
            else
            {
                return i;

            }
        }
        return -1;
    }
    {

    }




}