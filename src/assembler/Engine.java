package assembler;

import java.util.*;

import assembler.tokenization.*;
import util.*;

public class Engine {
    private final Map<String,BinaryAddress> dictMap;
    private AssemblerUnit assemblerUnit;
    private int numberOfLine;
    private BinaryAddress memoryAddress;
    private Lexical lex;
    private Parsable parser;

    public Engine(Map<String, BinaryAddress> dict, Lexical lexer, Parsable parser) {
        this.dictMap = dict;

        this.assemblerUnit = new AssemblerUnit();

        this.memoryAddress = new BinaryAddress(0x0);

        this.numberOfLine = 0;
        this.lex = lexer;
        this.parser = parser;
    }

    /**
     * Receives an array of strings, which represents one line, for lexical analysis, parsing and dictionary verificaion
     * @param code
     * @return
     */
    public boolean assemble(String...code){
        if(code.length == 0)    return false;
        if(code.length == 1 && code[0].isEmpty())   return true;
        
        if(!this.checkSyntax(code) || 
        !this.checkSemantic(lex.getTokens().toArray(new Vertex<?>[0])) || 
        !this.createLineStatement(lex.getTokens().toArray(new Vertex<?>[0]))) return false;
        
        this.numberOfLine++;
        this.memoryAddress.plus(0x1);
        return true;
    }

    private boolean checkSyntax(String...code){
        if(!lex.tokenization(code)){
            
            this.assemblerUnit.addAll(lex.getTokens());
            return false;
        }
        
        return true;
    }

    private boolean checkSemantic(Vertex<?>...code){
        if(!parser.parse(code)) {
            
            if(parser.getReturnValueObjects() == null)
                this.assemblerUnit.add(new Error<Integer>(0, "Incorrect format"));
            else
                this.assemblerUnit.addAll(parser.getReturnValueObjects());
            return false;
        }

        
        return true;
    }

    /**
     * Creates lines of instruction for each parsed lines
     * @param code
     * @return
     */
    private boolean createLineStatement(Vertex<?>...code){
        int cnt = 0;
        boolean flag = false;
        Instruction inst = null;
        Node label = null;
        Comment comment = null;

        for(int i = 0; i < code.length ; i++){
            cnt+=code[i].length();
            if(code[i].getKey().equals(FORMAT.LABEL)){
                label = new Node(0, Token.class.cast(code[i]).getValue());
                if(i == 0){
                    flag = true;
                    label.setValue(new BinaryAddress(this.numberOfLine));
                    this.assemblerUnit.add(label);
                }
            }   
            if(code[i].getKey().equals(FORMAT.COMMENT)){
                comment = new Comment(Token.class.cast(code[i]).getValue());
                flag =true;
            }
            
            if(checkDictionary(code[i]) != null){
                inst = checkDictionary(code[i]);
                flag = true;
            }
                
            if(code[i].getKey().equals(FORMAT.OPERAND) && inst != null){
                flag = true;
                
                inst.setOperand(new Operand(new BinaryAddress(Token.class.cast(code[i]).getValue(), false), Token.class.cast(code[i]).getValue()));
            }
        }

        if(flag){
            this.assemblerUnit.add(new LineStatement(this.numberOfLine+1, label, inst, comment, parser.getTypeEBNF()));
            return true;
        }
        else{
            this.assemblerUnit.add(new Error<>(cnt, Arrays.toString(code)));
            return false;
        }
    }

    /**
     * Creates an instruction based on opcode
     * @param code
     * @return
     */
    private Instruction checkDictionary(Vertex<?> code){
        if(
            (code.getKey().equals(FORMAT.OPCODEINHERENT) || 
            code.getKey().equals(FORMAT.OPCODERELATIVE) ||
            code.getKey().equals(FORMAT.OPCODEIMMEDIATE) )
        && this.dictMap.containsKey(Token.class.cast(code).getValue()) )
        {
            return new Instruction(this.dictMap.get(Token.class.cast(code).getValue()), Token.class.cast(code).getValue(), this.parser.getTypeEBNF());
        }
        return null;
    }

    public int getNumberOfLine() {
        return numberOfLine;
    }

    public AssemblerUnit getAssemblerUnit() {
        return assemblerUnit;
    }

    public BinaryAddress getMemoryAddress() {
        return memoryAddress;
    }
}
