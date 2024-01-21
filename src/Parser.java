import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;
    private int currentIndex = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        try {
            checkModuleDeclaration(tokens.get(currentIndex));
            System.out.println("Parsing is done");
        } catch (ParseException e) {
            System.out.println("Parsing error: " + e.getMessage());
        }
    }

    private void checkModuleDeclaration(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.KeyWord) && token.tokenValue.equals("module")) {
            currentIndex++;
            checkModuleHeading(tokens.get(currentIndex));
            checkDeclarations(tokens.get(currentIndex));
            checkProcedureDeclarations(tokens.get(currentIndex));
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid module declaration");
        }
    }
public void checkProcedureDeclarations(Token token) throws ParseException {
    if (token.tokenType.equals(TokenTypes.KeyWord) && token.tokenValue.equals("procedure")) {
        currentIndex++;
        checkProcedureHeading(tokens.get(currentIndex));
        checkDeclarations(tokens.get(currentIndex));
        checkBlock(tokens.get(currentIndex));
        checkSemiColon(tokens.get(currentIndex));
    } else {
        throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid procedure declaration");
    }
}
    public void checkProcedureHeading(Token token) throws ParseException {
        checkName(tokens.get(currentIndex));
        checkSemiColon(tokens.get(currentIndex));
    }
    public void checkBlock(Token token) throws ParseException {
        checkBegin(tokens.get(currentIndex));
        checkStatements(tokens.get(currentIndex));
        checkEnd(tokens.get(currentIndex));
        checkName(tokens.get(currentIndex));
    }
    public void checkStatements(Token token) throws ParseException
    {


        if((token.tokenType== TokenTypes.KeyWord) &&  (token.tokenValue.equals("readint") || token.tokenValue.equals("readreal")||
                token.tokenValue.equals("readreal")||
                token.tokenValue.equals("readchar") || token.tokenValue.equals("readln") ) )
        {
            checkReadStatement(tokens.get(currentIndex));

        }
        if (token.tokenType== TokenTypes.KeyWord && (token.tokenValue.equals("writeint") || token.tokenValue.equals("writereal")||
                token.tokenValue.equals("writechar") || token.tokenValue.equals("writeln") ) )
        {
            checkWriteStatement(tokens.get(currentIndex));
            checkStatements(tokens.get(currentIndex));
        }
        if(token.tokenType== TokenTypes.Identifier)
        {
            checkAssignStatment(tokens.get(currentIndex));
            checkStatements(tokens.get(currentIndex));

        }
        if (token.tokenType== TokenTypes.KeyWord && token.tokenValue.equals("if"))
        {
            currentIndex++;
            checkIfStatement(tokens.get(currentIndex));
            checkStatements(tokens.get(currentIndex));

        }
        if (token.tokenType== TokenTypes.KeyWord && token.tokenValue.equals("while"))
        {
            currentIndex++;
            checkWhileStatement(tokens.get(currentIndex));
            checkStatements(tokens.get(currentIndex));

        }
        if (token.tokenType== TokenTypes.KeyWord && token.tokenValue.equals("repeat"))
        {
            currentIndex++;
            checkRepeatStatment(tokens.get(currentIndex));
            checkStatements(tokens.get(currentIndex));

        }
        if (token.tokenType== TokenTypes.KeyWord && token.tokenValue.equals("loop"))
        {
            currentIndex++;
            checkLoopStatment(tokens.get(currentIndex));
            checkStatements(tokens.get(currentIndex));

        }
        if (token.tokenType== TokenTypes.KeyWord && token.tokenValue.equals("exit"))
        {
            currentIndex++;
            checkExitStatment(tokens.get(currentIndex));
            checkStatements(tokens.get(currentIndex));
        }
        if (token.tokenType== TokenTypes.KeyWord && token.tokenValue.equals("call"))
        {
            currentIndex++;
            checkCallStatment(tokens.get(currentIndex));
            checkStatements(tokens.get(currentIndex));

        }


    }
    public void checkCallStatment(Token token) throws ParseException {
        checkName(tokens.get(currentIndex));
        checkSemiColon(tokens.get(currentIndex));
    }
    public void checkExitStatment(Token token) throws ParseException {
        checkSemiColon(tokens.get(currentIndex));
    }
    public void checkLoopStatment(Token token) throws ParseException {
        checkStatements(tokens.get(currentIndex));
        checkEnd(tokens.get(currentIndex));
    }
    public void checkRepeatStatment(Token token) throws ParseException {
        checkStatements(tokens.get(currentIndex));
        checkUntil(tokens.get(currentIndex));
        checkCondition(tokens.get(currentIndex));
        checkSemiColon(tokens.get(currentIndex));
    }
    public void checkUntil(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.KeyWord) && token.tokenValue.equals("until")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid until");
        }
    }
    public void checkWhileStatement(Token token) throws ParseException {
        checkCondition(tokens.get(currentIndex));
        checkDo(tokens.get(currentIndex));
        checkStatements(tokens.get(currentIndex));
        checkEnd(tokens.get(currentIndex));
    }
    public void checkDo(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.KeyWord) && token.tokenValue.equals("do")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid do");
        }
    }
    public void checkIfStatement(Token token) throws ParseException {
        checkCondition(tokens.get(currentIndex));
        checkThen(tokens.get(currentIndex));
        checkStatements(tokens.get(currentIndex));
        checkElseIf(tokens.get(currentIndex));
        checkElseWithOptionalStatements(tokens.get(currentIndex));
        checkEnd(tokens.get(currentIndex));
    }

    public void checkElseWithOptionalStatements(Token token) throws ParseException {
        if(token.tokenType.equals(TokenTypes.KeyWord) && token.tokenValue.equals("else"))
        {
            currentIndex++;
            checkStatements(tokens.get(currentIndex));
        }
        else
        {
            return;
        }
    }
