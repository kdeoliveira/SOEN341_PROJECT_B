package assembler.tokenization;

import java.util.*;
import java.util.regex.*;

import assembler.Error;

public class Lexer implements Lexical{
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
        int cnt = 0;
        for(CharSequence x : input){
            if(!x.toString().isEmpty())
                this.tokenLength += internalTokenization(this.pattern.matcher(x), x.toString(), cnt);
            cnt++;
        }

        if (this.tokenPosition == 0){
            return true;
        }
        else{
            this.tokens.removeIf(n -> n.getClass().isAssignableFrom(Token.class));
            return false;
        }

    }

    private int internalTokenization(Matcher matcher, String in, int cnt){
        if(matcher.find() && matcher.end() == in.length()){
            for(SYNTAX x : SYNTAX.values())
            {
                if(matcher.group(x.name()) != null){
                    if((cnt == 0 && x == SYNTAX.OPCODE) || (cnt == 1 && x == SYNTAX.LABEL)){
                        this.generateError(null, in);                        
                        return in.length();
                    }
                    tokens.add(new Token(x, matcher.group(x.name()).strip()));
                }
            }            
        }else{
            this.generateError(matcher, in);
        }        

        return in.length();
    }

    private void generateError(Matcher matcher, String input){
        try{
            if(matcher == null) throw new IllegalStateException();
            this.tokenPosition = matcher.end() + this.tokenLength;
            this.tokens.add(new Error<Integer>(this.tokenPosition, new Token(null,input.substring(0, matcher.end())+"["+input.substring(matcher.end())+"]")));
        }catch(IllegalStateException e){
            this.tokenPosition = this.tokenLength + 1;
            this.tokens.add(new Error<Integer>(this.tokenPosition, new Token(null,"["+input+"]")));
        }
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
            System.out.println(lex.tokenization("", " Label", ";kfsfsd"));
            System.out.println((lex.getTokens()));
            System.out.println(lex.getTokenPosition()+" - "+lex.getTokenLength());

            /**
             * Expected result of main:
            [(Unknown:1) [ Label]]
            1 - 17
            [Label (LABEL), add (OPCODE), ;kfsfsd (COMMENT)]
            0 - 16
            [(Unknown:6) [add]]
            6 - 15
             */
    }
}