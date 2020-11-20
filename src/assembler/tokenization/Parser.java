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

    public boolean parse(Vertex<?>...object){
        if(object.length == 0 || !(object[0] instanceof Token))  return false;
        returnValueObjects = new ArrayList<>();
        List<SYNTAX> type = new ArrayList<>();
        
        for(Vertex<?> x : object)
            type.add((SYNTAX) x.getKey());

        if(type.size() > 1)
            type.removeIf(n -> n == SYNTAX.COMMENT);

        if(this.internalParser(type.toArray(new SYNTAX[0])))
        {
            this.returnValueObjects.add(Arrays.toString(object));
            return true;
        }
        else{
            this.returnValueObjects.add(new Error<Integer>(1, Arrays.toString(object)));
            return false;
        }
    }

    private boolean internalParser(SYNTAX[] cmp){
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
        System.out.println(lex.tokenization("","add",";add something"));

        Vertex<?>[] tokens = lex.getTokens().toArray(new Vertex<?>[0]);

        System.out.println(lex.getTokens());

        Parser parser = new Parser();

        parser.parse(tokens);

        
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