public void checkElseIf(Token token) throws ParseException {
        if(token.tokenType.equals(TokenTypes.KeyWord) && token.tokenValue.equals("elsif"))
        {
            currentIndex++;
            checkCondition(tokens.get(currentIndex));
            checkThen(tokens.get(currentIndex));
            checkStatements(tokens.get(currentIndex));
            checkElseIf(tokens.get(currentIndex));
        }
        else
        {
            return;
        }
    }
    public void checkThen(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.KeyWord) && token.tokenValue.equals("then")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid then");
        }
    }
    public void checkCondition(Token token) throws ParseException {
        checkExpression(tokens.get(currentIndex));
        checkRelationalOperator(tokens.get(currentIndex));
        checkExpression(tokens.get(currentIndex));
    }
    public void checkRelationalOperator(Token token) throws ParseException {
        if(token.tokenType.equals(TokenTypes.Operator) && (token.tokenValue.equals("=") || token.tokenValue.equals("<>") || token.tokenValue.equals("<") || token.tokenValue.equals(">") || token.tokenValue.equals("<=") || token.tokenValue.equals(">=")))
        {
            currentIndex++;
            return;
        }
        else
        {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid relational operator");
        }
    }


public void checkAssignStatment(Token token) throws ParseException {
    checkName(tokens.get(currentIndex));
    checkAssignOperator(tokens.get(currentIndex));
    checkExpression(tokens.get(currentIndex));
    checkSemiColon(tokens.get(currentIndex));
}
public void checkExpression(Token token) throws ParseException {
    checkTerm(tokens.get(currentIndex));
    checkAddStatment(tokens.get(currentIndex));
}
public void checkAddStatment(Token token) throws ParseException {
    if(token.tokenType.equals(TokenTypes.Operator) && (token.tokenValue.equals("+") || token.tokenValue.equals("-")))
    {
        currentIndex++;
        checkTerm(tokens.get(currentIndex));
        checkAddStatment(tokens.get(currentIndex));
    }
    else
    {
        return;
    }
}
public void checkTerm(Token token) throws ParseException {
    checkFactor(tokens.get(currentIndex));
    checkMulStatment(tokens.get(currentIndex));
}
public void checkFactor(Token token) throws ParseException {
    if(token.tokenType.equals(TokenTypes.Identifier))
    {
        checkName(tokens.get(currentIndex));
    }
    else if (token.tokenType.equals(TokenTypes.Integerliteral)  &&
            tokens.get(currentIndex + 1).tokenValue.equals(".") &&
            tokens.get(currentIndex + 2).tokenType.equals(TokenTypes.Integerliteral)) {
        checkRealLiteral(token);
    }
    else if(token.tokenType.equals(TokenTypes.Integerliteral)&&!tokens.get(currentIndex+1).tokenValue.equals('.'))
    {
        checkIntegerLiteral(tokens.get(currentIndex));
    }
    else if(token.tokenType.equals(TokenTypes.SpecialCharacter) && token.tokenValue.equals("("))
    {
        currentIndex++;
        checkExpression(tokens.get(currentIndex));
        checkRightParenthesis(tokens.get(currentIndex));
    }
    else
    {
        throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid factor");
    }
}
public void checkMulStatment(Token token) throws ParseException {
    if(token.tokenType.equals(TokenTypes.Operator) && (token.tokenValue.equals("*") || token.tokenValue.equals("/") || token.tokenValue.equals("div") || token.tokenValue.equals("mod")))
    {
        currentIndex++;
        checkFactor(tokens.get(currentIndex));
        checkMulStatment(tokens.get(currentIndex));
    }
    else
    {
        return;
    }
}

