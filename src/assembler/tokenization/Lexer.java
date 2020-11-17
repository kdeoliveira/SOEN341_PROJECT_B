package assembler.tokenization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

public class Lexer{
    private ArrayList<Token> tokens;

    public Lexer(){
        this.tokens = new ArrayList<>();
    }

    public boolean tokenization(Object...input){
        StringBuilder regex = new StringBuilder();
        boolean flag = false;
        

        for(SYNTAX x : SYNTAX.values())
            regex.append(String.format("|(?<%s>%s)", x.name(), x.getPattern()));
        Pattern pattern = Pattern.compile(regex.substring(1));

        for(Object x : input)
            flag = internalTokenization(pattern.matcher((CharSequence) x), x);

        return flag;
    }

    private boolean internalTokenization(Matcher matcher, Object in){
        boolean flag = false;
        if(matcher.find() && matcher.end() == in.toString().length()){
            for(SYNTAX x : SYNTAX.values())
            {
                if(matcher.group(x.name()) != null){
                    tokens.add(new Token(x, matcher.group(x.name())));
                    flag = true;
                }
            }

        }else{
            System.out.println("Syntax error:"+matcher.end()+" -> "+in.toString().substring(0, matcher.end())+"\033[1m"
                                                +in.toString().substring(matcher.end())+"\033[0m");
        }
        return flag;
    }

    public Token[] getTokens(){
        return this.tokens.toArray(new Token[0]);
    }

    public static void main(String[] args){
        Lexer lex = new Lexer();
        lex.tokenization("Label45", "add.u3");
        lex.tokenization(";kfsfsd  5478756 4fd");

        System.out.println(Arrays.toString(lex.getTokens()));
    }
}
