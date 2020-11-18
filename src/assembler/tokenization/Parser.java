package assembler.tokenization;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    private ArrayList<SYNTAX> type;
    private ArrayList<Token> errors;
    private final SEMANTIC[] semantic;

    public Parser(){
        this.semantic = SEMANTIC.values();
        this.type = new ArrayList<>();
        this.errors = new ArrayList<>();
    }

    public Object[] parse(Token...tokens){
        boolean flag = false;
        type = new ArrayList<>();
        this.errors = new ArrayList<>();

        if(tokens.length > 0)
            for(Token x : tokens)
                type.add(x.getType());
        
        
        
        for(int i = 0 ; i < this.semantic.length ; i++)
        {
            if(Arrays.equals(this.semantic[i].getType(), type.toArray(new SYNTAX[0])))
            {
                flag = true;
            }
        }

        if(flag)    return tokens;
        else        {
            errors.addAll(Arrays.asList(tokens));
            return new Object[0];
        }
        
    }

    public Object[] getType() {
        return this.type.toArray(new SYNTAX[0]);
    }

    public Token[] getErrors() {
        return errors.toArray(new Token[0]);
    }

    public static void main(String[] args){
        Lexer lex = new Lexer();
        lex.tokenization("addu", "abel");

        Token[] tokens = lex.getTokens();

        // System.out.println(Arrays.toString(lex.getTokens()));

        Parser parser = new Parser();
        parser.parse(tokens);
        // System.out.println(Arrays.toString(parser.parse(tokens)));
        System.out.println(Arrays.toString(parser.getErrors()));
    }


}
