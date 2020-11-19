package assembler.tokenization;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import assembler.Error;

public class Lexer{
    private List<CharSequence> tokens;
    private int tokenPosition;          // Character position start from 1 to String.length()
    private int tokenLength;
    private final Pattern pattern;

    public Lexer(){
        StringBuilder regex = new StringBuilder();
        for(SYNTAX x : SYNTAX.values())
            regex.append(String.format("|(?<%s>%s)", x.name(), x.getPattern()));
        this.pattern = Pattern.compile(regex.substring(1));
    }

    public boolean tokenization(CharSequence...input){
        if(!(input[0] instanceof Comparable) || input.length == 0)
            throw new IllegalCallerException();

        this.tokens = new ArrayList<>();
        this.tokenPosition = 0;
        this.tokenLength = 0;

        // Add if tokenLength and tokenPosition are initialized in constructor
        // if(this.tokenLength == 0)    this.tokenLength += input.length - 1;
        // else                        this.tokenLength += input.length;

        if(input.length == 0 || input[0].equals(""))       return true;
        for(CharSequence x : input)
            this.tokenLength += internalTokenization(this.pattern.matcher(x), x.toString());

        if (this.tokenPosition == 0){
            return true;
        }
        else{
            this.tokens.removeIf(n -> n.getClass().isAssignableFrom(Token.class));
            return false;
        }

    }

    private int internalTokenization(Matcher matcher, String in){
        if(matcher.find() && matcher.end() == in.length()){
            for(SYNTAX x : SYNTAX.values())
            {
                if(matcher.group(x.name()) != null){
                    tokens.add(new Token(x, matcher.group(x.name())));
                }
            }            
        }else{
            this.tokenPosition = matcher.end() + this.tokenLength;
            
            this.tokens.add(new Error<Integer>(this.tokenPosition, new Token(null,in.substring(0, matcher.end())+"["+in.substring(matcher.end())+"]")));
        }        

        return in.length();
    }
    

    public int getTokenPosition(){
        return this.tokenPosition;
    }

    public int getTokenLength() {
        return this.tokenLength;
    }

    public List<CharSequence> getTokens() {
        return tokens;
    }

    /**
     * Class is not invoked statically;
     * Reset if necessary to reinitialize data members values
     */
    // public void reset(){
    //     this.tokens.clear();
    //     this.tokenPosition = 0;
    //     this.tokenLength = 0;
    // }

    public static void main(String[] args){
        Lexer lex = new Lexer();
        lex.tokenization("add.u8");
        lex.tokenization("La8bel", "add.u3", ";kfsfsd");
        System.out.println((lex.getTokens()));
        System.out.println(lex.getTokenPosition()+" - "+lex.getTokenLength());
    }
}