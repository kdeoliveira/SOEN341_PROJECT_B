package assembler.tokenization;

import java.util.*;
import java.util.stream.Stream;

import assembler.Vertex;
import util.BinaryAddress;
import assembler.Comment;
import assembler.Error;
import assembler.Instruction;
import assembler.LineStatement;

public class Parser implements Parsable{
    private List<CharSequence> returnValueObjects;
    private final EBNF[] semantic;
    private String typeEBNF;
    private LineStatement iLineStatement;

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
        iLineStatement = new LineStatement();
        
        for(Vertex<?> x : object)
            type.add((FORMAT) x.getKey());

            //Ignore comments
        if(type.size() > 1)
            type.removeIf(n -> n == FORMAT.COMMENT);

        // System.out.println(Arrays.toString(object));

        if(this.internalParser(type.toArray(new FORMAT[0])))
        {
            for(int i=0 ; i<type.size(); i++){
                if(type.get(i).equals(FORMAT.LABEL)){
                    
                    if(i > 0){
                        if(!this.iLineStatement.hasOnlyLabels()){
                            this.returnValueObjects.add(new Error<Integer>(0, List.of(object), "Semantic does not match any valid EBNF format"));
                            return false;
                        }    
                        this.iLineStatement.setOperand(Token.class.cast(object[i]).getValue());
                    }
                    else{
                        System.out.println("Index: "+i+object[i]);
                        this.iLineStatement.setLabel(Token.class.cast(object[i]).getValue());
                    }
                }

                if(type.get(i).equals(FORMAT.COMMENT))
                    this.iLineStatement.setComment(new Comment(Token.class.cast(object[i]).getValue().getKey()));
                
                if(type.get(i).equals(FORMAT.OPCODEINHERENT) || type.get(i).equals(FORMAT.OPCODERELATIVE) || type.get(i).equals(FORMAT.OPCODEIMMEDIATE))
                    this.iLineStatement.setInstruction(new Instruction(Token.class.cast(object[i]).getValue(), null, typeEBNF), Token.class.cast(object[i]).hasParameters());
                if(type.get(i).equals(FORMAT.DIRECTIVE))
                    this.iLineStatement.setInstruction(new Instruction(new BinaryAddress(0), Token.class.cast(object[i]).getValue().getKey(), typeEBNF), Token.class.cast(object[i]).hasParameters());
                if(type.get(i).equals(FORMAT.STRINGOPERAND))
                    this.iLineStatement.setOperand(Token.class.cast(object[i]).getValue());
                if(type.get(i).equals(FORMAT.OPERAND))
                    this.iLineStatement.setOperand(Token.class.cast(object[i]).getValue());
                
            }
            
            this.iLineStatement.setTypeEBNF(typeEBNF);

            
            this.returnValueObjects.add(Arrays.toString(object));

            return true;
        }
        else{
            this.returnValueObjects.add(new Error<Integer>(1, List.of(object), "Invalid lexical syntax"));
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

    public LineStatement getLineStatement() {
        return this.iLineStatement;
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
