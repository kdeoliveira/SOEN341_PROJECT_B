package assembler.tokenization;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    private Token[] token;
    private final SEMANTIC[] semantic;

    public Parser(Token...tokens){
        this.token = tokens;
        this.semantic = SEMANTIC.values();
    }

    public void parse(){

        ArrayList<SYNTAX> type = new ArrayList<>();
        if(this.token.length > 0)
            for(Token x : this.token)
                type.add(x.getType());
        
        
        
        for(int i = 0 ; i < this.semantic.length ; i++)
        {
            if(Arrays.equals(this.semantic[i].getType(), type.toArray(new SYNTAX[0])))
            {
                System.out.println(this.semantic[i].name());
            }
        }
    }

    public static void main(String[] args){
        Lexer lex = new Lexer();
        lex.tokenization("add.u8", "Label");

        Token[] tokens = lex.getTokens();

        System.out.println(Arrays.toString(lex.getTokens()));

        Parser parser = new Parser(tokens);
        parser.parse();
    }


}