public void checkAssignOperator(Token token) throws ParseException {

    if ( token.tokenValue.equals(":") &&  tokens.get(currentIndex+1).tokenValue.equals("=")) {
        currentIndex= currentIndex+2;
        return;
    } else {
        throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid assign operator");
    }
}
public void checkWriteStatement (Token token) throws ParseException {
        if(token.tokenValue.equals("writeint")
                || token.tokenValue.equals("writereal")
                || token.tokenValue.equals("writechar") )
        {
            currentIndex++;
            checkLeftParenthesis(tokens.get(currentIndex));
            checkName(tokens.get(currentIndex));
            checkRightParenthesis(tokens.get(currentIndex));
            checkSemiColon(tokens.get(currentIndex));
        }
        else if (token.tokenValue.equals("writeln")) {
            checkSemiColon(tokens.get(currentIndex));
        }
        else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid write statement");
        }

    }
 public void checkReadStatement(Token token) throws ParseException {
        if(token.tokenValue.equals("readint")
                || token.tokenValue.equals("readreal")
                || token.tokenValue.equals("readchar") )
        {
            currentIndex++;
            checkLeftParenthesis(tokens.get(currentIndex));
            checkName(tokens.get(currentIndex));
            checkRightParenthesis(tokens.get(currentIndex));
            checkSemiColon(tokens.get(currentIndex));
        }
        else if (token.tokenValue.equals("readln")) {
            checkSemiColon(tokens.get(currentIndex));
        }
            else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid read statement");
        }

    }
    void checkLeftParenthesis(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.SpecialCharacter) && token.tokenValue.equals("(")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid left parenthesis");
        }
    }
    public void checkRightParenthesis(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.SpecialCharacter) && token.tokenValue.equals(")")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid right parenthesis");
        }
    }

    public void checkBegin(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.KeyWord) && token.tokenValue.equals("begin")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid begin");
        }
    }
    public void checkEnd(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.KeyWord) && token.tokenValue.equals("end")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid end");
        }
    }
    private void checkModuleHeading(Token token) throws ParseException {
        checkName(tokens.get(currentIndex));
        checkSemiColon(tokens.get(currentIndex));
            return;
        }


    public void checkDeclarations(Token token) throws ParseException {
        if(tokens.get(currentIndex).tokenValue.equals("const"))
        {
            currentIndex++;
            checkConstDeclarations(tokens.get(currentIndex));
        }
         if (tokens.get(currentIndex).tokenValue.equals("var"))
        {
            currentIndex++;
            checkVarDeclarations(tokens.get(currentIndex));
        }
        else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid declaration");
        }
    }

    public void checkConstDeclarations(Token token) throws ParseException {
         checkConstList(tokens.get(currentIndex));
    }

