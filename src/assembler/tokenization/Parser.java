package assembler.tokenization;

import java.util.*;

import assembler.Vertex;
import assembler.Error;

public class Parser implements Parsable{
    private List<CharSequence> returnValueObjects;
    private final EBNF[] semantic;
    private String typeEBNF;

    public Parser(){
        this.semantic = EBNF.values();
    }

    

    /**
     * Receives an array of Tokens (tokens implements the interface vertex<>) and verifies if tokens matches the EBNF syntax defined in EBNF.java
     */
    public boolean parse(Vertex<?>...object){
        if(object.length == 0 || !(object[0] instanceof Token))  return false;
        returnValueObjects = new ArrayList<>();
        List<FORMAT> type = new ArrayList<>();
        
        for(Vertex<?> x : object)
            type.add((FORMAT) x.getKey());

        if(type.size() > 1)
            type.removeIf(n -> n == FORMAT.COMMENT);

        if(this.internalParser(type.toArray(new FORMAT[0])))
        {
            this.returnValueObjects.add(Arrays.toString(object));
            return true;
        }
        else{
            
            this.returnValueObjects.add(new Error<Integer>(1, Arrays.toString(object)));
            return false;
        }
    }

    /**
     * Internal parsing for further verification. If tokens don't match, return false
     * @param cmp
     * @return boolean
     */
    private boolean internalParser(FORMAT[] cmp){
        for(int i = 0 ; i < this.semantic.length ; i++)
        {
            
            if(Arrays.equals(this.semantic[i].getType(), cmp))
            {
                this.typeEBNF = this.semantic[i].name();
                return true;
            }
        }
        this.typeEBNF = null;
        
        return false;
    }

    public List<CharSequence> getReturnValueObjects() {
        return this.returnValueObjects;
    }

    public String getTypeEBNF() {
        return this.typeEBNF;
    }

    public static void main(String[] args){
        Lexer lex = new Lexer();
        System.out.println(lex.tokenization("","add.u8","-3"));

        Vertex<?>[] tokens = lex.getTokens().toArray(new Vertex<?>[0]);

        Parser parser = new Parser();

        System.out.println(parser.parse(tokens));

        
        System.out.println(parser.getReturnValueObjects());
        System.out.println(parser.getTypeEBNF());


        /**
         * Expected result of main:
[Label (LABEL)]
[[Label (LABEL)]]
LABEL
         */
    }


}
