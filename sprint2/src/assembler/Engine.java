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
        !this.checkSemantic(lex.getTokens().toArray(new Vertex<?>[0]))
        ) return false;
        
        if(!((LineStatement) parser.getLineStatement()).getTypeEBNF().equals(EBNF.COMMENT.name()))
            this.numberOfLine++;
        this.memoryAddress.plus(0x1);
        return true;
    }

    private boolean checkSyntax(String...code){
        if(!lex.tokenization(code)){
            
            // this.assemblerUnit.addAll(lex.getTokens());
            for(CharSequence x : lex.getTokens())
                this.assemblerUnit.add(x);
            return false;
        }
        
        return true;
    }

    private boolean checkSemantic(Vertex<?>...code){
        try {
	    	if(!parser.parse(code)) {
	            
	            if(parser.getReturnValueObjects() == null)
	                this.assemblerUnit.add(new Error<Integer>(0, "Incorrect format"));
	            else{
	                for(CharSequence x : parser.getReturnValueObjects())
	                    this.assemblerUnit.add(x);
	            }
	            return false;
	        }
	    	parser.getLineStatement().checkBinaryValue(this.numberOfLine + 1);
        } 
        catch (Exception e) {
        	this.assemblerUnit.add(new Error<Integer>(0, " Operand out of bounds"));
        	return false;
        }
        
        this.assemblerUnit.add(parser.getLineStatement());
        
        if(parser.getLineStatement().getLabel() != null){

            this.assemblerUnit.add(parser.getLineStatement().getLabel());
            this.assemblerUnit.addLabelNumber(this.numberOfLine+1);
        }       
        return true;
    }

    public boolean pass2(){
        if(this.assemblerUnit.sizeLineStatement() == 0 || this.assemblerUnit.sizeLabel() == 0)  return false;
        for(int i =0; i < assemblerUnit.sizeLabel(); i++){
            for(int j=0; j < assemblerUnit.sizeLabel() ; j++){
                if(assemblerUnit.getLineStatements(j).getLabel() != null && assemblerUnit.getLineStatements(j).getLabel() == assemblerUnit.getLabel(i)){
                    this.assemblerUnit.getLineStatements(j).getInstruction().setOperand(assemblerUnit.getLabel(i));
                }
            }
        }
        return true;
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
