package assembler.tokenization;

import java.util.*;
import java.util.regex.*;

import assembler.Error;
import assembler.Node;
import assembler.Operand;
import util.BinaryAddress;

public class Lexer implements Lexical{
    private List<CharSequence> tokens;
    private int tokenPosition;          // Character position start from 1 to String.length()
    private int tokenLength;
    private final Pattern pattern;
    private Map<String, BinaryAddress> dictMap;

    public Lexer(){
        StringBuilder regex = new StringBuilder();
        for(FORMAT x : FORMAT.values())
            regex.append(String.format("|(?<%s>%s)", x.name(), x.getPattern()));
        this.pattern = Pattern.compile(regex.substring(1));
    }

    public Lexer(Map<String, BinaryAddress> map){
        dictMap = map;
        StringBuilder regex = new StringBuilder();
        for(FORMAT x : FORMAT.values())
            regex.append(String.format("|(?<%s>%s)", x.name(), x.getPattern()));
        this.pattern = Pattern.compile(regex.substring(1));
    }

    /**
     * Receives one or a string of Strings and generates an array of tokens if format matches one static instance of enum FORMAT.java
     * 
     */
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

    /**
     * Internal function for tokenization. Makes further verification in case of invalid format
     * @param matcher
     * @param in
     * @param cnt
     * @return
     */
    private int internalTokenization(Matcher matcher, String in, int cnt){
        if(matcher.find() && matcher.end() == in.length()){
            for(FORMAT x : FORMAT.values())
            {
                if(matcher.group(x.name()) != null){
                    if(
                    (   (x == FORMAT.OPCODEINHERENT || x == FORMAT.OPCODEIMMEDIATE || x == FORMAT.OPCODERELATIVE)
                    && (!this.dictMap.containsKey(in) || cnt == 0)    )

                    ||
                    
                    (   cnt == 1 && x == FORMAT.LABEL   )
                    )
                    {
                        this.generateError(null, in);                        
                        return in.length();
                    }
                    if(x == FORMAT.COMMENT){
                        tokens.add(new Token(x, new Node(0, in)));
                    }
                    if(x == FORMAT.LABEL)
                        tokens.add(new Token(x, new Node(0, in)));
                    if(x == FORMAT.OPERAND)
                        tokens.add(new Token(x, new Operand(Integer.valueOf(in), in)));
                    if(x == FORMAT.OPCODEINHERENT || x == FORMAT.OPCODEIMMEDIATE || x == FORMAT.OPCODERELATIVE)
                        tokens.add(new Token(x, new Node(this.dictMap.get(in), in)));
                }
            }            
        }else{
            this.generateError(matcher, in);
        }        

        return in.length();
    }

    /**
     * Internal function to generate an error array instead, in case of invalid format
     * @param matcher
     * @param input
     */
    
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
    public void reset(){
        this.tokens.clear();
        this.tokenPosition = 0;
        this.tokenLength = 0;
    }

    public static void main(String[] args){
            Lexer lex = new Lexer();
            System.out.println(lex.tokenization("", "add*", ";kfsfsd"));
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