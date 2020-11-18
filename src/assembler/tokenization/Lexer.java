package assembler.tokenization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

import util.TrinarySearchTree;

public class Lexer{
    private ArrayList<Token> tokens;
    private int tokenPosition;          // Character position start from 1 to String.length()
    private int tokenLength;
    private ArrayList<String> errors;
    private final Pattern pattern;

    public Lexer(){
        this.tokens = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.tokenPosition = 0;
        this.tokenLength = 0;

        StringBuilder regex = new StringBuilder();
        for(SYNTAX x : SYNTAX.values())
            regex.append(String.format("|(?<%s>%s)", x.name(), x.getPattern()));
        this.pattern = Pattern.compile(regex.substring(1));
    }

    // public Lexer(TrinarySearchTree<String, String> dictionary){
    //     this.tokens = new ArrayList<>();
    //     this.tokenPosition = 0;
    //     this.tokenLength = 0;
    //     this.pattern = null;
    // }


    public boolean tokenization(String...input){

        // Add if tokenLength and tokenPosition are initialized in constructor
        if(this.tokenLength == 0)    this.tokenLength += input.length - 1;
        else                        this.tokenLength += input.length;

        for(String x : input)
            this.tokenLength += internalTokenization(this.pattern.matcher((CharSequence) x), x);

        return this.tokenPosition == 0;
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
            this.errors.add(this.tokenPosition+" -> "+in.substring(0, matcher.end())+"["
            +in.substring(matcher.end())+"]");
            
            // System.out.println("Syntax error:"+this.tokenPosition+" -> "+in.substring(0, matcher.end())+"["
            //                                     +in.substring(matcher.end())+"]");
        }        

        return in.length();
    }

    public int getTokenPosition(){
        return this.tokenPosition;
    }

    public int getTokenLength() {
        return this.tokenLength;
    }

    public Token[] getTokens(){
        return this.tokens.toArray(new Token[0]);
    }

    public String[] getErrors() {
        return errors.toArray(new String[0]);
    }

    /**
     * Class is not invoked statically;
     * Reset if necessary to reinitialize data members values
     */
    public void reset(){
        this.tokens.clear();
        this.tokenPosition = 0;
        this.tokenLength = 0;
    }

    public static void main(String[] args){
        Lexer lex = new Lexer();
        lex.tokenization("add.u8");
        System.out.println(Arrays.toString(lex.getTokens()));
        lex.tokenization("Label", "addu3", ";kfsfsd");
        System.out.println(Arrays.toString(lex.getTokens()));
        System.out.println(lex.getTokenPosition()+" - "+lex.getTokenLength());
    }
}