public void checkConstList(Token token) throws ParseException {
        while(!tokens.get(currentIndex).tokenType.equals(TokenTypes.KeyWord))
        {
            if(tokens.get(tokens.indexOf(currentIndex) + 4).tokenType.equals(TokenTypes.SpecialCharacter))
            {
                checkName(tokens.get(currentIndex));
                checkEqualsOperator(tokens.get(currentIndex));
                checkValue(tokens.get(currentIndex),true);
                checkSemiColon(tokens.get(currentIndex));
            }
            else if(!tokens.get(tokens.indexOf(currentIndex) + 4).tokenType.equals(TokenTypes.SpecialCharacter))
            {
                checkName(tokens.get(currentIndex));
                checkEqualsOperator(tokens.get(currentIndex));
                checkValue(tokens.get(currentIndex),false);
                checkSemiColon(tokens.get(currentIndex));
            }
            else {
                throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid const declaration");
            }
        }

    }
    public void checkVarDeclarations(Token token) throws ParseException {
        if(token.tokenType.equals(TokenTypes.Identifier))
        {
            checkVarList(tokens.get(currentIndex));
        }
        else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid var declaration");
        }

    }
    public void checkVarList(Token token) throws ParseException {
        while(!tokens.get(currentIndex).tokenType.equals(TokenTypes.KeyWord))
        {
                checkName(tokens.get(currentIndex));
                checkColon(tokens.get(currentIndex));
                checkDataType(tokens.get(currentIndex));
                checkSemiColon(tokens.get(currentIndex));
        }
    }
    public void checkDataType(Token token) throws ParseException {
        if(token.tokenType.equals(TokenTypes.KeyWord) && (token.tokenValue.equals("integer") || token.tokenValue.equals("real") || token.tokenValue.equals("char")))
        {
            currentIndex++;
            return;
        }
        else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid data type");
        }
    }
    public void checkColon(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.SpecialCharacter) && token.tokenValue.equals(":")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid colon");
        }
    }
    public void checkName(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.Identifier)) {
            currentIndex++;
            return;
        } else {
            System.out.println(currentIndex);
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid name");
        }

    }

    public void checkValue(Token token ,boolean isReal) throws ParseException {
        if(isReal)
        {
            checkRealLiteral(token);
        }
        else
        {
            checkIntegerLiteral(token);
        }

    }
   public void checkIntegerLiteral(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.Integerliteral)) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid integer literal");
        }
    }
    public void checkRealLiteral(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.Integerliteral) &&
                tokens.get(currentIndex + 1).tokenType.equals(TokenTypes.SpecialCharacter) &&
                tokens.get(currentIndex+ 1).tokenValue.equals(".") && tokens.get(currentIndex + 2).tokenType.equals(TokenTypes.Integerliteral)) {
            currentIndex += 3;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid real literal");
        }
    }
    public void checkEqualsOperator(Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.Operator) && token.tokenValue.equals("=")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid equals operator");
        }
    }
    public void checkSemiColon (Token token) throws ParseException {
        if (token.tokenType.equals(TokenTypes.SpecialCharacter) && token.tokenValue.equals(";")) {
            currentIndex++;
            return;
        } else {
            throw new ParseException("Error in line " + token.lineNumber + ": " + token.tokenValue + " is not a valid semicolon");
        }
    }
}