// Ryan Widgeon and Luke Herczeg
// Team 72

package plc.compiler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The parser takes the sequence of tokens emitted by the lexer and turns that
 * into a structured representation of the program, called the Abstract Syntax
 * Tree (AST).
 *
 * The parser has a similar architecture to the lexer, just with {@link Token}s
 * instead of characters. As before, {@link #peek(Object...)} and {@link
 * #match(Object...)} are helpers to make the implementation easier.
 *
 * This type of parser is called <em>recursive descent</em>. Each rule in our
 * grammar will have it's own function, and reference to other rules correspond
 * to calling that functions.
 */
public final class Parser {

    private final TokenStream tokens;

    public Parser(List<Token> tokens) {
        this.tokens = new TokenStream(tokens);
    }

    /**
     * Parses the tokens and returns the parsed AST.
     */
    public static Ast parse(List<Token> tokens) throws ParseException {
        return new Parser(tokens).parseSource();
    }

    /**
     * Parses the {@code source} rule.
     */
    public Ast.Source parseSource() throws ParseException {
<<<<<<< HEAD
        //TODO
        List<Ast.Statement> statements = new ArrayList<Ast.Statement>();
        while(tokens.has(0)){
            statements.add(parseStatement());
        }
        Ast.Source source = new Ast.Source(statements);
        return source;
=======
        List<Ast.Statement> statements = new ArrayList<>();
        while(tokens.has(0)){
            statements.add(parseStatement());
        }

        return new Ast.Source(statements);
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
    }

    /**
     * Parses the {@code statement} rule and delegates to the necessary method.
     * If the next tokens do not start a declaration, assignment, if, or while
     * statement, then it is an expression statement. See these methods for
     * clarification on what starts each type of statement.
     */
    public Ast.Statement parseStatement() throws ParseException {
<<<<<<< HEAD
         //TODO
=======
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
        if(peek("LET")){
            return parseDeclarationStatement();
        }
        else if(peek("IF")){
            return parseIfStatement();
        }
        else if(peek("WHILE")){
            return parseWhileStatement();
        }
<<<<<<< HEAD
        else if(peek(Token.Type.IDENTIFIER,"=")){
            return parseAssignmentStatement();
        }
        else{
=======
        else if(peek(Token.Type.IDENTIFIER, "=")){
            return parseAssignmentStatement();
        } else {
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
            return parseExpressionStatement();
        }
    }

    /**
     * Parses the {@code expression-statement} rule. This method is called if
     * the next tokens do not start another statement type, as explained in the
     * javadocs of {@link #parseStatement()}.
     */
    public Ast.Statement.Expression parseExpressionStatement() throws ParseException {
<<<<<<< HEAD
        //TODO
        Ast.Expression exp =  parseExpression();
        if(match(";")){
            return new Ast.Statement.Expression(exp);
        }
        throw new ParseException("Missing ; for Expression Statement",tokens.index);

=======
        Ast.Expression e = parseExpression();
        if(match(";")){
            return new Ast.Statement.Expression(e);
        }
        throw new ParseException("Expression statement is missing a closing ';'.", tokens.index);
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
    }

    /**
     * Parses the {@code declaration-statement} rule. This method should only be
     * called if the next tokens start a declaration statement, aka {@code let}.
     */
    public Ast.Statement.Declaration parseDeclarationStatement() throws ParseException {
<<<<<<< HEAD
        //TODO
        match("LET");
        if(peek(Token.Type.IDENTIFIER,":",Token.Type.IDENTIFIER)){
            String name = tokens.get(0).getLiteral();
            match(name,":");
            String type = tokens.get(0).getLiteral();
            match(type);
            Optional<Ast.Expression> exp;
            if(match("=")){
                exp = Optional.of(parseExpression());
            }
            else{
                exp = Optional.ofNullable(null);
            }
            if(match(";")){
                return new Ast.Statement.Declaration(name,type, exp);
            }

            throw new ParseException("No ; for Declaration",tokens.index);
        }
        else{
            throw new ParseException("Incorrect Declaration Statement", tokens.index);
        }

=======
        match("LET");
        if(peek(Token.Type.IDENTIFIER, ":", Token.Type.IDENTIFIER)){
            // Grabbing the name and type of the declaration.
            String varName = tokens.get(0).getLiteral();
            match(varName,":");
            String varType = tokens.get(0).getLiteral();
            match(varType);

            Optional<Ast.Expression> e;

            if(match("=")){
                e = Optional.of(parseExpression());
            } else {
                e = Optional.empty();
            }

            if(match(";")){
                return new Ast.Statement.Declaration(varName, varType, e);
            }

            throw new ParseException("Declaration statement is missing a closing ';'.", tokens.index);
        } else {
            throw new ParseException("Declaration statement has wrong grammar!", tokens.index);
        }
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
    }

    /**
     * Parses the {@code assignment-statement} rule. This method should only be
     * called if the next tokens start an assignment statement, aka both an
     * {@code identifier} followed by {@code =}.
     */
    public Ast.Statement.Assignment parseAssignmentStatement() throws ParseException {
<<<<<<< HEAD
        //TODO
        String name = tokens.get(0).getLiteral();
        match(Token.Type.IDENTIFIER, "=");
        Ast.Expression exp = parseExpression();
        if(match(";")){

            return new Ast.Statement.Assignment(name, exp);
        }
        throw new ParseException("No ; for AssignmentStatement",tokens.index);
=======
        String varName = tokens.get(0).getLiteral();
        match(Token.Type.IDENTIFIER, "=");
        Ast.Expression e = parseExpression();
        if(match(";")){
            return new Ast.Statement.Assignment(varName, e);
        }
        throw new ParseException("Assignment statement is missing a closing ';'.",tokens.index);
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
    }

    /**
     * Parses the {@code if-statement} rule. This method should only be called
     * if the next tokens start an if statement, aka {@code if}.
     */
    public Ast.Statement.If parseIfStatement() throws ParseException {
<<<<<<< HEAD
         //TODO
        match("IF");
        Ast.Expression exp = parseExpression();
        if(!peek("THEN")){
            throw new ParseException("Incorrect If Statement",tokens.index);
        }
        else{
            match("THEN");
        }
        ArrayList<Ast.Statement> tStatements = new ArrayList<Ast.Statement>();
        ArrayList<Ast.Statement> eStatements = new ArrayList<Ast.Statement>();

        while(!(peek("ELSE")|| peek("END"))){
            Ast.Statement s = parseStatement();
            tStatements.add(s);
        }
        if(peek("ELSE")){
            match("ELSE");
            while(! peek("END")){
                Ast.Statement s = parseStatement();
                eStatements.add(s);
            }
        }
        if(match("END")){

            return new Ast.Statement.If(exp,tStatements, eStatements);
        }
        else{
            throw new ParseException("No END to end if Statement",tokens.index);
        }

=======
        match("IF");
        Ast.Expression e = parseExpression();

        if(!match("THEN")){
            throw new ParseException("If Statement has incorrect grammar!", tokens.index);
        }

        ArrayList<Ast.Statement> initialStatements = new ArrayList<>();
        ArrayList<Ast.Statement> elseStatements = new ArrayList<>();

        while(!(peek("ELSE") || peek("END"))){
            initialStatements.add(parseStatement());
        }
        if(match("ELSE")){
            while(!peek("END")){
                elseStatements.add(parseStatement());
            }
        }
        if(match("END")){
            return new Ast.Statement.If(e, initialStatements, elseStatements);
        } else {
            throw new ParseException("If statement has no end!", tokens.index);
        }
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
    }

    /**
     * Parses the {@code while-statement} rule. This method should only be
     * called if the next tokens start a while statement, aka {@code while}.
     */
    public Ast.Statement.While parseWhileStatement() throws ParseException {
<<<<<<< HEAD
        //TODO
        match("WHILE");
        Ast.Expression exp = parseExpression();
        if(match("DO")){
            ArrayList<Ast.Statement> dStatements = new ArrayList<Ast.Statement>();
            while(!peek("END")){
                Ast.Statement s = parseStatement();
                dStatements.add(s);
            }
            if (match("END")) {
                return new Ast.Statement.While(exp,dStatements);
            }
            else{
                throw new ParseException("No END for WHILE", tokens.index);
            }
        }
        else{
            throw new ParseException("No DO found for WHILE statement", tokens.index);
=======
        match("WHILE");
        Ast.Expression e = parseExpression();

        if(match("DO")){
            ArrayList<Ast.Statement> whileStatements = new ArrayList<>();
            while(!peek("END")){
                whileStatements.add(parseStatement());
            }
            if (match("END")) {
                return new Ast.Statement.While(e, whileStatements);
            } else {
                throw new ParseException("While statement has no end!", tokens.index);
            }
        } else {
            throw new ParseException("While statement has no do!", tokens.index);
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
        }

    }

    /**
     * Parses the {@code expression} rule.
     */
    public Ast.Expression parseExpression() throws ParseException {
<<<<<<< HEAD
        //TODO
=======
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
        return parseEqualityExpression();
    }

    /**
     * Parses the {@code equality-expression} rule.
     */
    public Ast.Expression parseEqualityExpression() throws ParseException {
<<<<<<< HEAD
        //TODO
        Ast.Expression left = parseAdditiveExpression();
        if((peek("==")||peek("!="))){
            // binary expression
            String operator = tokens.get(0).getLiteral();
            match(Token.Type.OPERATOR);
            Ast.Expression right = parseEqualityExpression();

            return new Ast.Expression.Binary(operator, left,right);
        }
        else{
            return left;
        }

=======
        Ast.Expression lhs = parseAdditiveExpression();
        if((peek("==") || peek("!="))){
            String op = tokens.get(0).getLiteral();
            match(Token.Type.OPERATOR);
            Ast.Expression rhs = parseEqualityExpression();

            return new Ast.Expression.Binary(op, lhs, rhs);
        } else {
            return lhs;
        }
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
    }

    /**
     * Parses the {@code additive-expression} rule.
     */
    public Ast.Expression parseAdditiveExpression() throws ParseException {
<<<<<<< HEAD
        //TODO

        Ast.Expression left = parseMultiplicativeExpression();
        if((peek("+")||peek("-"))){
            // binary expression
            String operator = tokens.get(0).getLiteral();
            match(Token.Type.OPERATOR);
            Ast.Expression right = parseAdditiveExpression();

            return new Ast.Expression.Binary(operator, left,right);
        }
        else{
            return left;
=======
        Ast.Expression lhs = parseMultiplicativeExpression();
        if((peek("+") || peek("-"))){
            String op = tokens.get(0).getLiteral();
            match(Token.Type.OPERATOR);
            Ast.Expression rhs = parseAdditiveExpression();

            return new Ast.Expression.Binary(op, lhs, rhs);
        } else {
            return lhs;
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
        }
    }

    /**
     * Parses the {@code multiplicative-expression} rule.
     */
    public Ast.Expression parseMultiplicativeExpression() throws ParseException {
<<<<<<< HEAD
        //TODO

        Ast.Expression left = parsePrimaryExpression();
        if((peek("*")||peek("/"))){
            // binary expression
            String operator = tokens.get(0).getLiteral();
            match(Token.Type.OPERATOR);
            Ast.Expression right = parseMultiplicativeExpression();

            return new Ast.Expression.Binary(operator, left,right);
        }
        else{
            return left;
=======
        Ast.Expression lhs = parsePrimaryExpression();
        if((peek("/") || peek("*"))){
            String op = tokens.get(0).getLiteral();
            match(Token.Type.OPERATOR);
            Ast.Expression rhs = parseMultiplicativeExpression();

            return new Ast.Expression.Binary(op, lhs, rhs);
        } else {
            return lhs;
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
        }
    }

    /**
     * Parses the {@code primary-expression} rule. This is the top-level rule
     * for expressions and includes literal values, grouping, variables, and
     * functions. It may be helpful to break these up into other methods but is
     * not strictly necessary.
     */
    public Ast.Expression parsePrimaryExpression() throws ParseException {
<<<<<<< HEAD
        //TODO
        if( peek("(")){
            return parseGroupExp();
        }
        else if(!peek(Token.Type.IDENTIFIER)){
            return parseLiteralExp();
        }
        else if(peek(Token.Type.IDENTIFIER,"(")){
            return parseFuncExp();
        }
        else if(peek(Token.Type.IDENTIFIER)){
            if(tokens.get(0).getLiteral().equals("TRUE")|| tokens.get(0).getLiteral().equals("FALSE")){
                return parseLiteralExp();
            }
            return parseVarExp();
        }
        throw new ParseException("Incorrect Primary Expression",tokens.index);
=======
        if(peek("(")){
            return parseGroup();
        }
        else if(!peek(Token.Type.IDENTIFIER)){
            return parseLiteral();
        }
        else if(peek(Token.Type.IDENTIFIER, "(")){
            return parseFunction();
        }
        else if(peek(Token.Type.IDENTIFIER)){
            if(tokens.get(0).getLiteral().equals("FALSE") || tokens.get(0).getLiteral().equals("TRUE")){
                return parseLiteral();
            } else {
                return parseVariable();
            }
        }
        throw new ParseException("Primary expression has incorrect grammar!", tokens.index);
    }

    public Ast.Expression parseFunction() throws ParseException {
        String name = tokens.get(0).getLiteral();
        match(Token.Type.IDENTIFIER, "(");
        ArrayList<Ast.Expression> functionParams = new ArrayList<>();

        while(!peek(")")){
            functionParams.add(parseExpression());
            match(",");
        }
        if(match(")")){
            return new Ast.Expression.Function(name, functionParams);
        }
        throw new ParseException("Function has no terminating ')'.", tokens.index);
    }

    public Ast.Expression parseVariable() throws ParseException {
        String varName = tokens.get(0).getLiteral();
        match(Token.Type.IDENTIFIER);
        return new Ast.Expression.Variable(varName);
>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
    }
    public Ast.Expression parseVarExp() throws ParseException {
        //TODO
        String name = tokens.get(0).getLiteral();
        match(Token.Type.IDENTIFIER);
        return new Ast.Expression.Variable(name);
    }
    public Ast.Expression parseFuncExp() throws ParseException {
        //TODO
        String name = tokens.get(0).getLiteral();
        match(Token.Type.IDENTIFIER);
        match("(");
        ArrayList<Ast.Expression> parameters = new ArrayList<Ast.Expression>();

        while(!peek(")")){
            Ast.Expression exp = parseExpression();
            parameters.add(exp);
            match(",");
        }
        if(match(")")){
            return new Ast.Expression.Function(name, parameters);
        }
        throw new ParseException("NO terminating ) for Function", tokens.index);

    }
    public Ast.Expression parseGroupExp() throws ParseException {
        //TODO
        match("(");
        Ast.Expression exp = parseExpression();
        if(match(")")){
            return new Ast.Expression.Group(exp);
        }
        throw new ParseException("No closing ) for Group", tokens.index);


<<<<<<< HEAD
    }
    public Ast.Expression parseLiteralExp() throws ParseException {
        //TODO
        Token.Type t = tokens.get(0).getType();
        if(t == Token.Type.DECIMAL){
            BigDecimal x = new BigDecimal(tokens.get(0).getLiteral());
            match(Token.Type.DECIMAL);
            return new Ast.Expression.Literal(x);
        }
        else if(t == Token.Type.INTEGER){
            BigInteger x = new BigInteger(tokens.get(0).getLiteral());
            match(Token.Type.INTEGER);
            return new Ast.Expression.Literal(x);
        }
        else if(t == Token.Type.STRING){
            String x = tokens.get(0).getLiteral();
            x= x.substring(1,x.length()-1);
            match(Token.Type.STRING);
            return new Ast.Expression.Literal(x);
        }
        else {
            String boo = tokens.get(0).getLiteral();
            match(Token.Type.IDENTIFIER);
            boolean fin= false;
            if(boo.equals("TRUE")){
                fin = true;
            }
            return new Ast.Expression.Literal(fin);
        }

    }
=======
    public Ast.Expression parseLiteral() throws ParseException {
        Token.Type type = tokens.get(0).getType();
        if(type == Token.Type.DECIMAL){
            BigDecimal decimal = new BigDecimal(tokens.get(0).getLiteral());
            match(Token.Type.DECIMAL);
            return new Ast.Expression.Literal(decimal);
        }
        else if(type == Token.Type.INTEGER){
            BigInteger integer = new BigInteger(tokens.get(0).getLiteral());
            match(Token.Type.INTEGER);
            return new Ast.Expression.Literal(integer);
        }
        else if(type == Token.Type.STRING){
            String str = tokens.get(0).getLiteral();

            // Shave off qu6otes
            str = str.substring(1, str.length() - 1);

            match(Token.Type.STRING);
            return new Ast.Expression.Literal(str);
        } else {
            String bool = tokens.get(0).getLiteral();
            match(Token.Type.IDENTIFIER);
            return new Ast.Expression.Literal(bool.equals("TRUE"));
        }
    }

    public Ast.Expression parseGroup() throws ParseException {
        match("(");
        Ast.Expression e = parseExpression();
        if(match(")")){
            return new Ast.Expression.Group(e);
        }
        throw new ParseException("Group has no terminating ')'.", tokens.index);
    }


>>>>>>> a427950fa6ee1b607c9189f69a0dc781a20ebd1f
    /**
     * As in the lexer, returns {@code true} if the current sequence of tokens
     * matches the given patterns. Unlike the lexer, the pattern is not a regex;
     * instead it is either a {@link Token.Type}, which matches if the token's
     * type is the same, or a {@link String}, which matches if the token's
     * literal is the same.
     *
     * In other words, {@code Token(IDENTIFIER, "literal")} is matched by both
     * {@code peek(Token.Type.IDENTIFIER)} and {@code peek("literal")}.
     */
    private boolean peek(Object... patterns) {
        for (int i = 0; i < patterns.length; i++) {
            if (!tokens.has(i)) {
                return false;
            } else if (patterns[i] instanceof Token.Type) {
                if (patterns[i] != tokens.get(i).getType()) {
                    return false;
                }
            } else if (patterns[i] instanceof String) {
                if (!patterns[i].equals(tokens.get(i).getLiteral())) {
                    return false;
                }
            } else {
                throw new AssertionError();
            }
        }
        return true;
    }

    /**
     * As in the lexer, returns {@code true} if {@link #peek(Object...)} is true
     * and advances the token stream.
     */
    private boolean match(Object... patterns) {
        boolean peek = peek(patterns);
        if (peek) {
            for (int i = 0; i < patterns.length; i++) {
                tokens.advance();
            }
        }
        return peek;
    }

    private static final class TokenStream {

        private final List<Token> tokens;
        private int index = 0;

        private TokenStream(List<Token> tokens) {
            this.tokens = tokens;
        }

        /**
         * Returns true if there is a token at index + offset.
         */
        public boolean has(int offset) {
            return index + offset < tokens.size();
        }

        /**
         * Gets the token at index + offset.
         */
        public Token get(int offset) {
            return tokens.get(index + offset);
        }

        /**
         * Advances to the next token, incrementing the index.
         */
        public void advance() {
            index++;
        }

    }

}
